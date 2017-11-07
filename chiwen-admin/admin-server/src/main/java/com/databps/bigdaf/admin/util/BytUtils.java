package com.databps.bigdaf.admin.util;

/**
 * @author shibingxin
 * @create 2017-08-11 下午4:01
 */
public class BytUtils {

  public static String convertByteSize(long size) {
    long kb = 1024;
    long mb = kb * 1024;

   if (size >= mb) {
      float f = (float) size / mb;
      return String.format(f > 100 ? "%.0f" : "%.1f", f);
    } else if (size >= kb) {
      float f = (float) size / kb;
      return String.format(f > 100 ? "%.0fK" : "%.1fK", f);
    } else
      return String.format("%d B", size);
  }
}