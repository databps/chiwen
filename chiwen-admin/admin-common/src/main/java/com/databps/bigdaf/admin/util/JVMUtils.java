package com.databps.bigdaf.admin.util;

import com.databps.bigdaf.core.util.DateUtils;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.time.Instant;
import java.util.Date;

/**
 * @author shibingxin
 * @create 2017-08-05 下午3:49
 */
public class JVMUtils {

  public static String getJvmRumTime() {
    RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
    long startTime = bean.getStartTime();
    long nowTime = System.currentTimeMillis();
    return DateUtils.formatIntervalDate(nowTime - startTime);
  }


}