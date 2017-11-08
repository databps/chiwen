package com.databps.bigdaf.chiwen.audit;

import com.databps.bigdaf.chiwen.policy.ChiWenAccessRequest;
import com.databps.bigdaf.chiwen.policy.ChiWenAccessResource;
import com.databps.bigdaf.chiwen.policy.ChiWenAccessResult;
import com.databps.bigdaf.chiwen.util.ChiWenAccessResultProcessor;
import com.databps.bigdaf.chiwen.util.MiscUtil;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by lgc on 17-7-20.
 */
public class ChiWenDefaultAuditHandler implements ChiWenAccessResultProcessor {

  private static final Log LOG = LogFactory.getLog(ChiWenDefaultAuditHandler.class);
  static long sequenceNumber;
  private static AtomicInteger  counter =  new AtomicInteger(0);
  private static String UUID 	= MiscUtil.generateUniqueId();

  public static final String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
  //protected static final String ChiWenModuleName =  ChiWenConfiguration.getInstance().get(ChiWenHadoopConstants.AUDITLOG_CHIWEN_MODULE_ACL_NAME_PROP , ChiWenHadoopConstants.DEFAULT_CHIWEN_MODULE_ACL_NAME);


  public void logAuthzAudit(AuthzAuditEvent auditEvent) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("==> ChiWenDefaultAuditHandler.logAuthzAudit(" + auditEvent + ")");
    }

    if (auditEvent != null) {
      populateDefaults(auditEvent);
      auditEvent.setSeqNum(sequenceNumber++);
      if(!AuditProviderFactory.getAuditProvider().log(auditEvent)) {
        MiscUtil.logErrorMessageByInterval(LOG, "fail to log audit event " + auditEvent);
      }
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("<== ChiWenDefaultAuditHandler.logAuthzAudit(" + auditEvent + ")");
    }

  }

  public void logAuthzAudits(Collection<AuthzAuditEvent> auditEvents) {
    if(LOG.isDebugEnabled()) {
      LOG.debug("==> ChiWenDefaultAuditHandler.logAuthzAudits(" + auditEvents + ")");
    }

    if(auditEvents != null) {
      for(AuthzAuditEvent auditEvent : auditEvents) {
        logAuthzAudit(auditEvent);
      }
    }

    if(LOG.isDebugEnabled()) {
      LOG.debug("<== ChiWenDefaultAuditHandler.logAuthzAudits(" + auditEvents + ")");
    }
  }


  public AuthzAuditEvent createAuthzAuditEvent() {
    return new AuthzAuditEvent();
  }

  public String getAdditionalInfo(ChiWenAccessRequest request) {
    if (StringUtils.isBlank(request.getRemoteIPAddress()) && CollectionUtils
        .isEmpty(request.getForwardedAddresses())) {
      return null;
    }
    StringBuilder sb = new StringBuilder();
    sb.append("{\"remote-ip-address\":").append(request.getRemoteIPAddress())
        .append(", \"forwarded-ip-addresses\":[")
        .append(StringUtils.join(request.getForwardedAddresses(), ", ")).append("]");

    return sb.toString();
  }
  public void processResult(ChiWenAccessResult result) {
    if(LOG.isDebugEnabled()) {
      LOG.debug("==> ChiWenDefaultAuditHandler.processResult(" + result + ")");
    }

    AuthzAuditEvent event = getAuthzEvents(result);

    logAuthzAudit(event);

    if(LOG.isDebugEnabled()) {
      LOG.debug("<== ChiWenDefaultAuditHandler.processResult(" + result + ")");
    }
  }

  @Override
  public void processResults(Collection<ChiWenAccessResult> results) {

  }

  public AuthzAuditEvent getAuthzEvents(ChiWenAccessResult result) {
    if(LOG.isDebugEnabled()) {
      LOG.debug("==> ChiWenDefaultAuditHandler.getAuthzEvents(" + result + ")");
    }

    AuthzAuditEvent ret = null;

    ChiWenAccessRequest request = result != null ? result.getRequest() : null;

    if(request != null && result != null && result.getIsAudited()) {
      ChiWenAccessResource resource     = request.getResource();
      String               resourceType = resource == null ? null : resource.getLeafName();
      String               resourcePath = resource == null ? null : resource.getAsString();

      ret = createAuthzAuditEvent();

      ret.setServiceType(result.getServiceType());
      ret.setServiceId(result.getServiceId());
      ret.setResourceType(resourceType);
      ret.setResourcePath(resourcePath);
      ret.setRequestData(request.getRequestData());
      ret.setAccessTime(request.getAccessTime());
      ret.setUser(request.getUser());
      ret.setUserGroups(request.getUserGroups());
      ret.setAction(request.getAccessType());
      ret.setAccessResult( (result.getIsAllowed() ? "success": "failure"));
      ret.setPolicyId(result.getPolicyId());
      ret.setAccessType(request.getAction());
      ret.setClientIPAddress(request.getClientIPAddress());
      ret.setClientType(request.getClientType());
      ret.setAdditionalInfo(getAdditionalInfo(request));
      ret.setClusterName(request.getClusterName());
      populateDefaults(ret);
    }

    if(LOG.isDebugEnabled()) {
      LOG.debug("<== ChiWenDefaultAuditHandler.getAuthzEvents(" + result + "): " + ret);
    }

    return ret;
  }
  private void populateDefaults(AuthzAuditEvent auditEvent) {
    if( auditEvent.getAclEnforcer() == null || auditEvent.getAclEnforcer().isEmpty()) {
      auditEvent.setAclEnforcer("chiwen-acl"); // TODO: review
    }

    if (auditEvent.getAgentHostname() == null || auditEvent.getAgentHostname().isEmpty()) {
      auditEvent.setAgentHostname(MiscUtil.getHostname());
    }

    if (auditEvent.getLogType() == null || auditEvent.getLogType().isEmpty()) {
      auditEvent.setLogType("ChiWenAudit");
    }

    if (auditEvent.getEventId() == null || auditEvent.getEventId().isEmpty()) {
      auditEvent.setEventId(generateNextAuditEventId());
    }


  }
  private String generateNextAuditEventId() {
    int nextId = counter.getAndIncrement();

    if(nextId == Integer.MAX_VALUE) {
      // reset UUID and counter
      UUID = MiscUtil.generateUniqueId();
      counter = new AtomicInteger(0);
    }

    return UUID + "-" + Integer.toString(nextId);
  }



}
