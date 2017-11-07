package com.databps.bigdaf.chiwen.model;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Created by lgc on 17-7-17.
 */
@JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class Access {

  private String type ;        //read,write,execute

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
