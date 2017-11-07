package com.databps.bigdaf.admin.domain;


import java.util.List;
import org.springframework.data.annotation.Id;

/**
 * @author merlin
 * @create 2017-08-14 下午4:38
 */
public class Policy {

  @Id
  private String id;

  private String type;


  private List<Privilege> privileges;

  private String createTime;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public List<Privilege> getPrivileges() {
    return privileges;
  }

  public void setPrivileges(List<Privilege> privileges) {
    this.privileges = privileges;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


}
