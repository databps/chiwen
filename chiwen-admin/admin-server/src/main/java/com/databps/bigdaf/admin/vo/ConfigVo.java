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
  private String hdfs_strategy;
  private String hbase_strategy;
  private String gateway_strategy;
  private String hive_strategy;


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

  public String getHdfs_strategy() {
    return hdfs_strategy;
  }

  public void setHdfs_strategy(String hdfs_strategy) {
    this.hdfs_strategy = hdfs_strategy;
  }

  public String getHbase_strategy() {
    return hbase_strategy;
  }

  public void setHbase_strategy(String hbase_strategy) {
    this.hbase_strategy = hbase_strategy;
  }

  public String getGateway_strategy() {
    return gateway_strategy;
  }

  public void setGateway_strategy(String gateway_strategy) {
    this.gateway_strategy = gateway_strategy;
  }

  public String getHive_strategy() {
    return hive_strategy;
  }

  public void setHive_strategy(String hive_strategy) {
    this.hive_strategy = hive_strategy;
  }
}