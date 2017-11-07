package com.databps.bigdaf.admin.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author shibingxin
 * @create 2017-08-31 下午1:39
 */
public class HbaseRole implements Serializable{

  @Id
  private String _id;
  @Field("cmpy_id")
  private String cmpyId;
  @Field("name")
  private String name;

  public List<Map<String, String>> getPrivileges() {
    return privileges;
  }

  public void setPrivileges(List<Map<String, String>> privileges) {
    this.privileges = privileges;
  }

  @Field("create_time")

  private String createTime;

  public String getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(String updateTime) {
    this.updateTime = updateTime;
  }

  @Field("update_time")
  private String updateTime;


  @Field("description")
  private String description;
  @Field("users_name")
  private List<String> usersName;

  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

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

  public List<String> getUsersName() {
    return usersName;
  }

  public void setUsersName(List<String> usersName) {
    this.usersName = usersName;
  }
  private List<Map<String, String>> privileges;

}