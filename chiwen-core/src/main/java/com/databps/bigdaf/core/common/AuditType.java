package com.databps.bigdaf.core.common;

/**
 * 审计类型
 * @author shibingxin
 * @create 2017-08-01 下午7:38
 */
public enum AuditType {

  HDFS("hdfs"),
  HBASE("hbase"),
  HIVE("hive"),
  GATEWAY("gateway"),
  ;

  private String name ;

  AuditType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}