package com.databps.bigdaf.admin.vo;

import java.io.Serializable;

/**
 * @author shibingxin
 * @create 2017-08-28 下午3:21
 */
public class FileAccessTypeVo implements Serializable {

  private String name;

  private long value ;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getValue() {
    return value;
  }

  public void setValue(long value) {
    this.value = value;
  }
}