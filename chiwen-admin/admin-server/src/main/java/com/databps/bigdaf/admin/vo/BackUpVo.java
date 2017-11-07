package com.databps.bigdaf.admin.vo;

import com.databps.bigdaf.admin.domain.BackUp;
import java.util.Map;


/**
 * AuditVo
 *
 * @author lgc
 * @create 2017-08-02 下午4:00
 */
public class BackUpVo {

  private String  id;

  private String createTime;

  private String out;

  private String err;

  private int code;

  private BackUp.Script script;

  private BackUp.Catalog catalog;

  public BackUp.Script getScript() {
    return script;
  }

  public void setScript(BackUp.Script script) {
    this.script = script;
  }

  public BackUp.Catalog getCatalog() {
    return catalog;
  }

  public void setCatalog(BackUp.Catalog catalog) {
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
}