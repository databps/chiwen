package com.databps.bigdaf.admin.web.controller;

import com.databps.bigdaf.admin.domain.Services;
import com.databps.bigdaf.admin.domain.ServicesGatewayConfig;
import com.databps.bigdaf.admin.manager.ServicesManager;
import com.databps.bigdaf.admin.security.AuthoritiesConstants;
import com.databps.bigdaf.admin.vo.*;
import com.databps.bigdaf.admin.web.controller.base.BaseController;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;

/**
 * @author shibingxin
 * @create 2017-08-05 下午4:26
 */
@Controller
@RequestMapping(value = "/services")
public class ServicesController extends BaseController {

  @Autowired
  private ServicesManager servicesManager;

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @Secured(AuthoritiesConstants.ADMIN)
  public String list(HttpServletRequest httpServletRequest, Model model){
    String	cmpyId = "5968802a01cbaa46738eee3d"; // getCmpyId(request);
      List<ServicesVo> services = servicesManager.getServices(cmpyId);
      model.addAttribute("services",services);
    return "services/list";
  }


  @RequestMapping(value = "/hbase/list", method = RequestMethod.GET)
  @Secured(AuthoritiesConstants.ADMIN)
  public String listHbase(HttpServletRequest httpServletRequest, Model model){
    String	cmpyId = "5968802a01cbaa46738eee3d"; // getCmpyId(request);
    ServicesVo service = servicesManager.getHbaseServcie(cmpyId);
    model.addAttribute("service",service);
    return "services/hbase_list";
  }

  @RequestMapping(value = "/hive/list", method = RequestMethod.GET)
  @Secured(AuthoritiesConstants.ADMIN)
  public String listHive(HttpServletRequest httpServletRequest, Model model){
    String	cmpyId = "5968802a01cbaa46738eee3d"; // getCmpyId(request);
    ServicesVo service = servicesManager.getHiveServcie(cmpyId);
    model.addAttribute("service",service);
    return "services/hive_list";
  }

  @RequestMapping(value = "/gateway/list", method = RequestMethod.GET)
  @Secured(AuthoritiesConstants.ADMIN)
  public String listGateway(HttpServletRequest httpServletRequest, Model model) {
    String cmpyId = "5968802a01cbaa46738eee3d"; // getCmpyId(request);
    ServicesVo service = servicesManager.getGatewayServcie(cmpyId);
    model.addAttribute("service", service);
    return "services/gateway_list";
  }

  @RequestMapping(value = "/edit", method = RequestMethod.POST)
  public void edit(HttpServletRequest httpServletRequest) {

  }

  @RequestMapping(value = "/saveConfig", method = RequestMethod.POST)
  public String saveServiceConfig(HttpServletRequest httpServletRequest,String confId ,ServiceHdfsConfigVo configVo ) {
    String	cmpyId = "5968802a01cbaa46738eee3d"; // getCmpyId(request);
    servicesManager.saveServicesConfig(cmpyId,configVo);
    return "redirect:list";
  }

  @RequestMapping(value = "/hbase/saveConfig", method = RequestMethod.POST)
  public String saveHbaseConfig(ServicesHbaseConfigVo configVo ) {
    String	cmpyId = "5968802a01cbaa46738eee3d"; // getCmpyId(request);
    servicesManager.saveHbaseConfig(cmpyId,configVo);
    return "redirect:list";
  }
  @RequestMapping(value = "/hive/saveConfig", method = RequestMethod.POST)
  public String saveHiveConfig(ServicesHiveConfigVo configVo ) {
    String	cmpyId = "5968802a01cbaa46738eee3d"; // getCmpyId(request);
    servicesManager.saveHiveConfig(cmpyId,configVo);
    return "redirect:list";
  }
  @RequestMapping(value = "/gateway/saveConfig", method = RequestMethod.POST)
  public String saveGatewayConfig(ServicesGatewayConfigVo configVo ) {
    String	cmpyId = "5968802a01cbaa46738eee3d"; // getCmpyId(request);
    servicesManager.saveGatewayConfig(cmpyId,configVo);
    return "redirect:list";
  }

  @RequestMapping(value = "/getConfig", method = RequestMethod.GET)
  public Services getServiceConfig(String name) {
    String	cmpyId = "5968802a01cbaa46738eee3d";
    Services jsonElement = servicesManager.getServiceConfig(cmpyId,name);
    return jsonElement;
  }

}