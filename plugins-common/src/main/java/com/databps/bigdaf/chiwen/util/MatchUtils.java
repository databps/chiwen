package com.databps.bigdaf.chiwen.util;

/**
 * @author shibingxin
 * @create 2017-09-11 下午2:28
 */
public class MatchUtils {


  public static boolean match(String pattern, String content, int p, int c) {
    if (pattern.length() == p && content.length() == c)
      return true;
    if (pattern.length() > p && '*' == pattern.charAt(p) && pattern.length() > p + 1
        && content.length() == c)
      return false;
    if (pattern.length() > p && content.length() > c && ('?' == pattern.charAt(p)
        || pattern.charAt(p) == content.charAt(c)))
      return match(pattern, content, p + 1, c + 1);
    if (pattern.length() > p && '*' == pattern.charAt(p))
      return match(pattern, content, p + 1, c) || match(pattern, content, p, c + 1);
    return false;
  }

  public static boolean match(String pattern, String content) {
    if (null == pattern || null == content)
     return false;
    return match(pattern, content, 0, 0);
  }

  public static void main(String[] args) {
//    match("tabl*","table");

      boolean isma = match("table1","table1,table3");
    System.out.println(isma);
//    test("g*ks", "geeks"); // Yes
//    test("ge?ks*", "geeksforgeeks"); // Yes
//    test("g*k", "gee");  // No because 'k' is not in second
//    test("*pqrs", "pqrst"); // No because 't' is not in first
//    test("abc*bcd", "abcdhghgbcd"); // Yes
//    test("abc*c?d", "abcd"); // No because second must have 2 instances of 'c'
//    test("*c*d", "abcd"); // Yes
//    test("*?c*d", "abcd"); // Yes
//    test("*?c***d", "abcd"); // Yes
//    test("ge?ks**", "geeks"); // Yes
  }
}