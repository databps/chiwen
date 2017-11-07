package com.databps.bigdaf.admin.domain;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by yyh on 17-7-19.
 */
public class ServiceHdfsConfig extends ServicesConfig {

  @Field("hdfs_http_url")
  private String hdfsHttpUrls;
  @Field("hdfs_rpc_url")
  private String hdfsRpcUrls;
  @Field("hdfs_default_name")
  private String hdfsDefaultName;
  @Field("yarn_rm_hostname")
  private String yarnRMHostname;
  @Field("mapreduce_job_hostname")
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
