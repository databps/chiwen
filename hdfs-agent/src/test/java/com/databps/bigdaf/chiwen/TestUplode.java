package com.databps.bigdaf.chiwen;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.UUID;

public class TestUplode {
//    static  Configuration conf = new Configuration(true);
//    static{
//        //指定hadoop fs的地址
//        conf.set("fs.default.name", "hdfs://192.168.1.105:9000");
//    }
//    static {
//        conf.set("HADOOP_USER_NAME", "hdfs");
//    }
URI uri = URI.create("hdfs://192.168.1.131:8020");

    private String homeDir = "/Users/merlin/Documents/software";

    private String userName = "hdfs";

    final private static String USER_NAME = "hdfs2";
    final private static String[] GROUP_NAMES = {""};

    @Test
    public void upLoadFileFromHDFS() throws Exception {
        Configuration conf = new Configuration(false);
        System.setProperty("hadoop.home.dir", homeDir);
        System.setProperty("HADOOP_USER_NAME", userName);
        FileSystem fs;

//        for (int i=0;i<1;i++){

        String dst = "/app-logs/10M/11"+ UUID.randomUUID().toString()+"a.txt";
        String filePath = "F:\\download\\upload\\100_2M.txt";
        fs = FileSystem.get(uri,conf);
        Path srcPath = new Path(filePath);
        Path dstPath = new Path(dst);
        Long start = System.currentTimeMillis();
        fs.copyFromLocalFile(false, srcPath, dstPath);
        System.out.println("Time:"+ (System.currentTimeMillis() - start));

        System.out.println("________________________Upload to "+conf.get("fs.default.name")+"________________________");
//            fs.close();
//        }
    }


}
