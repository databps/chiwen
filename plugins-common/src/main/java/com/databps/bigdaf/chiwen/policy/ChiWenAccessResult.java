package com.databps.bigdaf.chiwen.policy;

import com.databps.bigdaf.chiwen.model.ChiWenServiceDef;

/**
 * Created by lgc on 17-7-20.
 */
public class ChiWenAccessResult {


  private String serviceType = null;
  private String policyName = null;
  private String serviceId;




  // private final ChiWenServiceDef  serviceDef;
  private  ChiWenServiceDef  serviceDef;
  private boolean  isAllowed;
  private boolean isAudited = false;
  private String reason = null;

  private final ChiWenAccessRequest request;

  private boolean isAccessDetermined;
  private boolean isAuditedDetermined;
  private long     auditPolicyId  = -1;
  private long     policyId  = -1;
  private long     evaluatedPoliciesCount;

  public ChiWenAccessResult(String serviceType,String serviceId, ChiWenAccessRequest request,Boolean isAudited) {
    this(serviceType,serviceId, request, false, isAudited, "");
  }

  public ChiWenAccessResult(String serviceType,String serviceId, ChiWenAccessRequest request) {
    this(serviceType,serviceId, request, false, true, "");
  }
  public ChiWenAccessResult(final String serviceType, final String serviceId,final ChiWenServiceDef serviceDef, final ChiWenAccessRequest request) {
    this.serviceType = serviceType;
    this.serviceId=serviceId;
    this.serviceDef  = serviceDef;
    this.request     = request;
    this.isAccessDetermined = false;
    this.isAllowed   = false;
    this.isAuditedDetermined = false;
    this.isAudited   = false;
    this.auditPolicyId = -1;
    this.policyId    = -1;
    this.evaluatedPoliciesCount = 0;
    this.reason      = null;
  }

  public ChiWenAccessResult(String serviceType, String serviceId,ChiWenAccessRequest request, boolean isAllowed,
      boolean isAudited, String reason) {
    this.serviceType = serviceType;
    this.serviceId=serviceId;
    this.isAccessDetermined = false;
    this.request = request;
    this.isAllowed = isAllowed;
    this.isAudited = isAudited;
    this.reason = reason;
  }

  public String getServiceType() {
    return serviceType;
  }

  public void setServiceType(String serviceType) {
    this.serviceType = serviceType;
  }

  public String getPolicyName() {
    return policyName;
  }

  public void setPolicyName(String policyName) {
    this.policyName = policyName;
  }

  public ChiWenAccessRequest getRequest() {
    return request;
  }



  public boolean getIsAudited() {
    return isAudited;
  }

  public void setAudited(boolean audited) {
    isAudited = audited;
  }

  public boolean isAudited() {
    return isAudited;
  }

  public boolean isAccessDetermined() {
    return isAccessDetermined;
  }

  public void setAccessDetermined(boolean accessDetermined) {
    isAccessDetermined = accessDetermined;
  }

  public ChiWenAccessRequest getAccessRequest() {
    return request;
  }

  public boolean isAuditedDetermined() {
    return isAuditedDetermined;
  }

  public void setAuditedDetermined(boolean auditedDetermined) {
    isAuditedDetermined = auditedDetermined;
  }

  public long getAuditPolicyId() {
    return auditPolicyId;
  }

  public void setAuditPolicyId(long auditPolicyId) {
    this.auditPolicyId = auditPolicyId;
  }

  public long getPolicyId() {
    return policyId;
  }

  public void setPolicyId(long policyId) {
    this.policyId = policyId;
  }

  public long getEvaluatedPoliciesCount() {
    return evaluatedPoliciesCount;
  }

  public void setEvaluatedPoliciesCount(long evaluatedPoliciesCount) {
    this.evaluatedPoliciesCount = evaluatedPoliciesCount;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public boolean getIsAccessDetermined() { return isAccessDetermined; }

  public void setIsAccessDetermined(boolean value) { isAccessDetermined = value; }

  @Override
  public String toString() {
    return "ChiWenAccessResult{" +
        "serviceType='" + serviceType + '\'' +
        ", policyName='" + policyName + '\'' +
        ", request=" + request +
        ", isAllowed=" + isAllowed +
        ", isAudited=" + isAudited +
        ", reason='" + reason + '\'' +
        ", isAccessDetermined=" + isAccessDetermined +
        '}';
  }
  public StringBuilder toString(StringBuilder sb) {
    sb.append("ChiWenAccessResult={");

    sb.append("isAccessDetermined={").append(isAccessDetermined).append("} ");
    sb.append("isAllowed={").append(isAllowed).append("} ");
    sb.append("isAuditedDetermined={").append(isAuditedDetermined).append("} ");
    sb.append("isAudited={").append(isAudited).append("} ");
    sb.append("policyId={").append(policyId).append("} ");
    sb.append("auditPolicyId={").append(auditPolicyId).append("} ");
    sb.append("evaluatedPoliciesCount={").append(evaluatedPoliciesCount).append("} ");
    sb.append("reason={").append(reason).append("} ");

    sb.append("}");

    return sb;
  }
  public ChiWenServiceDef getServiceDef() {
    return serviceDef;
  }

  public void setServiceDef(ChiWenServiceDef serviceDef) {
    this.serviceDef = serviceDef;
  }

  public void setIsAllowed(boolean isAllowed) {
    if(! isAllowed) {
      setIsAccessDetermined(true);
    }

    this.isAllowed = isAllowed;
  }

  public boolean getIsAllowed() {
    return isAllowed;
  }

  public String getServiceId() {
    return serviceId;
  }

  public void setServiceId(String serviceId) {
    this.serviceId = serviceId;
  }
}
