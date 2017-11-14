package com.databps.bigdaf.admin.service;



import java.io.IOException;
import java.io.OutputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.Credentials;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.security.token.Token;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by fzz on 16-9-19.
 */
public class KerberosHdfsTest1 {
  private FileSystem fs;
  private static String url = "hdfs://192.168.1.230:8020";
  private static final String file_name = "/user/haha/one/asdfa.txt";


  @Before
  public void init() {
    try {
      // url = "hdfs://192.168.1.181:9000";
      Configuration conf = new Configuration(false);
      System.setProperty("hadoop.home.dir", "/Users/shibingxin/Documents");
      conf.set("fs.defaultFS", url);
      conf.set("hadoop.security.authentication", "kerberos");
      System.setProperty("java.security.krb5.conf",
          "/Users/shibingxin/krb5.conf");
      // conf.set("dfs.namenode.kerberos.principal", "fuck@HADOOP.COM");


      /*
       * conf.set("hadoop.security.crypto.buffer.size", "8192");
       * conf.set("hadoop.security.crypto.codec.classes.EXAMPLECIPHERSUITE", "");
       * conf.set("hadoop.security.crypto.codec.classes.aes.ctr.nopadding",
       * "org.apache.hadoop.crypto.OpensslAesCtrCryptoCodec,org.apache.hadoop.crypto.JceAesCtrCryptoCodec"
       * );
       * 
       * conf.set("dfs.encryption.key.provider.uri", "kms://http@60.205.94.252:16000/kms");
       * conf.set("hadoop.security.key.provider.path", "kms://http@60.205.94.252:16000/kms");
       */

      conf.set("dfs.namenode.kerberos.principal.pattern", "*");
      UserGroupInformation.setConfiguration(conf);
      UserGroupInformation.loginUserFromKeytab("hdfs@BIGDATA",
    		  "/Users/shibingxin/hdfs.keytab");
      fs = FileSystem.get(conf);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void create() {
    try {
      fs.copyFromLocalFile(new Path("/home/merlin/桌面/banner7.png"), new Path(file_name));
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Test
  public void testToken() {



    Configuration conf = new Configuration(false);
    
    System.setProperty("hadoop.home.dir", "/home/merlin/Documents");
    System.setProperty("HADOOP_USER_NAME", "test:123456");
    String ss = System.getProperty("HADOOP_USER_NAME");

    try {
      Token<?> token = fs.getDelegationToken("abc");
      byte[] pwd = token.getPassword();

      Path TEST_ROOT_DIR = new Path("/home/merlin/workspace/demoHdfs"); 
      String binaryTokenFile = FileSystem.getLocal(conf).makeQualified( 
          new Path(TEST_ROOT_DIR, "tokenFile")).toUri().getPath(); 
      
      Credentials creds = new Credentials();
      creds.addToken(token.getService(), token); 
      creds.writeTokenStorageFile(new Path(binaryTokenFile), conf);
      

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
    }

  }


  @Test
  public void list() {
    try {
      FileStatus[] listStatus = fs.listStatus(new Path("/"));
      for (FileStatus status : listStatus) {
        status.getPath();
      }
      Assert.assertTrue(listStatus.length > 0);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  // @Test
  public void append() {
    try {
      FSDataOutputStream fsDataOutputStream = fs.append(new Path(url + file_name));
      fsDataOutputStream.write("haha".getBytes());
      fsDataOutputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // @Test
  public void rename() {
    try {
      fs.rename(new Path(file_name), new Path(file_name + ".bak"));
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  // @Test
  public void delete() {
    try {
      fs.delete(new Path(file_name + ".bak"), true);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  // @Test
  public void setOwner() {
    try {
      fs.setOwner(new Path(file_name), "fzz2", null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // @After
  public void last() {
    if (null != fs) {
      try {
        fs.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Test
  public void testAll() {
    create();
    last();
    append();
    rename();
    delete();
    last();
    setOwner();
  }

  @Test
  public void open() {
    FileSystem fsopen = null;
    String msgopen = null;
    OutputStream out = null;
    try {

      FSDataInputStream open = this.fs.open(new Path(file_name));



      org.apache.commons.io.IOUtils.toByteArray(open.getWrappedStream());

    } catch (IOException e1) {
      msgopen = e1.getMessage();
      e1.printStackTrace();
    } finally {
      if (out != null) {
        try {
          out.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
