package com.databps.bigdaf.admin.vo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author shibingxin
 * @create 2017-08-07 下午3:37
 */
public class PluginsVo {

  private String cmpyId ;
  private String name;
  private String id;
  private String hostname;
  private String updateTime;
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