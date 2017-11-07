package com.databps.bigdaf.admin.util;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import org.apache.commons.lang.StringUtils;

/**
 * Created by fzz on 16-11-4.
 */
public class SocketStatusUtils {

  /**
   * 检测socket是否开启
   * @param ip ip
   * @param port port
   * @param timeout the timeout value to be used in milliseconds.
   * @return true/false
   */
  public static boolean isReachable(String ip, String port, int timeout) {
    boolean reachable = false;
    // 如果端口为空，使用 isReachable 检测，非空使用 socket 检测
    if(StringUtils.isBlank(ip)) {
      try {
        InetAddress address = InetAddress.getByName(ip);
        reachable = address.isReachable(timeout);
      } catch (Exception e) {
//        e.printStackTrace();
        reachable = false;
      }
    } else {
      Socket socket = new Socket();
      try {
        socket.connect(new InetSocketAddress(ip, Integer.parseInt(port)), timeout);
        reachable = true;
      } catch (Exception e) {
//        e.printStackTrace();
        reachable = false;
      } finally {
        try {
          if(socket != null) socket.close();
        }catch (Exception e) {}
      }
    }
    return reachable;
  }

  //
  public static boolean isReachable(String ip, int port, int timeout) {
    return isReachable(ip, port + "", timeout);
  }
}
