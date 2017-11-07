package com.databps.bigdaf.admin.domain;

import java.io.Serializable;

/**
 * Created by yyh on 17-7-15.
 */
public class AuditDateCount implements Serializable {

  private int count;
  private String date_time;

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public String getDate_time() {
    return date_time;
  }

  public void setDate_time(String date_time) {
    this.date_time = date_time;
  }
}
