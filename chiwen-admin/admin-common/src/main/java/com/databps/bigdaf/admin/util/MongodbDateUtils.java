package com.databps.bigdaf.admin.util;

import com.databps.bigdaf.core.util.DateUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by yyh on 17-7-18.
 */
public class MongodbDateUtils {


  public static String longStartTime() {
    String nowStr = DateUtils.formatDateNow();
    String now = StringUtils.substring(nowStr, 0, 8).concat("000000000");
    return now;
  }

  public static String longEndTime() {
    String nowStr = DateUtils.formatDateNow();
    String now = StringUtils.substring(nowStr, 0, 8).concat("235959999");
    return now;
  }
}
