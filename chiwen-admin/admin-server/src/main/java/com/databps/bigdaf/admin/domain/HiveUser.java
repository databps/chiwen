package com.databps.bigdaf.admin.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author wanghaiepng
 * @create 2017-08-31 下午1:40
 */
public class HiveUser implements Serializable {

  @Field("cmpy_id")
  private String cmpyId;
  @Id
  private String _id;
  @Field("name")
  private String name;
  @Field("create_time")
  private String createTime;
  @Field("description")
  private String description;
  private List<Map<String, String>> roles;

  public List<Map<String, String>> getRoles() {
    return roles;
  }

  public void setRoles(List<Map<String, String>> roles) {
    this.roles = roles;
  }

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
