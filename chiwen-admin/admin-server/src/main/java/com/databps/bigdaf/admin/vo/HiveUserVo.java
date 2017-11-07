package com.databps.bigdaf.admin.vo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author shibingxin
 * @create 2017-08-31 下午1:40
 */
public class HiveUserVo implements Serializable {
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Field("cmpy_id")
  private String cmpyId;

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

  @Id
  private String id;
  @Field("name")
  private String name;
  @Field("create_time")
  private String createTime;
  @Field("description")
  private String description;
  private Set<String> usedRoles = new HashSet<String>();
  private String roles ;

  public String getRoles() {
    return roles;
  }

  public void setRoles(String roles) {
    this.roles = roles;
  }

  private Set<String> notUsedRoles = new HashSet<String>();

  public String getCmpyId() {
    return cmpyId;
  }

  public void setCmpyId(String cmpyId) {
    this.cmpyId = cmpyId;
  }



  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
