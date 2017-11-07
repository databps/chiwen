package com.databps.bigdaf.chiwen.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Created by lgc on 17-7-17.
 */
@JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class ChiWenPrivilege implements java.io.Serializable{

  private static final long serialVersionUID = 405405541171215276L;
  private String name;
  private Resources resources;
  private List<ChiWenPrivilegeItem> privilegeItems;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Resources getResources() {
    return resources;
  }

  public void setResources(Resources resources) {
    this.resources = resources;
  }

  public List<ChiWenPrivilegeItem> getPrivilegeItems() {
    return privilegeItems;
  }

  public void setPrivilegeItems(
      List<ChiWenPrivilegeItem> privilegeItems) {
    this.privilegeItems = privilegeItems;
  }
}
