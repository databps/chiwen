package com.databps.bigdaf.core.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

/**
 * Cookie 辅助类
 */
public class CookieUtils {

  /**
   * 每页条数cookie名称
   */
  public static final String COOKIE_PAGE_SIZE = "_cookie_page_size";
  public static final String DOMAIN = null;
  /**
   * 默认每页条数
   */
  public static final int DEFAULT_SIZE = 20;
  /**
   * 最大每页条数
   */
  public static final int MAX_SIZE = 200;

  /**
   * 获得cookie
   *
   * @param request HttpServletRequest
   * @param name cookie name
   * @return if exist return cookie, else return null.
   */
  public static Cookie getCookie(HttpServletRequest request, String name) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null && cookies.length > 0) {
      for (Cookie c : cookies) {
        if (c.getName().equals(name))
          return c;
        }
      }
    return null;
  }

  public static String getCookieByKey(Cookie[] cookies, String key) {
    String re = null;
    if (cookies != null) {
      for (Cookie element : cookies) {
        if (element.getName().equals(key)) {
          String s_cookie = element.getValue();
          String[] cookie_array = s_cookie.split(";");
          re = cookie_array[0];
          break;
        }
      }
    }
    return re;
  }

  /**
   * 根据部署路径，将cookie保存在根目录。
   */
  public static Cookie addCookie(HttpServletRequest request,
      HttpServletResponse response, String name, String value,
      Integer expiry) {
    Cookie cookie = new Cookie(name, value);
    if (expiry != null) {
      cookie.setMaxAge(expiry);
    }
//    cookie.setDomain(DOMAIN);
    String ctx = request.getContextPath();
    cookie.setPath(StringUtils.isBlank(ctx) ? "/" : ctx);
    response.addCookie(cookie);
    return cookie;
  }

  /**
   *
   * @param request
   * @param response
   * @param name
   * @param value
   */
  public static void editCookie(HttpServletRequest request,
      HttpServletResponse response, String name, String value,
      Integer expiry, String domain) {
    Cookie[] cookies = request.getCookies();
    if (null == cookies) {
    } else {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(name)) {
          cookie.setValue(value);
          if (StringUtils.isNotBlank(domain)) {
            cookie.setDomain(domain);
          }
          if (expiry != null) {
            cookie.setMaxAge(expiry);
          }
          String ctx = request.getContextPath();
          cookie.setPath(StringUtils.isBlank(ctx) ? "/" : ctx);
          response.addCookie(cookie);
          break;
        }
      }
    }

  }

  /**
   * 取消cookie
   */
  public static void cancleCookie(HttpServletRequest request,
      HttpServletResponse response, String name) {
    Cookie cookie = new Cookie(name, "");
    cookie.setMaxAge(0);
    String ctx = request.getContextPath();
    cookie.setPath(StringUtils.isBlank(ctx) ? "/" : ctx);
//    cookie.setDomain(DOMAIN);
    response.addCookie(cookie);
  }
}
