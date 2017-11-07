package com.databps.bigdaf.admin.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author shibingxin
 * @create 2017-08-03 上午9:58
 */
public class AuditStatisticsGroupVo implements Serializable {
  private String auditType;

  private List<AuditStatisticsVo> statistics;

  public String getAuditType() {
    return auditType;
  }

  public void setAuditType(String auditType) {
    this.auditType = auditType;
  }

  public List<AuditStatisticsVo> getStatistics() {
    return statistics;
  }

  public void setStatistics(List<AuditStatisticsVo> statistics) {
    this.statistics = statistics;
  }
}