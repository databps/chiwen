package com.databps.bigdaf.admin.domain.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ChiWenPolicy implements java.io.Serializable{


  private static final long serialVersionUID = -7066285935735340504L;
  @Id
  private String _id;
  @Field("cmpy_id")
  private String cmpyId;
  @Field("plugin_uid")
  String pluginUid;
  @Field("last_version")
  Long lastVersion;
  @Field("update_time")
  String updateTime;
  @Field("is_enabled")
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

  public String getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(String updateTime) {
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
}
