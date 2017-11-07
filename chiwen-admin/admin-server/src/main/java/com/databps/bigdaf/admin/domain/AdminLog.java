package com.databps.bigdaf.admin.domain;

import java.io.Serializable;

public class AdminLog implements Serializable {

  /**
   * hdfs资源控制
   */
  private static final long serialVersionUID = 56262231L;

  private String uname;
  private String ip;
  private String access;
  private String result;
  private String message;
  private String create_time;
  private String cmpy_id;

  public String getAccess() {
    return access;
  }

  public void setAccess(String access) {
    this.access = access;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getCmpy_id() {
    return cmpy_id;
  }

  public void setCmpy_id(String cmpy_id) {
    this.cmpy_id = cmpy_id;
  }

  public String getUname() {
    return uname;
  }

  public void setUname(String uname) {
    this.uname = uname;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public String getCreate_time() {
    return create_time;
  }

  public void setCreate_time(String create_time) {
    this.create_time = create_time;
  }

  public static String getHeaderAccess() {
    StringBuffer sb = new StringBuffer();
    sb.append("用户名称").append(",");
    sb.append("访问ip").append(",");
    sb.append("操作").append(",");
    sb.append("操作结果").append(",");
    sb.append("操作时间").append("\r\n");
    return sb.toString();
  }


  public String toString() {
    String f = ",";
    return this.uname + f + this.ip + f + this.access + f + this.result + f + this.create_time
        + "\r\n";
  }
}
