package com.databps.bigdaf.admin.service;

import static java.lang.Thread.sleep;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多线程 FileSystem 测试类
 *
 * @author shibingxin
 * @create 2017-07-31 下午4:50
 */
public class FileSystemThread implements Runnable {

  @Override
  public void run() {

    try {
      while (true) {
        sleep(1000L);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

  public static void main(String args[]) {

    ExecutorService service = Executors.newCachedThreadPool();
    for (int i = 0; i < 100; i++) {
      service.submit(new FileSystemThread());
    }
  }
}