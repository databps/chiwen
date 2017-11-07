package com.databps.bigdaf.admin.web.controller;

import com.databps.bigdaf.admin.domain.Services;
import com.databps.bigdaf.admin.manager.GatewayApiManager;
import com.databps.bigdaf.admin.manager.GatewayManager;
import com.databps.bigdaf.admin.manager.ServicesManager;
import com.databps.bigdaf.admin.vo.ServiceHdfsConfigVo;
import com.databps.bigdaf.admin.vo.ServicesVo;
import com.databps.bigdaf.admin.web.controller.base.BaseController;
import com.databps.bigdaf.core.common.AuditType;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.hdfs.HAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author lvyf
 * @create 2017-08-21 14:37
 */
@Controller
@RequestMapping(value = "/api/gateway")
public class ApigatewayController extends BaseController {

  @Autowired
  private ServicesManager servicesManager;

  @Autowired
  private GatewayManager gatewayManager;

  @Autowired
  private GatewayApiManager gatewayApiManager;

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public String list(HttpServletRequest httpServletRequest, Model model) {
    String cmpyId = "5968802a01cbaa46738eee3d"; // getCmpyId(request);

    List<ServicesVo> services = servicesManager.getServices(cmpyId);
    model.addAttribute("services", services);

    return "gateway/index";
  }


  @RequestMapping("/Heartbeat")
  @ResponseBody
  public Object Heartbeat(HttpServletRequest httpServletRequest, String UUID) {
    String cmpyId = "5968802a01cbaa46738eee3d";


    /*data.addProperty("passwd:",passwd);*/
    gatewayManager
        .save(cmpyId, UUID, AuditType.GATEWAY.getName(), httpServletRequest.getRemoteAddr());
    return gatewayApiManager.getUserPrivilege();
  }

  @RequestMapping("/namenode")
  @ResponseBody
  public String namenode(HttpServletRequest httpServletRequest) throws Exception {
    String cmpyId = "5968802a01cbaa46738eee3d";
    String port=null;
    String ip=null;
    Services gatewayservices=servicesManager.getServiceConfig(cmpyId,AuditType.HDFS.getName());
    Map<String, String> namenodes = gatewayservices.getServiceConfig();
    String namenoderpc= namenodes.get("hdfs_default_name");
    String namenodewebhdfs=namenodes.get("hdfs_http_url");
    if(namenoderpc==null&&namenodewebhdfs==null){
      return null;
    }

    List<String> result = Arrays.asList(namenodewebhdfs.split(","));
    ArrayList List = new ArrayList();
    for (int i=0 ; i<result.size(); i++){
      port=result.get(i).substring(result.get(i).indexOf(":",result.get(i).indexOf(":")+1 )+1);
      List.add(result.get(i).substring(result.get(i).indexOf("//")+2, result.get(i).lastIndexOf(":"+port)));
    }

    for(int i =0;i<List.size();i++) {
      ip=getactive("hdfs://"+List.get(i)+":"+namenoderpc.substring(namenoderpc.indexOf(":")+1), port);
      if(ip!=null){
        return ip;
      }
      else {
        ip="没有启动集群或没有active";
      }
    }
    return ip;
  }
  public String getactive(String url,String port) throws Exception {
    Configuration conf = new Configuration();
    FileSystem system=null;
    String ip=null;
    try {
      system = FileSystem.get(URI.create(url),conf);
      InetSocketAddress active = HAUtil.getAddressOfActive(system);
      InetAddress address = active.getAddress();
      if(address!=null){
        ip="http://"+address.getHostAddress()+":"+port;
      }
    } catch (IOException e) {
      throw new Exception("获取active namenode的IP异常："+e.getMessage());
    }
    finally{
      try {
        if(system!=null){
          system.close();
        }
      }  catch (IOException e) {
        throw new Exception("FileSystem关闭时异常："+e.getMessage());

      }finally{
        return ip;
      }
    }
  }
}