package com.databps.bigdaf.admin.domain;

import java.io.Serializable;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by yyh on 17-7-15.
 */
public class Config implements Serializable {

  @Field("cmpy_id")
  private String cmpyId ;
  @Field("test_mode")
  private String testMode;
  @Field("login_max_number")
  private int loginMaxNumber;
  @Field("login_interval_time")
  private int loginIntervalTime;
  @Field("kerberos_enable")
  private int kerberosEnable;

  public String getCmpyId() {
    return cmpyId;
  }

  public void setCmpyId(String cmpyId) {
    this.cmpyId = cmpyId;
  }

  public String getTestMode() {
    return testMode;
  }

  public void setTestMode(String testMode) {
    this.testMode = testMode;
  }

  public int getLoginMaxNumber() {
    return loginMaxNumber;
  }

  public void setLoginMaxNumber(int loginMaxNumber) {
    this.loginMaxNumber = loginMaxNumber;
  }

  public int getLoginIntervalTime() {
    return loginIntervalTime;
  }

  public void setLoginIntervalTime(int loginIntervalTime) {
    this.loginIntervalTime = loginIntervalTime;
  }

  public int getKerberosEnable() {
    return kerberosEnable;
  }

  public void setKerberosEnable(int kerberosEnable) {
    this.kerberosEnable = kerberosEnable;
  }
}
