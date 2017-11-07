package com.databps.bigdaf.admin.vo;

import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * HdpAppUserVo
 *
 * @author lgc
 * @create 2017-08-10 下午2:23
 */
public class GatewayUserVo {
  public String getOwnRoles() {
    return ownRoles;
  }

  public void setOwnRoles(String ownRoles) {
    this.ownRoles = ownRoles;
  }

  private String id;


  @NotNull
  @Size(min = 1, max = 50)
  private String username;


  public String getGatewayOrHdfs() {
    return gatewayOrHdfs;
  }

  public void setGatewayOrHdfs(String gatewayOrHdfs) {
    this.gatewayOrHdfs = gatewayOrHdfs;
  }

  @NotNull
  @Size(min = 1, max = 250)

  private String description;

  private String  ownRoles;
  private String createTime;
  private String gatewayOrHdfs;

  public String getCmpyId() {
    return cmpyId;
  }

  public void setCmpyId(String cmpyId) {
    this.cmpyId = cmpyId;
  }

  @Field("cmpy_id")
  private String cmpyId;
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getDescription() {
    return description;
  }

  public Set<String> getUsedRoles() {
    return usedRoles;
  }

  public void setUsedRoles(Set<String> usedRoles) {
    this.usedRoles = usedRoles;
  }

  public Set<String> getNotUsedRoles() {
    return notUsedRoles;
  }

  public void setNotUsedRoles(Set<String> notUsedRoles) {
    this.notUsedRoles = notUsedRoles;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }
  //fangzhimeng
  private String UPassword;

  public String getUPassword() {
    return UPassword;
  }

  public void setUPassword(String UPassword) {
    this.UPassword = UPassword;
  }
  public List<Map<String, String>> getRoles() {
    return roles;
  }

  public void setRoles(List<Map<String, String>> roles) {
    this.roles = roles;
  }

  private List<Map<String, String>> roles;
  private Set<String> usedRoles = new HashSet<String>();
  private Set<String> notUsedRoles = new HashSet<String>();


}