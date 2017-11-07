package com.databps.bigdaf.admin.domain;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by yyh on 17-7-19.
 */
public class ServicesHBaseConfig extends ServicesConfig {

  @Field("hbase_http_url")
  private String hbaseHttpUrls;

  public String getHbaseHttpUrls() {
    return hbaseHttpUrls;
  }

  public void setHbaseHttpUrls(String hbaseHttpUrls) {
    this.hbaseHttpUrls = hbaseHttpUrls;
  }
}
