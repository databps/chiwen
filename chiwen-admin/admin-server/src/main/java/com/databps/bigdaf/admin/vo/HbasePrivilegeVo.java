package com.databps.bigdaf.admin.vo;

import java.util.List;

/**
 * @author merlin
 * @create 2017-11-04 下午1:48
 */
public class HbasePrivilegeVo {


  private String name;
  private String resource;
  private List<String> rolesName;
  private List<String> permissions;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getResource() {
    return resource;
  }

  public void setResource(String resource) {
    this.resource = resource;
  }

  public List<String> getRolesName() {
    return rolesName;
  }

  public void setRolesName(List<String> rolesName) {
    this.rolesName = rolesName;
  }

  public List<String> getPermissions() {
    return permissions;
  }

  public void setPermissions(List<String> permissions) {
    this.permissions = permissions;
  }
}
