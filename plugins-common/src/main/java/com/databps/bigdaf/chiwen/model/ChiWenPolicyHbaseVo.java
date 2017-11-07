package com.databps.bigdaf.chiwen.model;

import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class ChiWenPolicyHbaseVo implements java.io.Serializable{

  private static final long serialVersionUID = -3355133922294049500L;
  private String serviceType;
  private String serviceId;
  private Long lastVersion=1L;
  private String updateTime;
  private Boolean isEnabled;

  private String type;

  private List<HbaseRoleResponse> roles=new ArrayList<>();

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

  public Long getLastVersion() {
    return lastVersion;
  }

  public void setLastVersion(Long lastVersion) {
    this.lastVersion = lastVersion;
  }

  public String getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(String updateTime) {
    this.updateTime = updateTime;
  }

  public Boolean getEnabled() {
    return isEnabled;
  }

  public void setEnabled(Boolean enabled) {
    isEnabled = enabled;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public List<HbaseRoleResponse> getRoles() {
    return roles;
  }

  public void setRoles(List<HbaseRoleResponse> roles) {
    this.roles = roles;
  }

}
