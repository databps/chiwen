package com.databps.bigdaf.admin.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.junit.Test;

/**
 * 磁盘空间
 *
 * @author merlin
 * @create 2017-09-07 下午3:10
 */
public class SpaceTest {

  @Test
  public void disk() {
    File[] roots = File.listRoots();//获取磁盘分区列表
    for (File file : roots) {
      System.out.println(file.getPath() + "信息如下:");
      System.out.println("空闲未使用 = " + file.getFreeSpace() / 1024 / 1024 / 1024 + "G");//空闲空间
      System.out.println("已经使用 = " + file.getUsableSpace() / 1024 / 1024 / 1024 + "G");//可用空间
      System.out.println("总容量 = " + file.getTotalSpace() / 1024 / 1024 / 1024 + "G");//总空间
      System.out.println();
    }
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
    System.out.println("out3==========>" + list.get(3));
    System.out.println("out==========>" + result.getStdout());
    System.out.println("code==========>" + result.getExitCode());
    System.out.println("err==========>" + result.getStderr());
  }
}
