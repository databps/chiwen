package com.databps.bigdaf.admin.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class GatewayCharacter implements Serializable {

  /**
   * knox认证使用角色
   */
  private static final long serialVersionUID = 10000099L;
  @Id
  private String _id;

  public String getCmpyId() {
    return cmpyId;
  }

  public void setCmpyId(String cmpyId) {
    this.cmpyId = cmpyId;
  }

  @Field("cmpy_id")
  private String cmpyId;
  private String name;
  private String description;
  private String create_time;
  private String status;
  private String type;// hdfs,hive,hbase
  private List<Map<String, String>> privileges;
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  public List<Map<String, String>> getPrivileges() {
    return privileges;
  }

  public void setPrivileges(List<Map<String, String>> privileges) {
    this.privileges = privileges;
  }

  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCreate_time() {
    return create_time;
  }

  public void setCreate_time(String create_time) {
    this.create_time = create_time;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
