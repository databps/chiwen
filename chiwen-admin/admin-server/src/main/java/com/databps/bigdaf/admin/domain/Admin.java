package com.databps.bigdaf.admin.domain;

import java.io.Serializable;
import org.springframework.data.annotation.Id;

public class Admin implements Serializable {

  private static final long serialVersionUID = 18983333389L;
  @Id
  private String _id;//
  private String uname;
  private String sso_id;
  private String cmpy_id;// 公司id
  private String level;// 级别
  private String description;
  private String update_time;

  public String getUpdate_time() {
    return update_time;
  }

  public void setUpdate_time(String update_time) {
    this.update_time = update_time;
  }

  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
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

  public String getSso_id() {
    return sso_id;
  }

  public void setSso_id(String sso_id) {
    this.sso_id = sso_id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCmpy_id() {
    return cmpy_id;
  }

  public void setCmpy_id(String cmpy_id) {
    this.cmpy_id = cmpy_id;
  }

}
