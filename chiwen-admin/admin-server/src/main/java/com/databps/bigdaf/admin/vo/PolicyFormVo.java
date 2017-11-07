package com.databps.bigdaf.admin.vo;

/**
 * PolicyVo
 *
 * @author lgc
 * @create 2017-08-08 下午1:43
 */
public class PolicyFormVo {

  private String id;

  private String name;

  private String path;

  private String accesses;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getAccesses() {
    return accesses;
  }

  public void setAccesses(String accesses) {
    this.accesses = accesses;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}