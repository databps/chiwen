package com.databps.bigdaf.core.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;


public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

  public static final String YYYY = "yyyy";
  public static final String YYYY_MM = "yyyy-MM";
  public static final String YYYY_MM_DD = "yyyy-MM-dd";
  public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
  public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
  public static final String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
  public static final String EEE_MMM_DD_HH_MM_SS_ZZZ_YYYY = "EEE MMM dd HH:mm:ss zzz yyyy";

  private static final long SECOND_Of_MINUTE = 60 * 1000L;
  private static final long MINUTE_OF_HOUR = 60 * SECOND_Of_MINUTE;
  private static final long HOUR_OF_DAY = 24 * MINUTE_OF_HOUR;

  private StringBuffer buffer = new StringBuffer();
  private static String ZERO = "0";
  private static DateUtils date;
  public static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
  public static SimpleDateFormat format1 = new SimpleDateFormat(
      "yyyyMMdd HH:mm:ss");

  public static Date parse(String dateStr) {
    if (StringUtils.isBlank(dateStr)) {
      return null;
    }
    dateStr = dateStr.trim();
    Date date = null;
    if (dateStr.length() == YYYY.length()) {
      date = parse(dateStr, YYYY);
    } else if (dateStr.length() == YYYY_MM.length()) {
      date = parse(dateStr, YYYY_MM);
    } else if (dateStr.length() == YYYY_MM_DD.length()) {
      date = parse(dateStr, YYYY_MM_DD);
    } else if (dateStr.length() == YYYY_MM_DD_HH_MM.length()) {
      date = parse(dateStr, YYYY_MM_DD_HH_MM);
    } else if (dateStr.length() == YYYY_MM_DD_HH_MM_SS.length()) {
      date = parse(dateStr, YYYY_MM_DD_HH_MM_SS);
    } else if (dateStr.length() == YYYYMMDDHHMMSSSSS.length()) {
      date = parse(dateStr, YYYYMMDDHHMMSSSSS);
    } else {
      date = parseCST(dateStr);
    }
    return date;
  }

  public static String parseMsec(long interval) {
    long days = interval / HOUR_OF_DAY;
    long hours = (interval % HOUR_OF_DAY) / MINUTE_OF_HOUR;
    long minutes = ((interval % HOUR_OF_DAY) % MINUTE_OF_HOUR) / SECOND_Of_MINUTE;

    return days + " days " + hours + " hours " + minutes + " minutes ";
  }

  public static String formatIntervalDate(long interval) {
    long day = interval / HOUR_OF_DAY;
    long hour = (interval % HOUR_OF_DAY) / MINUTE_OF_HOUR;
    long minute = ((interval % HOUR_OF_DAY) % MINUTE_OF_HOUR) / SECOND_Of_MINUTE;
    StringBuffer sb=new StringBuffer();
    sb.append((day<10)?"0"+day+":":""+day+":");
    sb.append((hour<10)?"0"+hour+":":""+hour+":");
    return sb.toString();
  }

  public static Date parseCST(String dateStr) {
    SimpleDateFormat sdf = new SimpleDateFormat(
        EEE_MMM_DD_HH_MM_SS_ZZZ_YYYY, Locale.US);
    try {
      return sdf.parse(dateStr);
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      return parse(dateStr, YYYY_MM_DD_HH_MM_SS);
    }
  }

  public static Date parse(String dateStr, String pattern) {
    SimpleDateFormat fmt = new SimpleDateFormat();
    Date date = null;
    try {
      fmt.applyPattern(pattern);
      date = fmt.parse(dateStr);
    } catch (ParseException e) {
      try {
        fmt.applyPattern(YYYY_MM_DD_HH_MM);
        date = fmt.parse(dateStr);
      } catch (ParseException e1) {
        fmt.applyPattern(YYYY_MM_DD);
        try {
          date = fmt.parse(dateStr);
        } catch (ParseException e2) {
          e2.printStackTrace();
        }
      }
    }
    return date;
  }

  public static String formatTime(Date date) {
    return format(date, "HH:mm:ss");
  }

  public static String formatTear(Date date) {
    return format(date, YYYY);
  }

  public static String formatDate(Date date) {
    return format(date, YYYY_MM_DD);
  }


  public static String formatDateTime(Date date) {
    return format(date, YYYY_MM_DD_HH_MM_SS);
  }

  public static String format(String pattern) {
    return format(new Date(), pattern);
  }

  public static String longToDateTime(long millSec) {
    SimpleDateFormat fmt = new SimpleDateFormat();
    fmt.applyPattern(YYYY_MM_DD_HH_MM_SS);
    Date date = new Date(millSec);
    return fmt.format(date);
  }

  /**
   * 获取当前时间的格式为：YYYYMMDDHHMMSSSSS
   */
  public static String formatDateNow() {
    return DateFormatUtils.format(System.currentTimeMillis(), YYYYMMDDHHMMSSSSS);
  }

  public static String formatDateNow(String formate) {
    return DateFormatUtils.format(System.currentTimeMillis(), formate);
  }

  public static String longToDateTime(long millSec, String pattern) {
    SimpleDateFormat fmt = new SimpleDateFormat();
    fmt.applyPattern(pattern);
    Date date = new Date(millSec);
    return fmt.format(date);
  }

  public static String format(Date date, String pattern) {
    SimpleDateFormat fmt = new SimpleDateFormat();
    fmt.applyPattern(pattern);

    return fmt.format(date);
  }

  public static String formatDate(Date date,String pattern ) {
    Date data = getStartDate(new Date());
    return DateFormatUtils.format(data.getTime(), pattern);
  }

  public static String formatZeroDate() {
    Date data = getStartDate(new Date());
    return DateFormatUtils.format(data.getTime(), YYYYMMDDHHMMSSSSS);
  }

  public Date parseDate(String dateTime) {
    Date date=null;
    SimpleDateFormat lsdStrFormat = new SimpleDateFormat(YYYYMMDDHHMMSSSSS);
    try {
      date = lsdStrFormat.parse(dateTime);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }

  public static String formatLastTwoHourDate() {
    Calendar calendar = getCalendar();
    calendar.add(calendar.HOUR, -2);
    return DateFormatUtils.format(calendar.getTime(), YYYYMMDDHHMMSSSSS);
  }

  public static String formatLastDayDate() {
    Calendar calendar = getCalendar();
    calendar.add(calendar.DATE, -1);
    return DateFormatUtils.format(calendar.getTime(), YYYYMMDDHHMMSSSSS);
  }

  public static String formatLastWeekDate() {
    Calendar calendar = getCalendar();
    calendar.add(calendar.WEEK_OF_MONTH, -1);
    return DateFormatUtils.format(calendar.getTime(), YYYYMMDDHHMMSSSSS);
  }

  public static String formatLastDayDate(int date,String pattern) {
    Calendar calendar = getCalendar();
    calendar.add(calendar.DATE, date);
    return DateFormatUtils.format(calendar.getTime(), pattern);
  }

  public static String formatLastMonthDate() {
    Calendar calendar = getCalendar();
    calendar.add(calendar.MONTH, -1);
    return DateFormatUtils.format(calendar.getTime(), YYYYMMDDHHMMSSSSS);
  }

  public static Calendar getCalendar(String dateTime) {
    Calendar calendar = Calendar.getInstance();
    Date date = parse(dateTime,YYYYMMDDHHMMSSSSS);
    calendar.setTime(date);
    return calendar;
  }

  public static void main(String args[]) {

    //System.out.println(formatIntervalDate(123456789l));
    String date="2017-08-29 19:17";
    System.out.print(formatLastWeekDate());
  }

//  public static String randomDate() {
//    Calendar calendar = getCalendar();
//    calendar.add(calendar.MONTH, -1);
//    long startTime = calendar.getTimeInMillis();
//    long endTime = new Date().getTime();
//    //long dateTime = new Random().(endTime ,startTime)+startTime;
//    long dateTime =ThreadLocalRandom.current().nextLong(startTime,endTime);
//    System.out.println(dateTime);
//    Date date = new Date(dateTime);
//    System.out.println(date);
//    return DateFormatUtils.format(dateTime, YYYYMMDDHHMMSSSSS);
//  }

  /**
   * 获取现在的时间
   *
   * @return long类型的时间
   */
  public static long parseLong() {
    return parseLong(new Date());
  }

  public static long parseLong(Date date) {
    return date.getTime();
  }

  public static DateUtils getDateInstance() {
    if (date == null) {
      date = new DateUtils();
    }
    return date;
  }

  private static Calendar getCalendar() {
    return Calendar.getInstance();
  }

  /**
   * 得到指定日期的一天的的最后时刻23:59:59
   */
  public static Date getFinallyDate(Date date) {
    if (date != null) {
      String temp = format.format(date);
      temp += " 23:59:59";
      try {
        return format1.parse(temp);
      } catch (ParseException e) {
        return null;
      }
    }
    return null;
  }

  /**
   * 得到指定日期的一天的开始时刻00:00:00
   */
  public static Date getStartDate(Date date) {
    if (date != null) {
      String temp = format.format(date);
      temp += " 00:00:00";
      try {
        return format1.parse(temp);
      } catch (Exception e) {
        return null;
      }
    }
    return null;
  }

  public static String getStartDate() {
    String temp = format.format(System.currentTimeMillis());
    temp += "000000000";
    return temp;
  }


  /**
   * 得到指定日期的一天的开始时刻000000000
   */
  public static String getBeforeDate(int dates) {
    String temp = format.format(System.currentTimeMillis());
    temp += " 000000000";
    SimpleDateFormat lsdStrFormat = new SimpleDateFormat(YYYYMMDDHHMMSSSSS);
    Date beginDate= null;
    try {
      beginDate = lsdStrFormat.parse(temp);
    } catch (ParseException e) {
      return null;
    }
    Calendar date = Calendar.getInstance();
    date.setTime(beginDate);
    date.set(Calendar.DATE, date.get(Calendar.DATE) - dates);
    return lsdStrFormat.format(date.getTime());
  }

  /**
   * 得到当前日期 格式为 八位例如：20130510
   */
  public static String getNowDate() {
    StringBuffer buffers = new StringBuffer();
    Calendar calendar = getCalendar();
    buffers.delete(0, buffers.capacity());
    buffers.append(calendar.get(Calendar.YEAR));
    int monday = calendar.get(Calendar.MONDAY) + 1;
    if (monday < 10) {
      buffers.append(ZERO);
    }
    buffers.append(monday);
    int day = calendar.get(Calendar.DATE);
    if (day < 10) {
      buffers.append(ZERO);
    }
    buffers.append(day);
    return buffers.toString();
  }

  /**
   * 得到当前日期 格式为 八位例如：20130510
   */
  public static String getNowDateTime() {
    return DateFormatUtils.format(System.currentTimeMillis(), YYYYMMDDHHMMSSSSS);
  }

  /**
   * 根据输入的日期字符串 和 提前天数 ， 获得 指定日期提前几天的日期对象
   *
   * @return 指定日期倒推指定天数后的日期对象
   */
  public static Date getDate(Date date, int beforeDays) {
    Calendar theCa = Calendar.getInstance();
    theCa.setTime(new Date());
    theCa.add(Calendar.DATE, beforeDays * -1);
    return theCa.getTime();
  }


}