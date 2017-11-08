package com.databps.bigdaf.chiwen.audit;

import java.util.Date;
import java.util.Set;

/**
 * Created by lgc on 17-7-20.
 */
public class AuthzAuditEvent {

  private String resourcePath;
  private String serviceType = null;
  private String serviceId;
  private String accessType;
  private String user;
  private Set<String> userGroups;
  private Date accessTime;
  private String clientIPAddress;
  private String clientType="NONE";
  private String action;
  private short isAllowed;
  protected String resourceType = null;
  protected String requestData;
  protected String aclEnforcer = null;
  protected long policyId = 0;
  protected String additionalInfo;
  protected String clusterName;
  protected String agentHostname;


  protected String logType = null;

  protected String eventId = null;
  protected long seqNum = 0;


  public void setPolicyId(long policyId) {
    this.policyId = policyId;
  }


  public String  getAccessResult(){
    return this.accessResult;
  }

  protected String accessResult = "failure"; // 0 - failure; 1 - success; HTTP return
  // code

  protected String resultReason = null;

  public String getResourcePath() {
    return resourcePath;
  }

  public void setResourcePath(String resourcePath) {
    this.resourcePath = resourcePath;
  }

  public String getAccessType() {
    return accessType;
  }

  public void setAccessType(String accessType) {
    this.accessType = accessType;
  }
  public void setAclEnforcer(String aclEnforcer) {
    this.aclEnforcer = aclEnforcer;
  }
  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public Set<String> getUserGroups() {
    return userGroups;
  }

  public void setUserGroups(Set<String> userGroups) {
    this.userGroups = userGroups;
  }

  public Date getAccessTime() {
    return accessTime;
  }

  public void setAccessTime(Date accessTime) {
    this.accessTime = (accessTime == null) ? new Date() : accessTime;
  }

  public String getClientIPAddress() {
    return clientIPAddress;
  }

  public void setClientIPAddress(String clientIPAddress) {
    this.clientIPAddress = clientIPAddress;
  }

  public String getClientType() {
    return clientType;
  }

  public void setClientType(String clientType) {
    this.clientType = clientType;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public short getIsAllowed() {
    return isAllowed;
  }

  public void setIsAllowed(short isAllowed) {
    this.isAllowed = isAllowed;
  }

  public void setResultReason(String resultReason) {
    this.resultReason = resultReason;
  }

  public void setAccessResult(String accessResult) {
    this.accessResult = accessResult;
  }

  public String getResourceType() {
    return resourceType;
  }

  public void setResourceType(String resourceType) {
    this.resourceType = resourceType;
  }

  public String getRequestData() {
    return requestData;
  }

  public void setRequestData(String requestData) {
    this.requestData = requestData;
  }

  public String getServiceType() {
    return serviceType;
  }

  public void setServiceType(String serviceType) {
    this.serviceType = serviceType;
  }


  public String getAdditionalInfo() {
    return additionalInfo;
  }

  public void setAdditionalInfo(String additionalInfo) {
    this.additionalInfo = additionalInfo;
  }

  public String getClusterName() {
    return clusterName;
  }

  public void setClusterName(String clusterName) {
    this.clusterName = clusterName;
  }

  public String getAclEnforcer() {
    return aclEnforcer;
  }

  public String getAgentHostname() {
    return agentHostname;
  }

  public void setAgentHostname(String agentHostname) {
    this.agentHostname = agentHostname;
  }

  public String getLogType() {
    return logType;
  }

  public void setLogType(String logType) {
    this.logType = logType;
  }

  public String getEventId() {
    return eventId;
  }

  public void setEventId(String eventId) {
    this.eventId = eventId;
  }

  public long getSeqNum() {
    return seqNum;
  }

  public void setSeqNum(long seqNum) {
    this.seqNum = seqNum;
  }

  public String getServiceId() {
    return serviceId;
  }

  public void setServiceId(String serviceId) {
    this.serviceId = serviceId;
  }
}
