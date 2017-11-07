package com.databps.bigdaf.admin.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by yyh on 17-7-15.
 */
@CompoundIndexes({
    @CompoundIndex(name = "access_time_-1_is_allowed_1", def = "{'is_allowed': 1, 'access_time': -1}")
})
public class Audit implements Serializable {

  private static final long serialVersionUID = 3857998043910573220L;

  @Id
  private String id;

  @Field("cmpy_id")
  private String cmpyId ="5968802a01cbaa46738eee3d";

  @Field("client_ip_address")
  private String clientIPAddress;

  @Field("access_type")
  private String accessType;

  @Field("plugin_ip")
  private String pluginIp;

  @Field("resource_path")
  private String resourcePath;

  @Field("resource_type")
  private String resourceType;


  @Field("user")
  private String user;

  @Field("user_groups")
  private Set<String> userGroups=new HashSet<>();

  @Field("access_time")
  private String accessTime;

  @Field("create_time")
  private String createTime;

  @Field("service_type")
  private String serviceType;

  @Field("service_id")
  private String serviceId;


  @Field("action")
  private String action ;

  @Field("log_type")
  private String logType;

  @Field("acl_enforcer")
  private String aclEnforcer;

  @Field("agent_hostname")
  private String agentHostname;

  @Field("event_id")
  private String eventId = null;

  @Field("seq_num")
  private long seqNum = 0;

  @Field("access_result")
  private String accessResult;

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

  public String getResourceType() {
    return resourceType;
  }

  public void setResourceType(String resourceType) {
    this.resourceType = resourceType;
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

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getServiceType() {
    return serviceType;
  }

  public void setServiceType(String serviceType) {
    this.serviceType = serviceType;
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

  public String getAccessResult() {
    return accessResult;
  }

  public void setAccessResult(String accessResult) {
    this.accessResult = accessResult;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
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
}
