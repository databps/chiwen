package com.databps.bigdaf.admin.domain;

import java.io.Serializable;
import org.springframework.data.annotation.Id;

public class Company implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = -255098218785176665L;

  @Id
  private String _id;

  private String name;

  private String sn;

  private String phone;

  private String email;

  private String update_time;

  public String getUpdate_time() {
    return update_time;
  }

  public void setUpdate_time(String update_time) {
    this.update_time = update_time;
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

  public String getSn() {
    return sn;
  }

  public void setSn(String sn) {
    this.sn = sn;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}
