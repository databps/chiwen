package com.databps.bigdaf.chiwen.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author merlin
 * @create 2017-11-04 下午3:06
 */
public class HbaseRoleResponse {

  private List<String> users=new ArrayList<>();

  private List<HbasePrivilegeVo> privileges=new ArrayList<>();

  public List<String> getUsers() {
    return users;
  }

  public void setUsers(List<String> users) {
    this.users = users;
  }

  public List<HbasePrivilegeVo> getPrivileges() {
    return privileges;
  }

  public void setPrivileges(List<HbasePrivilegeVo> privileges) {
    this.privileges = privileges;
  }
}
