package com.databps.bigdaf.core.message;

import java.io.Serializable;

/**
 * @author merlin
 * @create 2017-07-21 上午10:14
 */
public class ResponseJson implements Serializable {

  public static final int SUCCESS = 0;

  private static final long serialVersionUID = 1L;
  private int code = 1100;

  private String msg;

  private Object data = null;

  private String result;

  public ResponseJson() {
  }


  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }


  public ResponseJson(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public ResponseJson(int code, String msg, Object data) {
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String emsg) {
    this.msg = emsg;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

}
