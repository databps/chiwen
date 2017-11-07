package com.databps.bigdaf.admin.vo;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author shibingxin
 * @create 2017-08-07 下午4:15
 */
public class ServiceHdfsConfigVo extends ServiceConfigVo{

  private String confId;
  private String hdfsHttpUrls;
  private String hdfsRpcUrls;
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

  public String getConfId() {
    return confId;
  }

  public void setConfId(String confId) {
    this.confId = confId;
  }

  public String getHdfsHttpUrls() {
    return hdfsHttpUrls;
  }

  public void setHdfsHttpUrls(String hdfsHttpUrls) {
    this.hdfsHttpUrls = hdfsHttpUrls;
  }

  public String getHdfsRpcUrls() {
    return hdfsRpcUrls;
  }

  public void setHdfsRpcUrls(String hdfsRpcUrls) {
    this.hdfsRpcUrls = hdfsRpcUrls;
  }
}