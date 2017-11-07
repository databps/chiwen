package com.databps.bigdaf.admin.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * HdpAppUserVo
 *
 * @author lgc
 * @create 2017-08-10 下午2:23
 */
public class PrivilegeVo {

  private String id;


  @NotNull
  @Size(min = 1, max = 50)
  private String name;

  @NotNull
  @Size(min = 1, max = 250)
  private String description;

  @NotNull
  @Size(min = 1, max = 250)
  private String path;

  private String[] accesses;

  private String createTime;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String[] getAccesses() {
    return accesses;
  }

  public void setAccesses(String[] accesses) {
    this.accesses = accesses;
  }
}