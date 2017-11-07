package com.databps.bigdaf.admin.vo;

/**
 * 首页查询
 *
 * @author shibingxin
 * @create 2017-08-05 下午2:06
 */
public class HomePageQueryVo {
  private String auditDateType;
  private String startTime;
  private String endTime;
  private String hdfsDefaultName;
  private String yarnRMHostname;
  private String mapreduceJobHostname;

  public String getHdfsDefaultName() {
    return hdfsDefaultName;
  }

  public void setHdfsDefaultName(String hdfsDefaultName) {
    this.hdfsDefaultName = hdfsDefaultName;
  }

  public String getYarnRMHostname() {
    return yarnRMHostname;
  }

  public void setYarnRMHostname(String yarnRMHostname) {
    this.yarnRMHostname = yarnRMHostname;
  }

  public String getMapreduceJobHostname() {
    return mapreduceJobHostname;
  }

  public void setMapreduceJobHostname(String mapreduceJobHostname) {
    this.mapreduceJobHostname = mapreduceJobHostname;
  }

  public String getAuditDateType() {
    return auditDateType;
  }

  public void setAuditDateType(String auditDateType) {
    this.auditDateType = auditDateType;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }
}