package com.databps.bigdaf.admin.security.kerberos.http;

import com.databps.bigdaf.admin.security.kerberos.KerberosHttpClient;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.security.authentication.client.AuthenticationException;
import org.apache.http.HttpResponse;
import org.junit.Test;

/**
 * @author shibingxin
 * @create 2017-09-28 下午4:44
 */
public class KerberosHttpClientTest {

  @Test
  public void testList() throws IOException, AuthenticationException {
    System.setProperty("java.security.krb5.conf", "/Users/shibingxin/krb5.conf");
    String urlStr = "http://192.168.1.100/webhdfs/v1/?op=LISTSTATUS&user.name=hdfs";
    URL urltmp = new URL(urlStr);
    String host = "192.168.1.100";
    String port ="50070";
    String urls = "http" + "://" + urltmp.getHost() + ":" + port+"/webhdfs/v1/?op=LISTSTATUS&user.name=hdfs";
    KerberosWebHDFSConnection conn = new KerberosWebHDFSConnection(urls, "hdfs@HADOOP",
        "123456");
    System.out.println(conn.getResponseString(urlStr));

  }

  @Test
  public void testSubject()throws IOException, UnsupportedOperationException {
    String user ="hdfs@HADOOP";
    String keytab="./hdfs.keytab";
    String krb5Location="./krb5.conf";
    System.setProperty("java.security.krb5.conf", "/Users/shibingxin/krb5.conf");
    String urlStr = "http://192.168.1.100/webhdfs/v1/?op=LISTSTATUS";
    URL urltmp = new URL(urlStr);
    String host = "192.168.1.100";
    String port ="50070";
    String urls = "http" + "://" + urltmp.getHost() + ":" + port+"/webhdfs/v1/?op=LISTSTATUS&user.name=hdfs";
    KerberosHttpClient client = new KerberosHttpClient(user,keytab,krb5Location, false);
    HttpResponse response = client.callRestUrl(urls,user);
    InputStream is = response.getEntity().getContent();
    System.out.println("Status code " + response.getStatusLine().getStatusCode());
    System.out.println("message is :"+ Arrays.deepToString(response.getAllHeaders()));
    System.out.println("字符串：\n"+new String(IOUtils.toByteArray(is), "UTF-8"));

  }
}