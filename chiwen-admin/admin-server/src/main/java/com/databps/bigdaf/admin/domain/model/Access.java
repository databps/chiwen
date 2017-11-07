package com.databps.bigdaf.admin.domain.model;

/**
 * Created by lgc on 17-7-17.
 */
public class Access {

  private String type ;        //read,write,execute

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
