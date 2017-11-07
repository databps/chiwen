package com.databps.bigdaf.admin.vo;

import com.databps.bigdaf.admin.domain.Plugins;
import com.databps.bigdaf.admin.domain.ServicesConfig;
import java.util.List;
import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author shibingxin
 * @create 2017-08-07 下午3:26
 */
public class ServicesVo {

  private String _id;
  private String cmpyId;
  private String name;
  private String createTime;
  private String updateTime;
  private int status;
  private List<PluginsVo> plugins;
  private Map<String,String> serviceConfig;
  private ServicesJMXVo servicesJMXVo;

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

  public List<PluginsVo> getPlugins() {
    return plugins;
  }

  public void setPlugins(List<PluginsVo> plugins) {
    this.plugins = plugins;
  }

  public Map<String, String> getServiceConfig() {
    return serviceConfig;
  }

  public void setServiceConfig(Map<String, String> serviceConfig) {
    this.serviceConfig = serviceConfig;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public ServicesJMXVo getServicesJMXVo() {
    return servicesJMXVo;
  }

  public void setServicesJMXVo(ServicesJMXVo servicesJMXVo) {
    this.servicesJMXVo = servicesJMXVo;
  }

}