package com.databps.bigdaf.admin.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @author shibingxin
 * @create 2017-08-02 下午5:39
 */
public class AuditStatisticsGroup implements Serializable {

  private String auditType;

  private List<AuditStatistics> statistics;

  public String getAuditType() {
    return auditType;
  }

  public void setAuditType(String auditType) {
    this.auditType = auditType;
  }

  public List<AuditStatistics> getStatistics() {
    return statistics;
  }

  public void setStatistics(List<AuditStatistics> statistics) {
    this.statistics = statistics;
  }
}