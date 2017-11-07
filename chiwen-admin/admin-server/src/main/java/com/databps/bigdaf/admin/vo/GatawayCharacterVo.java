package com.databps.bigdaf.admin.vo;

import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * GroupVo
 *
 * @author lgc
 * @create 2017-08-14 下午11:13
 */
public class GatawayCharacterVo {
  private String id;

  public String getCmpyId() {
    return cmpyId;
  }

  public void setCmpyId(String cmpyId) {
    this.cmpyId = cmpyId;
  }

  @Field("cmpy_id")
  private String cmpyId;
  public List<Map<String, String>> getGatewayPrivilege() {
    return GatewayPrivilege;
  }

  public void setGatewayPrivilege(List<Map<String, String>> gatewayPrivilege) {
    GatewayPrivilege = gatewayPrivilege;
  }

  public Set<String> getUsedGatewayPrivilege() {
    return usedGatewayPrivilege;
  }

  public void setUsedGatewayPrivilege(Set<String> usedGatewayPrivilege) {
    this.usedGatewayPrivilege = usedGatewayPrivilege;
  }

  public Set<String> getNotUsedGatewayPrivilege() {
    return notUsedGatewayPrivilege;
  }

  public void setNotUsedGatewayPrivilege(Set<String> notUsedGatewayPrivilege) {
    this.notUsedGatewayPrivilege = notUsedGatewayPrivilege;
  }

  @NotNull
  @Size(min = 1, max = 50)
  private String  name;
  @NotNull
  @Size(min = 1, max = 250)
  private String description;
  private String createTime;

  public String getName() {
    return  name;
  }

  public void setName(String  name) {
    this.name =  name;
  }

  public String getCreateTime() {
    return createTime;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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
  private List<Map<String, String>> GatewayPrivilege;
  private Set<String> usedGatewayPrivilege = new HashSet<String>();
  private Set<String> notUsedGatewayPrivilege = new HashSet<String>();

}