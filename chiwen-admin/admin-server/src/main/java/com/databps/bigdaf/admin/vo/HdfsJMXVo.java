package com.databps.bigdaf.admin.vo;

/**
 * @author shibingxin
 * @create 2017-08-11 下午4:55
 */
public class HdfsJMXVo extends ServicesJMXVo{

  private int numLiveDataNodes;
  private int numDeadDataNodes;
  private int numDecommissioningDataNodes;
  private long uptime;
  private String dataNodesUse;
  private String dataNodesMax;
  private String dataNodesPercent;

  private Runtime runtime;

  public String getDataNodesPercent() {
    return dataNodesPercent;
  }
  public void setDataNodesPercent(String dataNodesPercent) {
    this.dataNodesPercent = dataNodesPercent;
  }

  public int getNumLiveDataNodes() {
    return numLiveDataNodes;
  }

  public void setNumLiveDataNodes(int numLiveDataNodes) {
    this.numLiveDataNodes = numLiveDataNodes;
  }

  public int getNumDeadDataNodes() {
    return numDeadDataNodes;
  }

  public void setNumDeadDataNodes(int numDeadDataNodes) {
    this.numDeadDataNodes = numDeadDataNodes;
  }

  public int getNumDecommissioningDataNodes() {
    return numDecommissioningDataNodes;
  }

  public void setNumDecommissioningDataNodes(int numDecommissioningDataNodes) {
    this.numDecommissioningDataNodes = numDecommissioningDataNodes;
  }

  public long getUptime() {
    return uptime;
  }

  public void setUptime(long uptime) {
    this.uptime = uptime;
  }

  public String getDataNodesUse() {
    return dataNodesUse;
  }

  public void setDataNodesUse(String dataNodesUse) {
    this.dataNodesUse = dataNodesUse;
  }

  public String getDataNodesMax() {
    return dataNodesMax;
  }

  public void setDataNodesMax(String dataNodesMax) {
    this.dataNodesMax = dataNodesMax;
  }

  public Runtime getRuntime() {
    return runtime;
  }

  public void setRuntime(Runtime runtime) {
    this.runtime = runtime;
  }

  public static class Runtime{
    private long days;
    private long hours;
    private long minutes;

    public long getDays() {
      return days;
    }

    public void setDays(long days) {
      this.days = days;
    }

    public long getHours() {
      return hours;
    }

    public void setHours(long hours) {
      this.hours = hours;
    }

    public long getMinutes() {
      return minutes;
    }

    public void setMinutes(long minutes) {
      this.minutes = minutes;
    }
  }
}