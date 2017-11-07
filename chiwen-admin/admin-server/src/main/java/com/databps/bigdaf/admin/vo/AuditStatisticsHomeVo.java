package com.databps.bigdaf.admin.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author merlin
 * @create 2017-08-12 下午3:47
 */
public class AuditStatisticsHomeVo implements Serializable{


  private static final long serialVersionUID = 3442675867306475333L;

  private List<GroupSuccessOrFailure> groupSuccessList;

  private List<GroupSuccessOrFailure> groupFailureList;

  private List<FileAccessTypeVo> fileAccessTypeVos;

  public List<FileAccessTypeVo> getFileAccessTypeVos() {
    return fileAccessTypeVos;
  }

  public void setFileAccessTypeVos(
      List<FileAccessTypeVo> fileAccessTypeVos) {
    this.fileAccessTypeVos = fileAccessTypeVos;
  }

  public List<GroupSuccessOrFailure> getGroupSuccessList() {
    return groupSuccessList;
  }

  public void setGroupSuccessList(
      List<GroupSuccessOrFailure> groupSuccessList) {
    this.groupSuccessList = groupSuccessList;
  }

  public List<GroupSuccessOrFailure> getGroupFailureList() {
    return groupFailureList;
  }

  public void setGroupFailureList(
      List<GroupSuccessOrFailure> groupFailureList) {
    this.groupFailureList = groupFailureList;
  }




}
