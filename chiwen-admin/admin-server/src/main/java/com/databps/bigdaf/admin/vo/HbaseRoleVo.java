package com.databps.bigdaf.admin.vo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author shibingxin
 * @create 2017-08-31 下午1:39
 */
public class HbaseRoleVo implements Serializable{

  @Id
  private String _id;
  @Field("cmpy_id")
  private String cmpyId;
  @Field("name")
  private String name;

  public Set<String> getUsedHbasePrivilege() {
    return usedHbasePrivilege;
  }

  public void setUsedHbasePrivilege(Set<String> usedHbasePrivilege) {
    this.usedHbasePrivilege = usedHbasePrivilege;
  }

  public Set<String> getNotUsedHbasePrivilege() {
    return notUsedHbasePrivilege;
  }

  public void setNotUsedHbasePrivilege(Set<String> notUsedHbasePrivilege) {
    this.notUsedHbasePrivilege = notUsedHbasePrivilege;
  }

  public String getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(String updateTime) {
    this.updateTime = updateTime;
  }

  @Field("create_time")
  private String createTime;
  @Field("update_time")
  private String updateTime;
  @Field("description")
  private String description;
  @Field("users_name")
  private List<String> usersName;



  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  public String getCmpyId() {
    return cmpyId;
  }

  public void setCmpyId(String cmpyId) {
    this.cmpyId = cmpyId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<String> getUsersName() {
    return usersName;
  }
  private Set<String> usedHbasePrivilege = new HashSet<String>();
  private Set<String> notUsedHbasePrivilege = new HashSet<String>();
  public void setUsersName(List<String> usersName) {
    this.usersName = usersName;
  }
}
