package com.databps.bigdaf.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class RequestIPUtil {

  // 从正则表达式里面提取字符
  public static String checkIp(String s) {
    String rexp = "([\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3})";
    Pattern p = Pattern.compile(rexp);
    Matcher m = p.matcher(s);
    boolean result = m.find();
    String ip = "0.0.0.0";
    while (result) {
      ip = m.group(1);
      result = m.find();
    }
    return ip;

  }

  /**
   * 获取访问者IP
   *
   * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
   *
   * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
   * 如果还不存在则调用Request .getRemoteAddr()。
   */
  public static String getRemoteIp(HttpServletRequest request) {
    String ip = request.getHeader("X-Real-IP");
    if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
      return checkIp(ip);
    }
    ip = request.getHeader("X-Forwarded-For");
    if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
      // 多次反向代理后会有多个IP值，第一个为真实IP。
      int index = ip.indexOf(',');
      if (index != -1) {
        return checkIp(ip.substring(0, index));
      } else {
        return checkIp(ip);
      }
    } else {
      return checkIp(request.getRemoteAddr());
    }
  }
}
