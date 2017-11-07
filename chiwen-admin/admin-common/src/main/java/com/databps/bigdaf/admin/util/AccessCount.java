package com.databps.bigdaf.admin.util;

import com.databps.bigdaf.core.common.AuditStatus;

public enum AccessCount {
  TODAY_ACCESS_COUNT("today_access_count",null),
  IP_TOTAL_COUNT("ip_total_count",null),
  IP_FAILURE_COUNT("ip_failure_count", AuditStatus.FAILURE.getName()),
  TODAY_FAILURE_COUNT("today_failure_count",AuditStatus.FAILURE.getName()),
  IP_SUCCESS_COUNT("ip_success_count",AuditStatus.SUCCESS.getName()),
  ;

  private String name;
  private String allowed;

  AccessCount(String name, String allowed) {
    this.name = name;
    this.allowed = allowed;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAllowed() {
    return allowed;
  }

  public void setAllowed(String allowed) {
    this.allowed = allowed;
  }
}
