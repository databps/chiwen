package com.databps.bigdaf.admin.domain;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by yyh on 17-7-15.
 */
public class Plugins implements Serializable {

  @Field("cmpy_id")
  private String cmpyId ;
  @Field("name")
  private String name;
  @Id
  private String id;
  @Field("hostname")
  private String hostname;
  @Field("update_time")
  private String updateTime;
  @Field("status")
  private String status;

  public String getCmpyId() {
    return cmpyId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setCmpyId(String cmpyId) {
    this.cmpyId = cmpyId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getHostname() {
    return hostname;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  public String getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(String updateTime) {
    this.updateTime = updateTime;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }


}
