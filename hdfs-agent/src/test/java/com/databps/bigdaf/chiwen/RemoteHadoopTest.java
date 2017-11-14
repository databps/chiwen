//package com.databps.bigdaf.chiwen;
//
//import java.io.IOException;
//import java.net.URI;
//
//import java.util.List;
//
//
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.fs.FSDataInputStream;
//import org.apache.hadoop.fs.FSDataOutputStream;
//import org.apache.hadoop.fs.FileStatus;
//import org.apache.hadoop.fs.FileSystem;
//import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.fs.permission.AclEntry;
//import org.apache.hadoop.fs.permission.FsAction;
//import org.apache.hadoop.fs.permission.FsPermission;
//import org.apache.hadoop.security.UserGroupInformation;
//import org.apache.hadoop.security.token.Token;
//import org.junit.Test;
//
//
//public class RemoteHadoopTest {
//
//    URI uri = URI.create("hdfs://192.168.1.235:9000");
//
//    private String homeDir = "F:\\jdkmaveneclipies\\hadoop-2.7.3";
//
//    private String userName = "hdfs";
//
//    final private static String USER_NAME = "hdfs";
//    final private static String[] GROUP_NAMES = {""};
//
//
//    @Test
//    public void testListStatusNoClose() {
//
//        Configuration conf = new Configuration(false);
//        System.setProperty("hadoop.home.dir", homeDir);
//        System.setProperty("HADOOP_USER_NAME", userName);
//
//        FileSystem fs;
//        try {
//            fs = FileSystem.get(uri, conf);
//
//            FileStatus[] fileStatuses = fs.listStatus(new Path("/cool"));
//            System.out.println("请求1");
//            for (FileStatus file : fileStatuses) {
//                System.out.println(file.getPath());
//            }
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//    }
//
//
//    @Test
//    public void mkdir() {
//        Configuration conf = new Configuration(false);
//        System.setProperty("hadoop.home.dir", homeDir);
//        System.setProperty("HADOOP_USER_NAME", userName);
//
//        FileSystem fs;
//        try {
//            UserGroupInformation userGroupInfo =
//                    UserGroupInformation.createUserForTesting(USER_NAME, GROUP_NAMES);
//            fs = DFSTestUtil.getFileSystemAs(userGroupInfo, uri, conf);
//            boolean fileStatuses = fs.mkdirs(new Path("/user9/mytemp/wowo1"));
//            System.out.println(fileStatuses);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void rmdir() {
//        Configuration conf = new Configuration(false);
//
//        FileSystem fs;
//        try {
//            UserGroupInformation userGroupInfo =
//                    UserGroupInformation.createUserForTesting(USER_NAME, GROUP_NAMES);
//            fs = DFSTestUtil.getFileSystemAs(userGroupInfo, uri, conf);
//
//            boolean result = fs.delete(new Path("/user9/mytemp/wowo"), true);
//            System.out.println(result);
//            fs.close();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void create() {
//
//        Configuration conf = new Configuration(false);
//        System.setProperty("hadoop.home.dir", homeDir);
//        FileSystem fs;
//        try {
//            UserGroupInformation userGroupInfo =
//                    UserGroupInformation.createUserForTesting(USER_NAME, GROUP_NAMES);
//            fs = DFSTestUtil.getFileSystemAs(userGroupInfo, uri, conf);
//
//            FSDataOutputStream fsDataOutputStream = fs.create(new Path("/user9/mytemp/wowo/temp2.txt"));
//            fsDataOutputStream.write("test".getBytes());
//            fsDataOutputStream.close();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//    @Test
//    public void append() {
//
//        Configuration conf = new Configuration(false);
//        System.setProperty("hadoop.home.dir", homeDir);
//        FileSystem fs;
//        try {
//            UserGroupInformation userGroupInfo =
//                    UserGroupInformation.createUserForTesting(USER_NAME, GROUP_NAMES);
//            fs = DFSTestUtil.getFileSystemAs(userGroupInfo, uri, conf);
//
//            FSDataOutputStream fsDataOutputStream = fs.append(new Path("/user9/mytemp/wowo/temp.txt"));
//            fsDataOutputStream.write("append".getBytes());
//            fsDataOutputStream.close();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Test
//    public void open() {
//
//        Configuration conf = new Configuration(false);
//        System.setProperty("hadoop.home.dir", homeDir);
//        FileSystem fs;
//        try {
//            UserGroupInformation userGroupInfo =
//                    UserGroupInformation.createUserForTesting(USER_NAME, GROUP_NAMES);
//            fs = DFSTestUtil.getFileSystemAs(userGroupInfo, uri, conf);
//
//            FileStatus[] listStatus = fs.listStatus(new Path("/"));
//            for(FileStatus status : listStatus) {
//                System.out.println(status.getPath());
//            }
//            FSDataInputStream open = fs.open(new Path("/user9/mytemp/wowo/temp.txt"));
//            List<String> list = org.apache.commons.io.IOUtils.readLines(open.getWrappedStream());
//            for (String s : list) {
//                System.out.println("内容" + s);
//
//            }
//            open.close();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//    @Test
//    public void testToken() {
//
//        Configuration conf = new Configuration(false);
//        System.setProperty("hadoop.home.dir", homeDir);
//
//        String ss = System.getProperty("HADOOP_USER_NAME");
//
//        System.out.println(ss);
//
//        FileSystem fs;
//
//        try {
//            UserGroupInformation userGroupInfo =
//                    UserGroupInformation.createUserForTesting(USER_NAME, GROUP_NAMES);
//            fs = DFSTestUtil.getFileSystemAs(userGroupInfo, uri, conf);
//
//            Token<?> token = fs.getDelegationToken("aa");
//
//            System.out.println(token.toString());
//
//
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//    @Test
//    public void testSetXAttr() {
//
//        Configuration conf = new Configuration(false);
//        System.setProperty("hadoop.home.dir", homeDir);
//
//        String ss = System.getProperty("HADOOP_USER_NAME");
//
//        System.out.println(ss);
//
//        FileSystem fs;
//
//        try {
//            UserGroupInformation userGroupInfo =
//                    UserGroupInformation.createUserForTesting(USER_NAME, GROUP_NAMES);
//            fs = DFSTestUtil.getFileSystemAs(userGroupInfo, uri, conf);
//
//            fs.setXAttr(new Path("/user9/mytemp/wowo/temp.txt"), "user.myXAttr", "someValue".getBytes());
//
//
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//
//    @Test
//    public void testgetXAttr() {
//
//        Configuration conf = new Configuration(false);
//        System.setProperty("hadoop.home.dir", homeDir);
//
//        String ss = System.getProperty("HADOOP_USER_NAME");
//
//        System.out.println(ss);
//
//        FileSystem fs;
//
//        try {
//            UserGroupInformation userGroupInfo =
//                    UserGroupInformation.createUserForTesting(USER_NAME, GROUP_NAMES);
//            fs = DFSTestUtil.getFileSystemAs(userGroupInfo, uri, conf);
//
//            fs.getXAttr(new Path("/user9/mytemp/wowo/temp.txt"), "user.myXAttr");
//
//
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//    @Test
//    public void testMV() {
//
//        Configuration conf = new Configuration(false);
//        System.setProperty("hadoop.home.dir", homeDir);
//        FileSystem fs;
//        try {
//            UserGroupInformation userGroupInfo =
//                    UserGroupInformation.createUserForTesting(USER_NAME, GROUP_NAMES);
//            fs = DFSTestUtil.getFileSystemAs(userGroupInfo, uri, conf);
//
//            fs.moveToLocalFile(new Path("/user9/mytemp/wowo/temp2.txt"),
//                    new Path("/Users/merlin/Documents/software/temp.txt"));
//
//
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Test
//    public void testAclStatus() {
//
//        Configuration conf = new Configuration(false);
//        System.setProperty("hadoop.home.dir", homeDir);
//        FileSystem fs;
//        try {
//            UserGroupInformation userGroupInfo =
//                    UserGroupInformation.createUserForTesting(USER_NAME, GROUP_NAMES);
//            fs = DFSTestUtil.getFileSystemAs(userGroupInfo, uri, conf);
//
//            fs.getAclStatus(new Path("/user9/mytemp/wowo/temp.txt"));
//
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Test
//    public void testSetAcl() {
//
//        Configuration conf = new Configuration(false);
//        System.setProperty("hadoop.home.dir", homeDir);
//        FileSystem fs;
//        try {
//            UserGroupInformation userGroupInfo =
//                    UserGroupInformation.createUserForTesting(USER_NAME, GROUP_NAMES);
//            fs = DFSTestUtil.getFileSystemAs(userGroupInfo, uri, conf);
//            List<AclEntry> aclSpec = AclEntry.parseAclSpec("aaaaa:foo:rw-", true);
//
//            fs.setAcl(new Path("/user9/mytemp/wowo1"), aclSpec);
//
//
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Test
//    public void testPermission() {
//
//        Configuration conf = new Configuration(false);
//        System.setProperty("hadoop.home.dir", homeDir);
//        FileSystem fs;
//        try {
//            UserGroupInformation userGroupInfo =
//                    UserGroupInformation.createUserForTesting(USER_NAME, GROUP_NAMES);
//            fs = DFSTestUtil.getFileSystemAs(userGroupInfo, uri, conf);
//
//            FsPermission oper = new FsPermission(FsAction.ALL, FsAction.ALL, FsAction.ALL);
//            fs.setPermission(new Path("/user9/mytemp/wowo/temp.txt"), oper);
//
//
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//    @Test
//    public void testOwner() {
//
//        Configuration conf = new Configuration(false);
//        System.setProperty("hadoop.home.dir", homeDir);
//        FileSystem fs;
//        try {
//            UserGroupInformation userGroupInfo =
//                    UserGroupInformation.createUserForTesting(USER_NAME, GROUP_NAMES);
//            fs = DFSTestUtil.getFileSystemAs(userGroupInfo, uri, conf);
//
//            fs.setOwner(new Path("/user9/mytemp/wowo/temp.txt"), "aa", "ss");
//
//
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//}
//
//
