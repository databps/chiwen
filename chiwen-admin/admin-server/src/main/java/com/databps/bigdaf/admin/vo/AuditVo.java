package com.databps.bigdaf.admin.vo;

import java.util.Set;


/**
 * AuditVo
 *
 * @author lgc
 * @create 2017-08-02 下午4:00
 */
public class AuditVo {

  private String id;
  private String cmpyId;

  private String resourceType;
  private String clientType="NONE";
  private String pluginIp;

  private String clientIPAddress="0.0.0.0";
  private String accessType;//原生请求
  private String resourcePath;
  private String user;
  private Set<String> userGroups;
  private String accessTime;
  private String createTime;
  private String serviceType;
  private String serviceId;
  private String action ;//被翻译的
  private short isAllowed;
  private String logType;
  private String aclEnforcer;
  private String agentHostname;
  private String eventId = null;
  private long seqNum = 0;
  private String accessResult;
  private String requestData="";

  public String getCmpyId() {
    return cmpyId;
  }

  public void setCmpyId(String cmpyId) {
    this.cmpyId = cmpyId;
  }

  public String getClientIPAddress() {
    return clientIPAddress;
  }

  public void setClientIPAddress(String clientIPAddress) {
    this.clientIPAddress = clientIPAddress;
  }

  public String getAccessType() {
    return accessType;
  }

  public void setAccessType(String accessType) {
    this.accessType = accessType;
  }

  public String getResourcePath() {
    return resourcePath;
  }

  public void setResourcePath(String resourcePath) {
    this.resourcePath = resourcePath;
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

  public String getAccessTime() {
    return accessTime;
  }

  public void setAccessTime(String accessTime) {
    this.accessTime = accessTime;
  }

  public String getServiceType() {
    return serviceType;
  }

  public void setServiceType(String clientType) {
    this.serviceType = clientType;
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

  public String getAccessResult() {
    return accessResult;
  }

  public void setAccessResult(String accessResult) {
    this.accessResult = accessResult;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPluginIp() {
    return pluginIp;
  }

  public void setPluginIp(String pluginIp) {
    this.pluginIp = pluginIp;
  }

  public String getServiceId() {
    return serviceId;
  }

  public void setServiceId(String serviceId) {
    this.serviceId = serviceId;
  }

  public String getLogType() {
    return logType;
  }

  public void setLogType(String logType) {
    this.logType = logType;
  }

  public String getAclEnforcer() {
    return aclEnforcer;
  }

  public void setAclEnforcer(String aclEnforcer) {
    this.aclEnforcer = aclEnforcer;
  }

  public String getAgentHostname() {
    return agentHostname;
  }

  public void setAgentHostname(String agentHostname) {
    this.agentHostname = agentHostname;
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

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
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

  public String getClientType() {
    return clientType;
  }

  public void setClientType(String clientType) {
    this.clientType = clientType;
  }

  @Override
  public String toString() {
    return "AuditVo{" +
        "id='" + id + '\'' +
        ", cmpyId='" + cmpyId + '\'' +
        ", pluginIp='" + pluginIp + '\'' +
        ", clientIPAddress='" + clientIPAddress + '\'' +
        ", accessType='" + accessType + '\'' +
        ", path='" + resourcePath + '\'' +
        ", user='" + user + '\'' +
        ", userGroups=" + userGroups +
        ", accessTime='" + accessTime + '\'' +
        ", serviceType='" + serviceType + '\'' +
        ", action='" + action + '\'' +
        ", isAllowed='" + isAllowed + '\'' +
        '}';
  }
}