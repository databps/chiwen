package com.databps.bigdaf.chiwen.policy;

import com.databps.bigdaf.chiwen.policyengine.ChiWenAccessRequestReadOnly;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by lgc on 17-7-20.
 */
public class ChiWenAccessRequestImpl implements ChiWenAccessRequest {

  private ChiWenAccessResource resource;
  private String path = null;
  private String clusterName=null;
  private String accessType = null;
  private String user = null;
  private Set<String> userGroups = null;
  private Date accessTime = null;
  private String clientIPAddress = null;
  private boolean isAccessTypeAny;
  private boolean isAccessTypeDelegatedAdmin;

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  private List<String> forwardedAddresses = null;
  private String remoteIPAddress = null;
  private String clientType = null;
  private String action = null;
  private Map<String, Object> context;
  private String requestData = null;
  private ResourceMatchingScope resourceMatchingScope = ResourceMatchingScope.SELF;
  private String               sessionId;

 public ChiWenAccessRequestImpl() {
   this(null, null, null, null);
  }

//  public ChiWenAccessRequestImpl(ChiWenAccessResource resource, String accessType, String user, Set<String> userGroups) {
//    setResource(resource);
//    setAccessType(accessType);
//    setUser(user);
//    setUserGroups(userGroups);
//    setContext(null);
//  }


  public ChiWenAccessRequestImpl(ChiWenAccessResource resource, String accessType, String user, Set<String> userGroups) {
    setResource(resource);
    setAccessType(accessType);
    setUser(user);
    setUserGroups(userGroups);
    setForwardedAddresses(null);

    // set remaining fields to default value
    setAccessTime(null);
    setRemoteIPAddress(null);
    setClientType(null);
    setAction(null);
    setRequestData(null);
    setSessionId(null);
    setContext(null);
    setClusterName(null);
  }

  public void setResource(ChiWenAccessResource resource) {
    this.resource = resource;
  }

  public ChiWenAccessResource getResource() {
    return resource;
  }

  public String getAccessType() {
    return accessType;
  }

  public void setAccessType(String accessType) {
    this.accessType = accessType;
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

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public void setUserGroups(Set<String> userGroups) {
    this.userGroups = userGroups;
  }

  public Date getAccessTime() {
    return accessTime;
  }

  public void setAccessTime(Date accessTime) {
    this.accessTime = accessTime;
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

  public String getClusterName() {
    return clusterName;
  }

  public void setClusterName(String clusterName) {
    this.clusterName = clusterName;
  }

  @Override
  public Map<String, Object> getContext() {
    return context;
  }

  public void setContext(Map<String, Object> context) {
    this.context = (context == null) ? new HashMap<String, Object>() : context;
  }

  @Override
  public String getRequestData() {
    return requestData;
  }

  public void setRequestData(String requestData) {
    this.requestData = requestData;
  }

  @Override
  public ResourceMatchingScope getResourceMatchingScope() {
    return resourceMatchingScope;
  }

  public void setResourceMatchingScope(
      ResourceMatchingScope resourceMatchingScope) {
    this.resourceMatchingScope = resourceMatchingScope;
  }

  public List<String> getForwardedAddresses() {
    return forwardedAddresses;
  }

  public void setForwardedAddresses(List<String> forwardedAddresses) {
    this.forwardedAddresses = forwardedAddresses;
  }

  public String getRemoteIPAddress() {
    return remoteIPAddress;
  }

  public void setRemoteIPAddress(String remoteIPAddress) {
    this.remoteIPAddress = remoteIPAddress;
  }

  @Override
  public boolean isAccessTypeDelegatedAdmin() {
    return isAccessTypeDelegatedAdmin;
  }

  @Override
  public boolean isAccessTypeAny() {
    return isAccessTypeAny;
  }

  @Override
  public String toString() {
    return "ChiWenAccessRequestImpl{" +
        "resource=" + resource +
        ", path='" + path + '\'' +
        ", clusterName='" + clusterName + '\'' +
        ", accessType='" + accessType + '\'' +
        ", user='" + user + '\'' +
        ", userGroups=" + userGroups +
        ", accessTime=" + accessTime +
        ", clientIPAddress='" + clientIPAddress + '\'' +
        ", forwardedAddresses=" + forwardedAddresses +
        ", remoteIPAddress='" + remoteIPAddress + '\'' +
        ", clientType='" + clientType + '\'' +
        ", action='" + action + '\'' +
        ", context=" + context +
        ", requestData='" + requestData + '\'' +
        ", resourceMatchingScope=" + resourceMatchingScope +
        '}';
  }

  @Override
  public ChiWenAccessRequest getReadOnlyCopy() {
    return new ChiWenAccessRequestReadOnly(this);
  }
}

