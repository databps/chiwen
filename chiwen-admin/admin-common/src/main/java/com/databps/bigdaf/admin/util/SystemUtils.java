package com.databps.bigdaf.admin.util;

/**
 * @author merlin
 * @create 2017-09-07 上午10:20
 */
public class SystemUtils {

  /**
   * 获取操作系统的名称
   */
  public static OSNAME getOsName() {
    String os = System.getProperty("os.name");
    os = os.toLowerCase();
    if (os.startsWith("win")) {
      return OSNAME.WIN;
    }
    if (os.startsWith("mac")) {
      return OSNAME.MAC;
    }
    if (os.startsWith("linux")) {
      return OSNAME.LINUX;
    }
    return OSNAME.UNKNOWN;
  }

  public static boolean isWIN() {
    return getOsName().equals(OSNAME.WIN);
  }

  public static boolean isMAC() {
    return getOsName().equals(OSNAME.MAC);
  }

  public static boolean isLINUX() {
    return getOsName().equals(OSNAME.LINUX);
  }
  public static boolean isUNKNOWN() {
    return getOsName().equals(OSNAME.UNKNOWN);
  }

  public enum OSNAME {
    LINUX, MAC, WIN, UNKNOWN
  }
}
