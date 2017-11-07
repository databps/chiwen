package com.databps.bigdaf.admin.vo;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;

/**
 * @author shibingxin
 * @create 2017-08-18 下午3:06
 */
public class PersistentAuditEventVo implements Serializable {

  private String id;
  private String principal;
  private String auditEventDate;
  private String auditEventType;
  private Map<String, String> data =null;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPrincipal() {
    return principal;
  }

  public void setPrincipal(String principal) {
    this.principal = principal;
  }

  public String getAuditEventDate() {
    return auditEventDate;
  }

  public void setAuditEventDate(String auditEventDate) {
    this.auditEventDate = auditEventDate;
  }

  public String getAuditEventType() {
    return auditEventType;
  }

  public void setAuditEventType(String auditEventType) {
    this.auditEventType = auditEventType;
  }

  public Map<String, String> getData() {
    return data;
  }

  public void setData(Map<String, String> data) {
    this.data = data;
  }
}