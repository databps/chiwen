package com.databps.bigdaf.admin.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * @author shibingxin
 * @create 2017-08-01 下午7:22
 */
public class PluginStatus implements Serializable {
  @Id
  private String id;
  @Field("cmpy_id")
  private String cmpyId ="5968802a01cbaa46738eee3d";
  @Field("type")
  private String type ;
  @Field("user_count")
  private long userCount;
  @Field("auth_count")
  private long authCount;
  @Field("audit_count")
  private long auditCount;
  @Field("status")
  private int status;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCmpyId() {
    return cmpyId;
  }

  public void setCmpyId(String cmpyId) {
    this.cmpyId = cmpyId;
  }



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