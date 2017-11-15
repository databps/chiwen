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
  @Field("hdfs_strategy")
  private Boolean hdfs_strategy;
  @Field("hbase_strategy")
  private Boolean hbase_strategy;
  @Field("gateway_strategy")
  private Boolean gateway_strategy;
  @Field("hive_strategy")
  private Boolean hive_strategy;


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

  public Boolean getHdfs_strategy() {
    return hdfs_strategy;
  }

  public void setHdfs_strategy(Boolean hdfs_strategy) {
    this.hdfs_strategy = hdfs_strategy;
  }

  public Boolean getHbase_strategy() {
    return hbase_strategy;
  }

  public void setHbase_strategy(Boolean hbase_strategy) {
    this.hbase_strategy = hbase_strategy;
  }

  public Boolean getGateway_strategy() {
    return gateway_strategy;
  }

  public void setGateway_strategy(Boolean gateway_strategy) {
    this.gateway_strategy = gateway_strategy;
  }

  public Boolean getHive_strategy() {
    return hive_strategy;
  }

  public void setHive_strategy(Boolean hive_strategy) {
    this.hive_strategy = hive_strategy;
  }

}
