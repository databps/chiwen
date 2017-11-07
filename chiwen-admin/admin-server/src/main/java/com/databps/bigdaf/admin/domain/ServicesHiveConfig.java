package com.databps.bigdaf.admin.domain;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by yyh on 17-7-19.
 */
public class ServicesHiveConfig extends ServicesConfig {

  @Field("hive_http_url")
  private String hiveHttpUrls;

  public String getHiveHttpUrls() {
    return hiveHttpUrls;
  }

  public void setHiveHttpUrls(String hiveHttpUrls) {
    this.hiveHttpUrls = hiveHttpUrls;
  }
}
