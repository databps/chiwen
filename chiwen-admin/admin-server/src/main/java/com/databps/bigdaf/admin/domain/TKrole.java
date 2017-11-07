package com.databps.bigdaf.admin.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.springframework.data.annotation.Id;

public class TKrole implements Serializable {

  /**
   * knox认证使用角色
   */
  private static final long serialVersionUID = 10000099L;
  @Id
  private String _id;
  private String name;
  private String description;
  private String create_time;
  private String status;
  private String cmpy_id;
  private String type;// hdfs,hive,hbase
  private List<Map<String, Object>> privileges;

  private String countUser;
  private String rolename;

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

  public String getCmpy_id() {
    return cmpy_id;
  }

  public void setCmpy_id(String cmpy_id) {
    this.cmpy_id = cmpy_id;
  }

  public String getCountUser() {
    return countUser;
  }

  public void setCountUser(String countUser) {
    this.countUser = countUser;
  }

  public List<Map<String, Object>> getPrivileges() {
    return privileges;
  }

  public void setPrivileges(List<Map<String, Object>> privileges) {
    this.privileges = privileges;
  }

  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  public String getRolename() {
    return rolename;
  }

  public void setRolename(String rolename) {
    this.rolename = rolename;
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
