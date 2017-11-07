package com.databps.bigdaf.chiwen.model;

import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ChiWenPolicy implements java.io.Serializable{

  String pluginUid;
  Long lastVersion;
  Date updateTime;
  boolean isEnabled;
  List<ChiWenPrivilege> privileges;


  public String getPluginUid() {
    return pluginUid;
  }

  public void setPluginUid(String pluginUid) {
    this.pluginUid = pluginUid;
  }

  public Long getLastVersion() {
    return lastVersion;
  }

  public void setLastVersion(Long lastVersion) {
    this.lastVersion = lastVersion;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public boolean isEnabled() {
    return isEnabled;
  }

  public void setEnabled(boolean enabled) {
    isEnabled = enabled;
  }

  public List<ChiWenPrivilege> getPrivileges() {
    return privileges;
  }

  public void setPrivileges(List<ChiWenPrivilege> privileges) {
    this.privileges = privileges;
  }
}
