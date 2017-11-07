package com.databps.bigdaf.admin.web.controller;

import com.databps.bigdaf.admin.security.AuthoritiesConstants;
import com.databps.bigdaf.admin.service.SettingsService;
import com.databps.bigdaf.admin.vo.ConfigVo;
import com.databps.bigdaf.admin.vo.HbaseUserVo;
import com.databps.bigdaf.core.message.ResponseJson;
import javax.servlet.http.HttpServletRequest;
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
 * 管理设置
 *
 * @author shibingxin
 * @create 2017-08-30 上午11:58
 */
@Controller
@RequestMapping(value = "/security/settings")
public class ManagerSettingsController {
  @Autowired
  private SettingsService settingsService;

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @Secured(AuthoritiesConstants.ADMIN)
  public String list(Model model) {
    String	cmpyId = "5968802a01cbaa46738eee3d"; // getCmpyId(request);
    ConfigVo configVo = settingsService.getConfig(cmpyId);
    model.addAttribute("vo", configVo);
    return "settings/list";
  }

  @RequestMapping(value = "/edit", method = RequestMethod.POST)
  public String edit(@Valid ConfigVo configVo,Model model) {
    String	cmpyId = "5968802a01cbaa46738eee3d"; // getCmpyId(request);
    configVo.setCmpyId(cmpyId);
    configVo = settingsService.saveConfigVo(configVo);
    model.addAttribute("vo", configVo);
    return "redirect:list";
  }


  @ResponseBody
  @RequestMapping(value = "/kerberos", method = RequestMethod.POST)
  public ResponseJson kerberos(@RequestParam(required = true) int kerberosEnable) {
    String	cmpyId = "5968802a01cbaa46738eee3d"; // getCmpyId(request);
    ResponseJson json = new ResponseJson();
    ConfigVo configVo  = settingsService.saveKerberosEnable(cmpyId,kerberosEnable);
    json.setCode(0);
    json.setData(configVo);
    return json;
  }

}