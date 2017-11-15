package com.databps.bigdaf.admin.web.controller;

import com.databps.bigdaf.admin.domain.Policy;
import com.databps.bigdaf.admin.security.AuthoritiesConstants;
import com.databps.bigdaf.admin.service.HbaseUserService;
import com.databps.bigdaf.admin.service.PrivilegeService;
import com.databps.bigdaf.admin.service.SettingsService;
import com.databps.bigdaf.admin.vo.ConfigVo;
import com.databps.bigdaf.admin.vo.HbaseUserVo;
import com.databps.bigdaf.admin.web.controller.base.BaseController;
import com.databps.bigdaf.core.message.ResponseJson;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * HbaseStrategyController
 *
 * @author lvyf
 * @create 2017-11-08 14:22@Controller
@RequestMapping(value = "/authority/hdfs/strateg")
 */
@Controller
@RequestMapping(value = "/authority/hbase/strategy")
public class HbaseStrategyController extends BaseController{

  @Autowired
  private SettingsService settingsService;

  @RequestMapping(value = "/list")
  @Secured(AuthoritiesConstants.ADMIN)
  public String listUser(Model model) {
    ConfigVo vo = settingsService.findStrategy();
    model.addAttribute("vo", vo);
    return "hbase/strategy/list";
  }

  @ResponseBody
  @RequestMapping(value = "/edit", method = RequestMethod.POST)
  public ResponseJson save(@Valid ConfigVo vo) {
    settingsService.editStrategy(vo,"hbase");
    return responseMsg(0);
  }

}