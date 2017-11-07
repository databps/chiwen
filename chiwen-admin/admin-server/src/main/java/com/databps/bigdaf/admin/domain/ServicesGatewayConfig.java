package com.databps.bigdaf.admin.domain;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 */
public class ServicesGatewayConfig extends ServicesConfig {

  @Field("gateway_http_url")
  private String gatewayHttpUrls;

  public String getGatewayHttpUrls() {
    return gatewayHttpUrls;
  }

  public void setGatewayHttpUrls(String gatewayHttpUrls) {
    this.gatewayHttpUrls = gatewayHttpUrls;
  }
}
