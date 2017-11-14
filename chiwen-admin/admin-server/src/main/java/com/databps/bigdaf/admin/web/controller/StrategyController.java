package com.databps.bigdaf.admin.web.controller;

import com.databps.bigdaf.admin.domain.Policy;
import com.databps.bigdaf.admin.security.AuthoritiesConstants;
import com.databps.bigdaf.admin.service.GatewayUserService;
import com.databps.bigdaf.admin.service.PolicyService;
import com.databps.bigdaf.admin.service.PrivilegeService;
import com.databps.bigdaf.admin.vo.GatewayUserVo;
import com.databps.bigdaf.admin.web.controller.base.BaseController;
import com.databps.bigdaf.core.message.ResponseJson;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import java.util.List;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * StrategyController
 *
 * @author lvyf
 * @create 2017-11-08 14:22
 */
@Controller
@RequestMapping(value = "/authority/hdfs/strategy")
public class StrategyController extends BaseController{
  @Autowired
  private GatewayUserService hdpAppUserService;

  @Autowired
  private PrivilegeService privilegeService;

  @Autowired
  private PolicyService policyService;

  @RequestMapping(value = "/list")
  @Secured(AuthoritiesConstants.ADMIN)
  public String listUser(@RequestParam(required = false) String name,Model model) {
    Policy policy = policyService.findStrategy("hdfs");
    model.addAttribute("policy", policy);
    return "strategy/list";
  }

  @ResponseBody
  @RequestMapping(value = "/edit", method = RequestMethod.POST)
  public ResponseJson save(@Valid Policy policy) {
    policyService.editStrategy(policy);
    return responseMsg(0);
  }
}