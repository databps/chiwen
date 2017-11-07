package com.databps.bigdaf.admin.domain;

import java.io.Serializable;
import java.util.List;

import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by yyh on 17-7-15.
 */
public class Services implements Serializable {

  private static final long serialVersionUID = -2655008502507593029L;
  @Id
  private String _id;
  @Field("cmpy_id")
  private String cmpyId;
  @Field("name")
  private String name;
  @Field("create_time")
  private String createTime;
  @Field("update_time")
  private String updateTime;
  @Field("plugins")
  private List<Plugins> plugins;
  @Field("service_config")
  private Map<String,String> serviceConfig;

  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  public String getCmpyId() {
    return cmpyId;
  }

  public void setCmpyId(String cmpyId) {
    this.cmpyId = cmpyId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(String updateTime) {
    this.updateTime = updateTime;
  }

  public List<Plugins> getPlugins() {
    return plugins;
  }

  public void setPlugins(List<Plugins> plugins) {
    this.plugins = plugins;
  }

  public Map<String, String> getServiceConfig() {
    return serviceConfig;
  }

  public void setServiceConfig(Map<String, String> serviceConfig) {
    this.serviceConfig = serviceConfig;
  }
}
