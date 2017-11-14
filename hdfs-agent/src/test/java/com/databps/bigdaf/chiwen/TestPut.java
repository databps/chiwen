package com.databps.bigdaf.chiwen;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;
import org.junit.Test;

public class TestPut {
    URI uri = URI.create("hdfs://192.168.1.235:9000");

    private String homeDir = "F:\\jdkmaveneclipies\\hadoop-2.7.3";

    private String userName = "hdfs";

    final private static String USER_NAME = "hdfs";
    final private static String[] GROUP_NAMES = {""};


    @Test
    public void put() {

        Configuration conf = new Configuration(false);
        System.setProperty("hadoop.home.dir", homeDir);
        FileSystem fs;
//        System.out.println(conf);
//        System.out.println(USER_NAME);
        try {
            UserGroupInformation userGroupInfo =
                    UserGroupInformation.createUserForTesting(USER_NAME, GROUP_NAMES);
            fs = DFSTestUtil.getFileSystemAs(userGroupInfo, uri, conf);
//            System.out.println(fs);
//            System.out.println(userGroupInfo);
            String hdfstr = "/user/litao/hadoop/uplode_1000_1"+ UUID.randomUUID().toString()+"up.txt";
            String content = "test123";
            FSDataOutputStream fsDataOutputStream = fs.create(new Path(hdfstr));
            System.out.println("现在"+fsDataOutputStream);
            fsDataOutputStream.write(content.getBytes());
            fsDataOutputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
