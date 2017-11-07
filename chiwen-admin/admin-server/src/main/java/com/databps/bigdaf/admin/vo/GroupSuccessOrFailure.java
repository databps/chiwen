package com.databps.bigdaf.admin.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author merlin
 * @create 2017-08-12 下午8:13
 */
public class GroupSuccessOrFailure implements Serializable {

  private static final long serialVersionUID = 4483963144853877457L;

  private String auditType;

  private List<String> counts;

  private List<String> times;

  public GroupSuccessOrFailure() {
  }

  public String getAuditType() {
    return auditType;
  }

  public void setAuditType(String auditType) {
    this.auditType = auditType;
  }

  public List<String> getCounts() {
    return counts;
  }

  public void setCounts(List<String> counts) {
    this.counts = counts;
  }

  public List<String> getTimes() {
    return times;
  }

  public void setTimes(List<String> times) {
    this.times = times;
  }

}
