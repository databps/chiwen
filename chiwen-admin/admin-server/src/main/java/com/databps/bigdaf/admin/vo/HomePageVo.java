package com.databps.bigdaf.admin.vo;

import com.databps.bigdaf.admin.domain.AuditStatisticsGroup;
import java.io.Serializable;
import java.util.List;

/**
 * @author shibingxin
 * @create 2017-08-01 上午11:00
 */
public class HomePageVo  implements Serializable {
  private String cmpId="5968802a01cbaa46738eee3d";
  private int todayAccess;
  private int ipTotalAccess;
  private int todayViolation;
  private int totalViolation;
  private String highDay;
  private String day;
  private String highhour;
  private String hour;
  private String runtime;
  private String loopholeUrl;
  private int loopholeScore;
  private List<PluginStatusVo> pluginStatus;
  private List<AuditStatisticsGroupVo> groupSuccess;
  private List<AuditStatisticsGroupVo> groupFailure;

  public String getCmpId() {
    return cmpId;
  }

  public void setCmpId(String cmpId) {
    this.cmpId = cmpId;
  }
  public String getHighDay() {
    return highDay;
  }

  public void setHighDay(String highDay) {
    this.highDay = highDay;
  }

  public String getDay() {
    return day;
  }

  public void setDay(String day) {
    this.day = day;
  }

  public String getHighhour() {
    return highhour;
  }

  public void setHighhour(String highhour) {
    this.highhour = highhour;
  }

  public String getHour() {
    return hour;
  }

  public void setHour(String hour) {
    this.hour = hour;
  }

  public int getTodayAccess() {
    return todayAccess;
  }

  public void setTodayAccess(int todayAccess) {
    this.todayAccess = todayAccess;
  }

  public int getIpTotalAccess() {
    return ipTotalAccess;
  }

  public void setIpTotalAccess(int ipTotalAccess) {
    this.ipTotalAccess = ipTotalAccess;
  }

  public int getTodayViolation() {
    return todayViolation;
  }

  public void setTodayViolation(int todayViolation) {
    this.todayViolation = todayViolation;
  }

  public int getTotalViolation() {
    return totalViolation;
  }

  public void setTotalViolation(int totalViolation) {
    this.totalViolation = totalViolation;
  }

  public String getRuntime() {
    return runtime;
  }

  public void setRuntime(String runtime) {
    this.runtime = runtime;
  }

  public List<PluginStatusVo> getPluginStatus() {
    return pluginStatus;
  }

  public void setPluginStatus(
      List<PluginStatusVo> pluginStatus) {
    this.pluginStatus = pluginStatus;
  }

  public List<AuditStatisticsGroupVo> getGroupSuccess() {
    return groupSuccess;
  }

  public void setGroupSuccess(
      List<AuditStatisticsGroupVo> groupSuccess) {
    this.groupSuccess = groupSuccess;
  }

  public List<AuditStatisticsGroupVo> getGroupFailure() {
    return groupFailure;
  }

  public void setGroupFailure(
      List<AuditStatisticsGroupVo> groupFailure) {
    this.groupFailure = groupFailure;
  }

  public String getLoopholeUrl() {
    return loopholeUrl;
  }

  public void setLoopholeUrl(String loopholeUrl) {
    this.loopholeUrl = loopholeUrl;
  }

  public int getLoopholeScore() {
    return loopholeScore;
  }

  public void setLoopholeScore(int loopholeScore) {
    this.loopholeScore = loopholeScore;
  }
}