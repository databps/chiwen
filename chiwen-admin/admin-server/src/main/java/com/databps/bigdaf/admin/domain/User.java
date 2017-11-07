package com.databps.bigdaf.admin.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.springframework.data.annotation.Id;

public class User implements Serializable {

  /**
   * knox认证使用用户
   */
  private static final long serialVersionUID = 189889L;
  @Id
  private String _id;//

  private String uname;// 用户名

  private String password;// 密码

  private String status;// 1启用 0禁用

  private String create_time;// 创建时间

  private String update_time;// 更新时间

  private String description;

  private String type;

  private String cmpy_id;

  private String white_ip;

  private List<Map<String, String>> roles;

  private List<String> roleNameList;


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getWhite_ip() {
    return white_ip;
  }

  public void setWhite_ip(String white_ip) {
    this.white_ip = white_ip;
  }

  public String getCmpy_id() {
    return cmpy_id;
  }

  public void setCmpy_id(String cmpy_id) {
    this.cmpy_id = cmpy_id;
  }

  public List<String> getRoleNameList() {
    return roleNameList;
  }

  public void setRoleNameList(List<String> roleNameList) {
    this.roleNameList = roleNameList;
  }

  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  public String getUname() {
    return uname;
  }

  public void setUname(String uname) {
    this.uname = uname;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getCreate_time() {
    return create_time;
  }

  public void setCreate_time(String create_time) {
    this.create_time = create_time;
  }

  public String getUpdate_time() {
    return update_time;
  }

  public void setUpdate_time(String update_time) {
    this.update_time = update_time;
  }

  public List<Map<String, String>> getRoles() {
    return roles;
  }

  public void setRoles(List<Map<String, String>> roles) {
    this.roles = roles;
  }

}
