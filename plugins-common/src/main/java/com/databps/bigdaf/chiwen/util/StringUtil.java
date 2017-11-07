package com.databps.bigdaf.chiwen.util;

import com.google.gson.Gson;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by lgc on 17-7-20.
 */
public class StringUtil {

  private static Gson sGsonBuilder = null;
  private static final TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT+0");
  public static Date getUTCDate() {
    Calendar local  = Calendar.getInstance();
    int      offset = local.getTimeZone().getOffset(local.getTimeInMillis());

    GregorianCalendar utc = new GregorianCalendar(gmtTimeZone);

    utc.setTimeInMillis(local.getTimeInMillis());
    utc.add(Calendar.MILLISECOND, -offset);

    return utc.getTime();
  }
  public static String toLower(String str) {
    return str == null ? null : str.toLowerCase();
  }
  public static <T> boolean isEmpty(Collection<T> set) {
    return set == null || set.isEmpty();
  }

  public static String toString(Iterable<String> iterable) {
    String ret = "";

    if(iterable != null) {
      int count = 0;
      for(String str : iterable) {
        if(count == 0)
          ret = str;
        else
          ret += (", " + str);
        count++;
      }
    }

    return ret;
  }

  public static boolean isEmpty(String str) {
    return str == null || str.trim().isEmpty();
  }

  public static String toString(String[] arr) {
    String ret = "";

    if(arr != null && arr.length > 0) {
      ret = arr[0];
      for(int i = 1; i < arr.length; i++) {
        ret += (", " + arr[i]);
      }
    }

    return ret;
  }

  public static String toString(List<String> arr) {
    String ret = "";

    if(arr != null && !arr.isEmpty()) {
      ret = arr.get(0);
      for(int i = 1; i < arr.size(); i++) {
        ret += (", " + arr.get(i));
      }
    }

    return ret;
  }
  public static <T> String logToJsonString(T log) {
    String ret = null;

    if(log != null) {
      if(log instanceof String) {
        ret = (String)log;
      } else if(StringUtil.sGsonBuilder != null) {
        ret = StringUtil.sGsonBuilder.toJson(log);
      } else {
        ret = log.toString();
      }
    }

    return ret;
  }

  public static boolean containsIgnoreCase(String str, String strToFind) {
    return str != null && strToFind != null && str.toLowerCase().contains(strToFind.toLowerCase());
  }
}
