package com.databps.bigdaf.admin.util;

/**
 * Created by shibingxin on 2017/8/11.
 */
public enum HadoopJMXType {
  NameNode("qry=Hadoop:service=NameNode,name=FSNamesystemState"),
  Runtime("get=java.lang:type=Runtime::Uptime"),
  Memory("get=java.lang:type=Memory::HeapMemoryUsage"),
  ;
  private String param;


  HadoopJMXType(String param) {
    this.param = param;
  }

  public String getParam() {
    return param;
  }
}
