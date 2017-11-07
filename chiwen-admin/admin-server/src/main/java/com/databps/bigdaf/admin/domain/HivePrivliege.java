package com.databps.bigdaf.admin.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

/**
 * @author wanghaipeng
 * @create 2017-08-31 下午1:40
 */
public class HivePrivliege implements Serializable{

  @Id
  private String _id;
  @Field("cmpy_id")
  private String cmpyId;
  @Field("name")
  private String name;
  @Field("resource")
  private String resource;
  @Field("roles_name")
  private List<String> rolesName;
  @Field("permissions")
  private List<String> permissions;
  @Field("create_time")
  private String createTime;
  @Field("description")
  private String description;

  public String getCmpyId() {
    return cmpyId;
  }

  public void setCmpyId(String cmpyId) {
    this.cmpyId = cmpyId;
  }

  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getResource() {
    return resource;
  }

  public void setResource(String resource) {
    this.resource = resource;
  }

  public List<String> getRolesName() {
    return rolesName;
  }

  public void setRolesName(List<String> rolesName) {
    this.rolesName = rolesName;
  }


  public List<String> getPermissions() {
    return permissions;
  }

  public void setPermissions(List<String> permissions) {
    this.permissions = permissions;
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