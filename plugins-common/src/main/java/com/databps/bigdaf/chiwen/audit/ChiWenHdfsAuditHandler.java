package com.databps.bigdaf.chiwen.audit;
import com.databps.bigdaf.chiwen.policy.ChiWenAccessResult;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.permission.FsAction;


/**
 * @author merlin
 * @create 2017-10-26 上午11:45
 */
public class ChiWenHdfsAuditHandler extends ChiWenDefaultAuditHandler {
  private static final String    HadoopModuleName ="hadoop-acl";

  private static final Log LOG = LogFactory.getLog(ChiWenHdfsAuditHandler.class);

  private AuthzAuditEvent auditEvent = null;
  private String          pathToBeValidated = null;
  private final boolean auditOnlyIfDenied;

  public ChiWenHdfsAuditHandler(String pathToBeValidated) {
    this.pathToBeValidated = pathToBeValidated;
    this.auditEvent = new AuthzAuditEvent();
    this.auditOnlyIfDenied = false;
  }

  public ChiWenHdfsAuditHandler(String pathToBeValidated,boolean auditOnlyIfDenied) {
    this.pathToBeValidated = pathToBeValidated;
    this.auditEvent = new AuthzAuditEvent();
    this.auditOnlyIfDenied = auditOnlyIfDenied;
  }

  public void logHadoopEvent(String path, FsAction action, boolean accessGranted) {
    if(LOG.isDebugEnabled()) {
      LOG.debug("==> ChiWenHdfsAuditHandler.logHadoopEvent(" + path + ", " + action + ", " + accessGranted + ")");
    }

    if(auditEvent != null) {
      auditEvent.setResultReason(path);
      auditEvent.setAccessResult((accessGranted ? "success": "failure"));
      auditEvent.setAccessType(action == null ? null : action.toString());
      auditEvent.setAclEnforcer(HadoopModuleName);
      auditEvent.setPolicyId(-1);
    }

    if(LOG.isDebugEnabled()) {
      LOG.debug("<== ChiWenHdfsAuditHandler.logHadoopEvent(" + path + ", " + action + ", " + accessGranted + "): " + auditEvent);
    }
  }
//
//  @Override
//  public void logAudit(ChiWenAccessResult result) {
//    auditEvent.setAccessTime((Date) result.getRequest().getAccessTime());
//    auditEvent.setAccessType(result.getRequest().getAccessType());
//    auditEvent.setAction(result.getRequest().getAction());
//    auditEvent.setClientIPAddress(result.getRequest().getClientIPAddress());
//    auditEvent.setPath(result.getRequest().getPath());
//    auditEvent.setUser(result.getRequest().getUser());
//    auditEvent.setUserGroups(result.getRequest().getUserGroups());
//    auditEvent.setIsAllowed(result.getIsAllowed()? AuditStatus.SUCCESS.getName():AuditStatus.FAILURE.getName());
//  }

  public void flushAudit() {
    super.logAuthzAudit(auditEvent);
  }

  public enum AuditStatus {
    SUCCESS("success"),
    FAILURE("failure"),
    UNKOWN("unknown"),;

    private String name;

    AuditStatus(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }
}
