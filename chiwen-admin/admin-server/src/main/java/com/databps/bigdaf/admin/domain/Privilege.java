package com.databps.bigdaf.admin.domain;

import java.util.List;
import java.util.Set;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author merlin
 * @create 2017-08-14 下午4:39
 */
public class Privilege {

  private String name;

  @Field("create_time")
  private String createTime;

  @Field("update_time")
  private String updateTime;

  private String description;

  private Resource resources;

  private List<PrivilegeItems> privilege_items;

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

  public Resource getResources() {
    return resources;
  }

  public void setResources(Resource resources) {
    this.resources = resources;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<PrivilegeItems> getPrivilege_items() {
    return privilege_items;
  }

  public String getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(String updateTime) {
    this.updateTime = updateTime;
  }

  public void setPrivilege_items(
      List<PrivilegeItems> privilege_items) {
    this.privilege_items = privilege_items;
  }

  public static class Resource {
    private Path path;

    public Path getPath() {
      return path;
    }

    public void setPath(Path path) {
      this.path = path;
    }
  }

  public static class Path{
    private String[] values;

    public String[] getValues() {
      return values;
    }

    public void setValues(String[] values) {
      this.values = values;
    }
  }

  public static class PrivilegeItems{
    private Set<String> users;
    private Set<String>  groups;
    private List<Accesse> accesses;

    public Set<String> getUsers() {
      return users;
    }

    public void setUsers(Set<String> users) {
      this.users = users;
    }

    public Set<String> getGroups() {
      return groups;
    }

    public void setGroups(Set<String> groups) {
      this.groups = groups;
    }

    public List<Accesse> getAccesses() {
      return accesses;
    }

    public void setAccesses(List<Accesse> accesses) {
      this.accesses = accesses;
    }
  }

  public static class Accesse{
    private String type;

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }
  }
}
