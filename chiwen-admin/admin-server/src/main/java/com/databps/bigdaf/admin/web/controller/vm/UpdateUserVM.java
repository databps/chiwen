package com.databps.bigdaf.admin.web.controller.vm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author merlin
 * @create 2017-08-30 下午2:18
 */
public class UpdateUserVM {

  @Size(min = 4,max = 100)
  private String oldpassword;

  @Size(min = 4,max = 100)
  private String password;

  @Size(min = 4,max = 100)
  private String newpassword;

  public String getOldpassword() {
    return oldpassword;
  }

  public void setOldpassword(String oldpassword) {
    this.oldpassword = oldpassword;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getNewpassword() {
    return newpassword;
  }

  public void setNewpassword(String newpassword) {
    this.newpassword = newpassword;
  }
}
