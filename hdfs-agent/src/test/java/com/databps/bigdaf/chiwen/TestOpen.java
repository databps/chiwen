package com.databps.bigdaf.chiwen;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


import org.apache.hadoop.security.UserGroupInformation;
import org.junit.Test;


public class  TestOpen {

    URI uri = URI.create("hdfs://192.168.1.105:9000");

    private String homeDir = "F:\\jdkmaveneclipies\\hadoop-2.7.3";

    private String userName = "hdfs";

    final private static String USER_NAME = "hdfs";
    final private static String[] GROUP_NAMES = {""};

    @Test
    public void open() {

        Configuration conf = new Configuration(false);
        conf.set("fs.defaultFS", "hdfs://192.168.1.105:8020");
        conf.set("yarn.resourcemanager.hostname", "192.168.1.105");
        System.setProperty("hadoop.home.dir", homeDir);
        FileSystem fs;
        String context = "";
        String Ilist = "";
        try {
            UserGroupInformation userGroupInfo =
                    UserGroupInformation.createUserForTesting(USER_NAME, GROUP_NAMES);
            fs = DFSTestUtil.getFileSystemAs(userGroupInfo, uri, conf);

            FileStatus[] listStatus = fs.listStatus(new Path("/"));
            for (FileStatus status : listStatus) {
                System.out.println(
                        String.valueOf(status.getPath()));
            }
            FSDataInputStream open = fs.open(new Path("/user/litao/hadoop/core-site.xml"));
            List<String> list = org.apache.commons.io.IOUtils.readLines(open.getWrappedStream());
            for (String s : list) {
                System.out.println("context : "+ s );

            }
            open.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }

//    public static void main(String args[]) {
//       String context =  new TestOpen().open();
//        System.out.println(context);
//    }
}
