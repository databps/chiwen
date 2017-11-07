package com.databps.bigdaf.chiwen.model;

import java.util.List;

/**
 * Created by lgc on 17-7-17.
 */
public class ChiWenPrivilege {
  String name;
  Resources resources;
  List<ChiWenPrivilegeItem> privilegeItems;

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
