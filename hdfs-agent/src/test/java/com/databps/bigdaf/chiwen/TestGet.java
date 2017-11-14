package com.databps.bigdaf.chiwen;

import java.io.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

public class TestGet {
    URI uri = URI.create("hdfs://192.168.1.235:9000");

    private String homeDir = "F:\\jdkmaveneclipies\\hadoop-2.7.3";

    private String userName = "hdfs";


    @Test
    public void get() {

        try {
            String localStr = "F:\\download\\abbcc.txt";
            String dst = "/cool/bb.txt";
            Configuration conf = new Configuration();
            //获得hadoop系统的连接
            FileSystem fs = FileSystem.get(uri.create(dst),conf);
            //in对应的是本地文件系统的目录
            //out对应的是Hadoop文件系统中的目录
            String path = dst ; //"hdfs://192.168.1.235:9000"+
            FSDataInputStream in = fs.open(new Path(path));
            FileOutputStream out = new FileOutputStream(localStr);
            IOUtils.copyBytes(in,out, 4096,true);
        } catch (Exception e) {
            System.out.println(e.toString());
        }finally{

        }

    }
}
