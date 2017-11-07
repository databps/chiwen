package com.databps.bigdaf.core.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * fzz on 2016/8/16.
 * IP地址工具类
 */
public class IpUtils {

    //ip v4的正则表达式
    private final static String IP_V4_REG = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)" +
            "\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)" +
            "\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

    private final static Pattern IP_V4_PATTERN = Pattern.compile(IP_V4_REG);

    /**
     * 是否是正确的IP地址
     */
    public static boolean isIpV4(String str) {

        return IP_V4_PATTERN.matcher(str).matches();
    }

    /**
     * 检测ip是否在白名单 whiteIp中
     * @param whiteIp 白名单IP 用-间隔 e:192.168.2.100-192.168.2.110
     * @param ip 需要检测IP
     * @return true 有效, false 无效
     */
    public static boolean checkWhiteIp(String whiteIp, String ip) {

        if (StringUtils.isBlank(whiteIp) || StringUtils.isBlank(ip)
                || !isIpV4(ip)) {
            return false;
        }

        String[] arr = whiteIp.split("-");

        long ipValue = ipV4StrToLong(ip);
        if (!isIpV4(arr[0]) || ipValue < ipV4StrToLong(arr[0])) {
            return false;
        }

        if (arr.length == 1) {
            if (ipValue != ipV4StrToLong(arr[0])) {
                return false;
            }

        } else {
           if (!isIpV4(arr[1]) || ipValue > ipV4StrToLong(arr[1])) {
                return false;
            }
        }

        return true;
    }

    /**
     * IP v4转 数字 long型的
     * @param ipStr ipStr
     */
    public static long ipV4StrToLong(String ipStr) {

        String[] arr = ipStr.split("\\.");

        long result = 0;

        for (int i = 0; i < arr.length; i++) {

            long ip = Long.parseLong(arr[3 - i]);

            result |= ip << (i * 8);
        }

        return result;
    }


    /**
     * 是否是IP段
     * @param ipStr ipStr
     * @param separator 链接字符 默认中划线
     */
    public static boolean isIpSection(String ipStr, String separator) {

        int len = ipStr.length();
        //1.1.1.1-1.1.1.1 ~ 222.222.222.222-223.223.223.223
        if (len < 15 || len > 31 || !ipStr.contains(separator)) {
            return false;
        }

        if (StringUtils.isBlank(separator)) {
            separator = "-";
        }

        String[] arr = ipStr.split(separator);

        if (arr.length != 2) {
            return false;
        }

        if (!isIpV4(arr[0]) || !isIpV4(arr[1])) {
            return false;
        }

        return ipV4StrToLong(arr[0]) < ipV4StrToLong(arr[1]);

    }

    /**
     * IP数字转 IP v4字符串
     */
    public static String longToIp(long ipNumber) {

        return ((ipNumber >> 24) & 0xFF) +
                "." + ((ipNumber >> 16) & 0xFF) +
                "." + ((ipNumber >> 8) & 0xFF) +
                "." + (ipNumber & 0xFF);
    }

    public static void test() {
        System.out.println(checkWhiteIp("192.168.1.10", "192.168.1.10"));
        System.out.println(checkWhiteIp("192.168.1.10", "192.168.1.9"));
        System.out.println(checkWhiteIp("192.168.1.10", "192.168.1.11"));
        System.out.println(checkWhiteIp("192.168.1.a0", "192.168.1.10"));
        System.out.println("----------");
        System.out.println(checkWhiteIp("192.168.1.10-192.168.1.20", "192.168.1.10"));
        System.out.println(checkWhiteIp("192.168.1.10-192.168.1.20", "192.168.1.20"));
        System.out.println(checkWhiteIp("192.168.1.10-192.168.1.20", "192.168.1.15"));
        System.out.println(checkWhiteIp("192.168.1.10-192.168.1.20", "192.168.1.9"));
        System.out.println(checkWhiteIp("192.168.1.10-192.168.1.20", "192.168.1.21"));
    }

}
