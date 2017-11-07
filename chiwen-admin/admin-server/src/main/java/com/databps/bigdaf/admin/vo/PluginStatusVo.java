package com.databps.bigdaf.admin.vo;

import java.io.Serializable;

/**
 * @author shibingxin
 * @create 2017-08-01 下午7:15
 */
public class PluginStatusVo  implements Serializable {

  private String type ;
  private long userCount;
  private long authCount;
  private long auditCount;
  private int status;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public long getUserCount() {
    return userCount;
  }

  public void setUserCount(long userCount) {
    this.userCount = userCount;
  }

  public long getAuthCount() {
    return authCount;
  }

  public void setAuthCount(long authCount) {
    this.authCount = authCount;
  }

  public long getAuditCount() {
    return auditCount;
  }

  public void setAuditCount(long auditCount) {
    this.auditCount = auditCount;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }
}