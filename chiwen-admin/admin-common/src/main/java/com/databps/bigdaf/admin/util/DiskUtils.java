package com.databps.bigdaf.admin.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 磁盘工具类
 *
 * @author merlin
 * @create 2017-09-07 下午4:02
 */
public class DiskUtils {

  /**
   * 获取剩余磁盘空间
   * @return
   */
  public static String getAvail(){

    List<String> command = new ArrayList<>();
    command.add("df");
    command.add("-hl");
    ShellCommandUtils.Result result = null;
    try {
      result = ShellCommandUtils
          .runCommand(command.toArray(new String[command.size()]));
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    BufferedReader bre = new BufferedReader(new StringReader(result.getStdout()));
    int i =1;
    String str;
    String values = null;
    try {
      while ((str = bre.readLine()) != null) {
        if (i == 2) {//判断是第二行，进行文件行内容输出。
          values=str;
        }
        i++;
      }
    } catch (IOException e) {
      e.printStackTrace();
    };
    List<String> list=new ArrayList<>();
    StringTokenizer st=new StringTokenizer(values);
    while ( st.hasMoreElements() ){
      list.add(st.nextToken());
    }
    return list.get(3);
  }

}
