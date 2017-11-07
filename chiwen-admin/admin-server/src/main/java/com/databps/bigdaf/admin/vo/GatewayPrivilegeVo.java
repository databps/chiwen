package com.databps.bigdaf.admin.vo;

import com.databps.bigdaf.admin.domain.GatewayPrivilege;
import com.databps.bigdaf.admin.domain.GatewayCharacter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * PolicyVo
 *
 * @author lgc
 * @create 2017-08-08 下午1:43
 */
public class GatewayPrivilegeVo {

  // private String policyjsonStr;

  //public String getPolicyjsonStr() {
  // return policyjsonStr;
  //}

  public List<Map<String, Object>> getUser() {
    return user;
  }

  public void setUser(List<Map<String, Object>> user) {
    this.user = user;
  }

  public String getCmpyId() {
    return cmpyId;
  }

  public void setCmpyId(String cmpyId) {
    this.cmpyId = cmpyId;
  }

  @Field("cmpy_id")
  private String cmpyId;

  //public void setPolicyjsonStr(String policyjsonStr) {
  //  this.policyjsonStr = policyjsonStr;
  // }



  /**
   * knox认证使用角色
   */
  private static final long serialVersionUID = 10000099L;

  private List<Map<String,Object>> user;

   Object o=new ArrayList<>();

  private List<Map<String,GatewayCharacter>> gatewayRole;

  private List<Map<String,GatewayPrivilege>> gatewayPrivilege;


  private String _id;

  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  private String name;

  private String description;

  public GatewayPrivilege.Source getSources() {
    return sources;
  }

  public void setSources(GatewayPrivilege.Source sources) {
    this.sources = sources;
  }

  private GatewayPrivilege.Source sources;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getPath() {
      return path;
    }

    public void setPath(String path) {
      this.path = path;
    }

    private String path;

    public List getOps() {
      return ops;
    }

    public void setOps(List ops) {
      this.ops = ops;
    }

    private List ops;




    private String type;

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

}