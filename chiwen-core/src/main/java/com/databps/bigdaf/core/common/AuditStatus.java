package com.databps.bigdaf.core.common;

import com.sun.org.apache.bcel.internal.classfile.Unknown;

/**
 * @author shibingxin
 * @create 2017-08-03 下午5:01
 */
public enum AuditStatus {
  SUCCESS("success"),
  FAILURE("failure"),
  UNKOWN("unknown"),;

  private String name;

  AuditStatus(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}