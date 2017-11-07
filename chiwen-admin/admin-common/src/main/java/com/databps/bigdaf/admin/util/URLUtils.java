package com.databps.bigdaf.admin.util;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class URLUtils {

  /**
   * 根据url
   * 获取http://ip:port
   */
  public static String getHttpHostPort(String url) {
    String host = "";
    int port = 0;
    String scheme = "http";
    try {
      URI urlt = URI.create(url);
      scheme = urlt.getScheme();
      host = urlt.getHost();
      port = urlt.getPort();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return scheme + "://" + host + ":" + port;
  }

  public static final String IPV4_BASIC_PATTERN_STRING =
      "(([1-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){1}" + // initial first field, 1-255
          "(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){2}" +
          // following 2 fields, 0-255 followed by .
          "([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])"; // final field, 0-255

  public static boolean isIpv4(String ipAddress) {
    String ip = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
        + "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
        + "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
        + "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
    Pattern pattern = Pattern.compile(ip);
    Matcher matcher = pattern.matcher(ipAddress);
    return matcher.matches();

  }

}
