package com.databps.bigdaf.admin.web.controller;

import com.databps.bigdaf.admin.service.GatewayUserService;
import com.databps.bigdaf.admin.service.PrivilegeService;
import com.databps.bigdaf.admin.vo.GatewayUserVo;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * GatewayStrategyController
 *
 * @author lvyf
 * @create 2017-11-08 14:23
 */
@Controller
@RequestMapping(value = "/authority/gateway/strateg")
public class GatewayStrategyController {

  @Autowired
  private GatewayUserService hdpAppUserService;

  @Autowired
  private PrivilegeService privilegeService;

  @RequestMapping(value = "/list")
  // @Secured(AuthoritiesConstants.AUDITOR)
  public String listUser(MongoPage page,@RequestParam(required = false) String name, Model model) {


    List<GatewayUserVo> hdpAppUserPage = hdpAppUserService.findAllByName(page,name,"gateway");
    model.addAttribute("GatewayUserVoList", hdpAppUserPage);
    model.addAttribute("page", page);
    return "gatewayUser/list";
  }
}