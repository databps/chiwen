package com.databps.bigdaf.admin.vo;


/**
 * @author shibingxin
 * @create 2017-08-18 下午2:07
 */
public class LogQueryVo {

  private String startDate;
  private String endDate;
  private String accessType;
  private String result;

  public void setServiceType(String serviceType) {
    this.serviceType = serviceType;
  }

  private String username;
  private String serviceType;

  public String getServiceType() {
    return serviceType;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getAccessType() {
    return accessType;
  }

  public void setAccessType(String accessType) {
    this.accessType = accessType;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

}