package com.databps.bigdaf.admin.util;

import com.databps.bigdaf.core.util.DateUtils;

/**
 * @author shibingxin
 * @create 2017-08-04 下午3:48
 */
public enum  AuditDateType {

  TWOHOUR("twohour",DateUtils.formatLastTwoHourDate(),DateUtils.formatDateNow(),0,11,12),
  DAYS("days",DateUtils.formatLastDayDate(),DateUtils.formatDateNow(),0,10,24),
  WEEK("week",DateUtils.formatLastWeekDate(),DateUtils.formatDateNow(),0,8,7),
  MONTH("month",DateUtils.formatLastMonthDate(),DateUtils.formatDateNow(),0,8,31),
  OTHER("other",null,null,0,8,31),//自定义格式
  ;
  private String name;
  private String startTime;
  private String endTime;
  private int orgin;
  private int bound;
  private int limit;

  AuditDateType(String name, String startTime, String endTime, int orgin, int bound,
      int limit) {
    this.name = name;
    this.startTime = startTime;
    this.endTime = endTime;
    this.orgin = orgin;
    this.bound = bound;
    this.limit = limit;
  }

   public static AuditDateType  getAuditDateType(String name){
    for(AuditDateType type : AuditDateType.values()) {
      if (type.getName().equals(name)) {
        return type;
      }
    }
    return null;
   }
  public String getName() {
    return name;
  }

  public String getStartTime() {
    if(AuditDateType.WEEK.getName().equals(this.name)) {
      return DateUtils.formatLastWeekDate();
    }
    if(AuditDateType.MONTH.getName().equals(this.name)) {
      return DateUtils.formatLastMonthDate();
    }
    if(AuditDateType.DAYS.getName().equals(this.name)) {
      return DateUtils.formatLastDayDate();
    }
    if(AuditDateType.TWOHOUR.getName().equals(this.name)) {
      return DateUtils.formatLastTwoHourDate();
    }
    return startTime;
  }

  public String getEndTime() {
    if(AuditDateType.WEEK.getName().equals(this.name)) {
      return DateUtils.formatDateNow();
    }
    return endTime;
  }

  public int getOrgin() {
    return orgin;
  }

  public int getBound() {
    return bound;
  }

  public int getLimit() {
    return limit;
  }
}