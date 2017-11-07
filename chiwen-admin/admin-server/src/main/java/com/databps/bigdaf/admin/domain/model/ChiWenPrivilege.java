package com.databps.bigdaf.admin.domain.model;

import java.util.List;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by lgc on 17-7-17.
 */
@JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class ChiWenPrivilege {

  private String name;

  private Resources resources;

  @Field("privilege_items")
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
