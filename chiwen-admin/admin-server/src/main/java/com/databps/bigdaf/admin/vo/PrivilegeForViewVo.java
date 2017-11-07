package com.databps.bigdaf.admin.vo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * PrivilegeForViewVo
 *
 * @author fangzhimneg
 * @create 2017-09-01 下午3:50
 */
public class PrivilegeForViewVo {

  private Set<String> usedPrivileges = new HashSet<String>();
  private Set<String> notUsedPrivileges = new HashSet<String>();

  public Set<String> getUsedPrivileges() {
    return usedPrivileges;
  }

  public void setUsedPrivileges(Set<String> usedPrivileges) {
    this.usedPrivileges = usedPrivileges;
  }

  public Set<String> getNotUsedPrivileges() {
    return notUsedPrivileges;
  }

  public void setNotUsedPrivileges(Set<String> notUsedPrivileges) {
    this.notUsedPrivileges = notUsedPrivileges;
  }
}