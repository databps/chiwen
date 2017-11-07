package com.databps.bigdaf.admin.vo;


import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;

/**
 * A DTO representing a user, with his authorities.
 */
public class AdminVo {

  private String id;

  @Size(min = 1,max = 150)
  private String login;


  @Email
  private String email;

  private String createdDate;

  private String password;


  private String lastModifiedDate;


  public AdminVo() {
    // Empty constructor needed for Jackson.
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }

  public String getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(String lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
