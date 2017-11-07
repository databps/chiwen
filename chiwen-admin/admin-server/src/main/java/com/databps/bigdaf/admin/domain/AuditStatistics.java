package com.databps.bigdaf.admin.domain;

import java.io.Serializable;

/**
 * 组件统计
 * Created by yyh on 17-7-15.
 */
public class AuditStatistics implements Serializable,Comparable<AuditStatistics> {

  private int count;
  private String accessType;
  private String dateTime;
  private String isAllowed;


  public String getIsAllowed() {
    return isAllowed;
  }

  public void setIsAllowed(String isAllowed) {
    this.isAllowed = isAllowed;
  }

  public AuditStatistics() {
  }

  public AuditStatistics(int count, String accessType, String dateTime) {
    this.count = count;
    this.accessType = accessType;
    this.dateTime = dateTime;
  }

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

  @Override
  public int compareTo(AuditStatistics o) {
    int dateTimeInteger = Integer.parseInt(dateTime);
    int oDateTimeInteger = Integer.parseInt(o.getDateTime());
    if(dateTimeInteger <oDateTimeInteger)
      return -1;
    else if(dateTimeInteger > oDateTimeInteger)
      return 1;
    return 0;
  }
}
