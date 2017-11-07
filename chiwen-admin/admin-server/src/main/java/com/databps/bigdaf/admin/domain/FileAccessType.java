package com.databps.bigdaf.admin.domain;

import java.io.Serializable;

/**
 * @author shibingxin
 * @create 2017-08-28 下午5:09
 */
public class FileAccessType implements Serializable ,Comparable<FileAccessType>{

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

  public int compareTo(FileAccessType o) {
    int result=0;
    if(o.getValue() <value)
       result = -1;
    if(o.getValue() > value)
      result =1;
    return result;
  }
}