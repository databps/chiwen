package com.databps.bigdaf.admin.web.controller;

import com.databps.bigdaf.admin.manager.HadoopManager;
import com.databps.bigdaf.admin.security.AuthoritiesConstants;
import com.databps.bigdaf.admin.security.kerberos.KerberosHttpClient;
import com.databps.bigdaf.admin.security.kerberos.http.KerberosWebHDFSConnection;
import com.databps.bigdaf.admin.service.HadoopService;
import com.databps.bigdaf.admin.vo.BrowseVo;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.security.authentication.client.AuthenticationException;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author shibingxin
 * @create 2017-08-14 上午11:23
 */
@Controller
@RequestMapping(value = "/browse")
public class BrowseController {

  private static final Log LOG = LogFactory.getLog(AuditController.class);

  @Autowired
  private HadoopManager hadoopManager;

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @Secured(AuthoritiesConstants.ADMIN)
  public String browse(String path ,HttpServletRequest httpServletRequest, Model model){
    String	cmpyId = "5968802a01cbaa46738eee3d"; // getCmpyId(request);
    try {
      List<BrowseVo> vos = hadoopManager.getHadoopFileList(cmpyId,path);
      model.addAttribute("files",vos);
      model.addAttribute("path",path);
//      test();
    }catch (IOException e) {
      model.addAttribute("errors","您还没有完成配置.");
      e.printStackTrace();
    }
    return "browse/list";
  }

//  private void test () throws IOException, UnsupportedOperationException , AuthenticationException {
//
//    System.setProperty("java.security.krb5.conf", "./krb5.conf");
//    String urlStr = "http://192.168.1.100/webhdfs/v1/?op=LISTSTATUS";
//    URL urltmp = new URL(urlStr);
//    String host = "192.168.1.100";
//    String port ="50070";
//    String urls = "http" + "://" + urltmp.getHost() + ":" + port+"/webhdfs/v1/?op=LISTSTATUS";
//    try {
//    KerberosWebHDFSConnection conn = new KerberosWebHDFSConnection(urls, "admin/admin@HADOOP",
//        "123456");
//    System.out.println(conn.getResponseString(urlStr));
//    }catch (IOException e) {
//      e.printStackTrace();
//    } catch (AuthenticationException e) {
//      e.printStackTrace();
//    }
//    try{
//
//    String user ="hdfs@HADOOP";
//    String keytab="./hdfs.keytab";
//    String krb5Location="./krb5.conf";
//    String urlss = "http" + "://" + urltmp.getHost() + ":" + port+"/webhdfs/v1/?op=LISTSTATUS&user.name=hdfs";
//    KerberosHttpClient client = new KerberosHttpClient(user,keytab,krb5Location, false);
//    HttpResponse responses = client.callRestUrl(urlss,user);
//    InputStream is = responses.getEntity().getContent();
//    System.out.println("Status code " + responses.getStatusLine().getStatusCode());
//    System.out.println("message is :"+ Arrays.deepToString(responses.getAllHeaders()));
//    System.out.println("字符串：\n"+new String(IOUtils.toByteArray(is), "UTF-8"));
//    }catch (IOException e) {
//      e.printStackTrace();
//    }
//  }
}