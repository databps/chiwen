package com.databps.bigdaf.admin.domain;

import java.io.Serializable;

/**
 * Created by yyh on 17-7-19.
 */
public class AuditOpCount implements Serializable {

  private int count;
  private String op;
  private String opName;

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public String getOp() {
    return op;
  }

  public void setOp(String op) {
    this.op = op;
  }

  public String getOpName() {
    if (op.equals("read")) {
      opName = "读取";
    } else if (op.equals("write")) {
      opName = "写入";
    } else if (op.equals("execute")) {
      opName = "执行";
    } else {
      opName = "";
    }
    return opName;
  }

  public void setOpName(String opName) {
    this.opName = opName;
  }
}
