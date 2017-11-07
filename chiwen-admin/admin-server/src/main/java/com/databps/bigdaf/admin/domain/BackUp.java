package com.databps.bigdaf.admin.domain;

import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 备份
 *
 * @author merlin
 * @create 2017-09-07 上午11:00
 */
public class BackUp {

  @Id
  private String  id;

  @Field("create_time")
  private String createTime;

  private String out;

  private String err;

  private int code;

  private Script script;

  private Catalog catalog;

  public Script getScript() {
    return script;
  }

  public void setScript(Script script) {
    this.script = script;
  }

  public Catalog getCatalog() {
    return catalog;
  }

  public void setCatalog(Catalog catalog) {
    this.catalog = catalog;
  }


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getOut() {
    return out;
  }

  public void setOut(String out) {
    this.out = out;
  }

  public String getErr() {
    return err;
  }

  public void setErr(String err) {
    this.err = err;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public static class Script{

    private String bean;

    private Map<String,String> param;

    public String getBean() {
      return bean;
    }

    public void setBean(String bean) {
      this.bean = bean;
    }

    public Map<String, String> getParam() {
      return param;
    }

    public void setParam(Map<String, String> param) {
      this.param = param;
    }
  }



  public static  class Catalog{
    private String path;
    private double size;
    private long count;

    public String getPath() {
      return path;
    }

    public void setPath(String path) {
      this.path = path;
    }

    public double getSize() {
      return size;
    }

    public void setSize(double size) {
      this.size = size;
    }

    public long getCount() {
      return count;
    }

    public void setCount(long count) {
      this.count = count;
    }
  }

//  public class  BackUpConfig{
//
//    private String day;
//
//    private String time;
//
//    public String getDay() {
//      return day;
//    }
//
//    public void setDay(String day) {
//      this.day = day;
//    }
//
//    public String getTime() {
//      return time;
//    }
//
//    public void setTime(String time) {
//      this.time = time;
//    }
//  }

}
