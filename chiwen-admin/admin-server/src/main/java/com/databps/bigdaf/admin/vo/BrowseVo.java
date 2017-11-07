package com.databps.bigdaf.admin.vo;

import java.io.Serializable;

/**
 * @author shibingxin
 * @create 2017-08-16 上午10:23
 */
public class BrowseVo implements Serializable {

  private String accessTime;
  private String blockSize;
  private String childrenNum;
  private String fileId;
  private String group;
  private String length;
  private String modificationTime;
  private String owner;
  private String pathSuffix;
  private String permission;
  private String replication;
  private String storagePolicy;
  private String type;

  public String getAccessTime() {
    return accessTime;
  }

  public void setAccessTime(String accessTime) {
    this.accessTime = accessTime;
  }

  public String getBlockSize() {
    return blockSize;
  }

  public void setBlockSize(String blockSize) {
    this.blockSize = blockSize;
  }

  public String getChildrenNum() {
    return childrenNum;
  }

  public void setChildrenNum(String childrenNum) {
    this.childrenNum = childrenNum;
  }

  public String getFileId() {
    return fileId;
  }

  public void setFileId(String fileId) {
    this.fileId = fileId;
  }

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public String getLength() {
    return length;
  }

  public void setLength(String length) {
    this.length = length;
  }

  public String getModificationTime() {
    return modificationTime;
  }

  public void setModificationTime(String modificationTime) {
    this.modificationTime = modificationTime;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public String getPathSuffix() {
    return pathSuffix;
  }

  public void setPathSuffix(String pathSuffix) {
    this.pathSuffix = pathSuffix;
  }

  public String getPermission() {
    return permission;
  }

  public void setPermission(String permission) {
    this.permission = permission;
  }

  public String getReplication() {
    return replication;
  }

  public void setReplication(String replication) {
    this.replication = replication;
  }

  public String getStoragePolicy() {
    return storagePolicy;
  }

  public void setStoragePolicy(String storagePolicy) {
    this.storagePolicy = storagePolicy;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}