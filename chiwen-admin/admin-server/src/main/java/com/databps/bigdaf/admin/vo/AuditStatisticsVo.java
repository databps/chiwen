package com.databps.bigdaf.admin.vo;

import java.io.Serializable;

/**
 * @author shibingxin
 * @create 2017-08-03 上午9:59
 */
public class AuditStatisticsVo implements Serializable {

  private int count;
  private String accessType;
  private String dateTime;

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public String getAccessType() {
    return accessType;
  }

  public void setAccessType(String accessType) {
    this.accessType = accessType;
  }

  public String getDateTime() {
    return dateTime;
  }

  public void setDateTime(String dateTime) {
    this.dateTime = dateTime;
  }
}