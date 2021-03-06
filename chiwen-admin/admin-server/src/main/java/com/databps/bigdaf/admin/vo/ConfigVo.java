package com.databps.bigdaf.admin.vo;

import java.io.Serializable;

/**
 * @author shibingxin
 * @create 2017-08-30 下午1:51
 */
public class ConfigVo implements Serializable {

  private String cmpyId ;
  private String testMode;
  private int loginMaxNumber;
  private int loginIntervalTime;
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