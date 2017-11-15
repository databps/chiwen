package com.databps.bigdaf.admin.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author merlin
 * @create 2017-10-25 下午1:31
 */
public class HivePolicyVo {

  private String serviceType;
  private String serviceId;
  private Long lastVersion=1L;
  private String updateTime;
  private Boolean isEnabled;
  private Boolean isAudited=true;

  public Boolean getAudited() {
    return isAudited;
  }

  public void setAudited(Boolean audited) {
    isAudited = audited;
  }

  private List<HiveRoleResponse> roles=new ArrayList<>();

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


  public List<HiveRoleResponse> getRoles() {
    return roles;
  }

  public void setRoles(List<HiveRoleResponse> roles) {
    this.roles = roles;
  }

  public static class HiveRoleResponse {


    private List<String> users=new ArrayList<>();

    private List<HivePrivilegeVo> privileges=new ArrayList<>();

    public List<String> getUsers() {
      return users;
    }

    public void setUsers(List<String> users) {
      this.users = users;
    }

    public List<HivePrivilegeVo> getPrivileges() {
      return privileges;
    }

    public void setPrivileges(List<HivePrivilegeVo> privileges) {
      this.privileges = privileges;
    }
  }

}
