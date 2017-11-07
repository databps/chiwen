package com.databps.bigdaf.admin.domain;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author merlin
 * @create 2017-08-14 下午4:39
 */
public class GatewayPrivilege {
  public String getCmpyId() {
    return cmpyId;
  }

  public void setCmpyId(String cmpyId) {
    this.cmpyId = cmpyId;
  }

  /**
   * knox认证使用角色
   */
  private static final long serialVersionUID = 10000099L;
  @Id
  private String _id;
  @Field("cmpy_id")
  private String cmpyId;
  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  private String name;
  private String type;

  public String getCreate_time() {

    if(StringUtils.isNotBlank(create_time)) {
      return create_time;
    }else {
      String time = "1256006105375";
      Date date = new Date(Long.parseLong(time));
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      time = formatter.format(date);
      create_time = time;
      return create_time;
    }

  }

  public void setCreate_time(String create_time) {
    if(StringUtils.isNotBlank(create_time)) {
      this.create_time = create_time;
    }else {
      String time = "1256006105375";
      Date date = new Date(Long.parseLong(time));
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      time = formatter.format(date);
      this.create_time = time;
    }

  }

  private String description;
  private String create_time;
  public Source getSources() {
    return sources;
  }

  public void setSources(Source sources) {
    this.sources = sources;
  }

  private Source sources;


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

  public static class Source {
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


  }

}
