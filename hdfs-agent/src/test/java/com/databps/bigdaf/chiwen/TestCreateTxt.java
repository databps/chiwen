package com.databps.bigdaf.chiwen;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;
import org.junit.Test;

public class TestCreateTxt {
    URI uri = URI.create("hdfs://192.168.1.235:9000");

    private String homeDir = "F:\\jdkmaveneclipies\\hadoop-2.7.3";

    private String userName = "hdfs";

    final private static String USER_NAME = "hdfs";
    final private static String[] GROUP_NAMES = {""};


    @Test
    public void create() {

        Configuration conf = new Configuration(false);
        System.setProperty("hadoop.home.dir", homeDir);
        FileSystem fs;
        try {
            UserGroupInformation userGroupInfo =
                    UserGroupInformation.createUserForTesting(USER_NAME, GROUP_NAMES);
            fs = DFSTestUtil.getFileSystemAs(userGroupInfo, uri, conf);

            FSDataOutputStream fsDataOutputStream = fs.create(new Path("/cool/cc.txt"));
            fsDataOutputStream.write("test".getBytes());
            fsDataOutputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
