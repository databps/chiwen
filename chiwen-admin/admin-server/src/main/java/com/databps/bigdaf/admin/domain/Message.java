package com.databps.bigdaf.admin.domain;

import org.springframework.data.annotation.Id;

/**
 * 16-10-14.
 */
public class Message {

  @Id
  private String _id;

  private String cmpy_id;

  private String key;//消息类型

  private String type = "system";

  private String msg;//标题

  private String content;//内容

  private String status = "unread";//delete：删除, unread:未查看, read: 已查看

  private String create_time;

  private String update_time;

  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  public String getCmpy_id() {
    return cmpy_id;
  }

  public void setCmpy_id(String cmpy_id) {
    this.cmpy_id = cmpy_id;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getCreate_time() {
    return create_time;
  }

  public void setCreate_time(String create_time) {
    this.create_time = create_time;
  }

  public String getUpdate_time() {
    return update_time;
  }

  public void setUpdate_time(String update_time) {
    this.update_time = update_time;
  }
}
