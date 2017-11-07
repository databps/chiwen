package com.databps.bigdaf.admin.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * GroupVo
 *
 * @author lgc
 * @create 2017-08-14 下午11:13
 */
public class GroupVo {
  private String id;
  @NotNull
  @Size(min = 1, max = 50)
  private String groupName;
  @NotNull
  @Size(min = 1, max = 250)
  private String description;
  private String createTime;

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
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
}