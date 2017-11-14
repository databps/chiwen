package com.databps.bigdaf.chiwen.plugin;

import com.databps.bigdaf.chiwen.audit.AuthzAuditEvent;
import com.databps.bigdaf.chiwen.audit.ChiWenDefaultAuditHandler;
import com.databps.bigdaf.chiwen.policy.ChiWenAccessRequest;
import com.databps.bigdaf.chiwen.policy.ChiWenAccessResource;
import com.databps.bigdaf.chiwen.policy.ChiWenAccessResult;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

/**
 * @author merlin
 * @create 2017-11-07 下午6:22
 */
public class ChiWenHiveAuditHandler extends ChiWenDefaultAuditHandler {
  public static final String  ACCESS_TYPE_ROWFILTER = "ROW_FILTER";
  Collection<AuthzAuditEvent> auditEvents  = null;
  boolean                     deniedExists = false;

  public ChiWenHiveAuditHandler() {
    super();
  }
  AuthzAuditEvent createAuditEvent(ChiWenAccessResult result, String accessType, String resourcePath) {
    ChiWenAccessRequest request      = result.getAccessRequest();
    ChiWenAccessResource resource     = request.getResource();
    String               resourceType = resource != null ? resource.getLeafName() : null;

    AuthzAuditEvent auditEvent = super.getAuthzEvents(result);

    auditEvent.setAccessType(accessType);
    auditEvent.setResourcePath(resourcePath);
    auditEvent.setResourceType("@" + resourceType); // to be consistent with earlier release

    if (request instanceof ChiWenHiveAccessRequest && resource instanceof ChiWenHiveResource) {
      ChiWenHiveAccessRequest hiveAccessRequest = (ChiWenHiveAccessRequest) request;
      ChiWenHiveResource hiveResource = (ChiWenHiveResource) resource;

      if (hiveAccessRequest.getHiveAccessType() == HiveAccessType.USE && hiveResource.getObjectType() == HiveObjectType.DATABASE && StringUtils
          .isBlank(hiveResource.getDatabase())) {
        // this should happen only for SHOWDATABASES
      }
    }

    return auditEvent;
  }

  AuthzAuditEvent createAuditEvent(ChiWenAccessResult result) {
    ChiWenAccessRequest  request  = result.getAccessRequest();
    ChiWenAccessResource resource = request.getResource();
    String               resourcePath = resource != null ? resource.getAsString() : null;

    String accessType = null;

    if (request instanceof ChiWenHiveAccessRequest) {
      ChiWenHiveAccessRequest hiveRequest = (ChiWenHiveAccessRequest) request;

      accessType = hiveRequest.getHiveAccessType().toString();
    }

    if (StringUtils.isEmpty(accessType)) {
      accessType = request.getAccessType();
    }

    return createAuditEvent(result, accessType, resourcePath);
  }

  List<AuthzAuditEvent> createAuditEvents(Collection<ChiWenAccessResult> results) {

    Map<Long, AuthzAuditEvent> auditEvents = new HashMap<Long, AuthzAuditEvent>();
    Iterator<ChiWenAccessResult> iterator = results.iterator();
    AuthzAuditEvent deniedAuditEvent = null;
    while (iterator.hasNext() && deniedAuditEvent == null) {
      ChiWenAccessResult result = iterator.next();
      if(result.getIsAudited()) {
        if (!result.getIsAllowed()) {
          deniedAuditEvent = createAuditEvent(result);
        } else {
          long policyId = result.getPolicyId();
          if (auditEvents.containsKey(policyId)) { // add this result to existing event by updating column values
            AuthzAuditEvent auditEvent = auditEvents.get(policyId);
            ChiWenHiveAccessRequest request    = (ChiWenHiveAccessRequest)result.getAccessRequest();
            ChiWenHiveResource resource   = (ChiWenHiveResource)request.getResource();
            String resourcePath = auditEvent.getResourcePath() + "," + resource.getColumn();
            auditEvent.setResourcePath(resourcePath);
          } else { // new event as this approval was due to a different policy.
            AuthzAuditEvent auditEvent = createAuditEvent(result);

            if(auditEvent != null) {
              auditEvents.put(policyId, auditEvent);
            }
          }
        }
      }
    }
    List<AuthzAuditEvent> result;
    if (deniedAuditEvent == null) {
      result = new ArrayList<>(auditEvents.values());
    } else {
      result = Lists.newArrayList(deniedAuditEvent);
    }

    return result;
  }

  @Override
  public void processResult(ChiWenAccessResult result) {
    if(! result.getIsAudited()) {
      return;
    }
    AuthzAuditEvent auditEvent = createAuditEvent(result);

    if(auditEvent != null) {
      addAuthzAuditEvent(auditEvent);
    }
  }

  /**
   * This method is expected to be called ONLY to process the results for multiple-columns in a table.
   * To ensure this, ChiWenHiveAuthorizer should call isAccessAllowed(Collection<requests>) only for this condition
   */
  @Override
  public void processResults(Collection<ChiWenAccessResult> results) {
    List<AuthzAuditEvent> auditEvents = createAuditEvents(results);
    for(AuthzAuditEvent auditEvent : auditEvents) {
      addAuthzAuditEvent(auditEvent);
    }
  }

  public void logAuditEventForDfs(String userName, String dfsCommand, boolean accessGranted, String serviceId) {
    AuthzAuditEvent auditEvent = new AuthzAuditEvent();

    auditEvent.setAclEnforcer("chiwen-acl");
    auditEvent.setResourceType("@dfs"); // to be consistent with earlier release
    auditEvent.setAccessType("DFS");
    auditEvent.setAction("DFS");
    auditEvent.setUser(userName);
    auditEvent.setAccessResult((accessGranted ? "success": "failure"));
    auditEvent.setAccessTime(new Date());
    auditEvent.setRequestData(dfsCommand);
    auditEvent.setServiceId(serviceId);
    auditEvent.setServiceType("hive");
    auditEvent.setResourcePath(dfsCommand);

    addAuthzAuditEvent(auditEvent);
  }

  public void flushAudit() {
    if(auditEvents == null) {
      return;
    }

    for(AuthzAuditEvent auditEvent : auditEvents) {
      if(deniedExists && !auditEvent.getAccessResult().equals("failure") ) { // if deny exists, skip logging for allowed results
        continue;
      }

      super.logAuthzAudit(auditEvent);
    }
  }

  private void addAuthzAuditEvent(AuthzAuditEvent auditEvent) {
    if(auditEvent != null) {
      if(auditEvents == null) {
        auditEvents = new ArrayList<AuthzAuditEvent>();
      }

      auditEvents.add(auditEvent);

      if(auditEvent.getAccessResult().equals("failure")) {
        deniedExists = true;
      }
    }
  }
}
