package com.databps.bigdaf.admin.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * GatewayUserDao
 *
 * @author lgc
 * @create 2017-08-07 下午7:34
 */
public class GatewayUser implements Serializable {

  private static final long serialVersionUID = -4421332032662508198L;
  @Id
  private String id;
  @Field("cmpy_id")
  private String cmpyId;

  public String getCmpyId() {
    return cmpyId;
  }

  public void setCmpyId(String cmpyId) {
    this.cmpyId = cmpyId;
  }

  @Field("u_name")

  private String uName;
  @Field("alias_name")
  private String aliasName;
  private String email;
  private String gatewayOrHdfs;

  public String getGatewayOrHdfs() {
    return gatewayOrHdfs;
  }

  public void setGatewayOrHdfs(String gatewayOrHdfs) {
    this.gatewayOrHdfs = gatewayOrHdfs;
  }

  public String getUPassword() {
    return UPassword;
  }

  public void setUPassword(String UPassword) {
    this.UPassword = UPassword;
  }

  @Field("country_code")
  private Integer countryCode;
  @Field("phone_num")
  private String phoneNum;
  @Field("create_time")
  private String createTime;
  private String remarks;
  @Field("group_name_list")
  private List<String> groupNameList;

  @Field("update_time")
  private String updateTime;
  @Field("u_password")
  private String UPassword;

  public List<Map<String, String>> getRoles() {
    return roles;
  }

  public void setRoles(List<Map<String, String>> roles) {
    this.roles = roles;
  }

  private List<Map<String, String>> roles;
  private String description;


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getuName() {
    return uName;
  }

  public void setuName(String uName) {
    this.uName = uName;
  }

  public String getAliasName() {
    return aliasName;
  }

  public void setAliasName(String aliasName) {
    this.aliasName = aliasName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Integer getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(Integer countryCode) {
    this.countryCode = countryCode;
  }

  public String getPhoneNum() {
    return phoneNum;
  }

  public void setPhoneNum(String phoneNum) {
    this.phoneNum = phoneNum;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public List<String> getGroupNameList() {
    return groupNameList;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public void setGroupNameList(List<String> groupNameList) {
    this.groupNameList = groupNameList;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(String updateTime) {
    this.updateTime = updateTime;
  }
}