package com.databps.bigdaf.admin.web.controller;

import com.databps.bigdaf.admin.service.HiveUserService;
import com.databps.bigdaf.admin.service.PrivilegeService;
import com.databps.bigdaf.admin.vo.HiveUserVo;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * HiveStrategyController
 *
 * @author lvyf
 * @create 2017-11-08 14:23
 */
@Controller
@RequestMapping(value = "/authority/hive/strateg")
public class HiveStrategyController {
  @Autowired
  private HiveUserService hdpAppUserService;

  @Autowired
  private PrivilegeService privilegeService;

  @RequestMapping(value = "/list")
  // @Secured(AuthoritiesConstants.AUDITOR)
  public String listUser(MongoPage page, @RequestParam(required = false) String name, Model model) {


    List<HiveUserVo> hdpAppUserPage = hdpAppUserService.findAllByName(page,name);
    model.addAttribute("GatewayUserVoList", hdpAppUserPage);
    model.addAttribute("page", page);
    return "hive/user/list";
  }
}