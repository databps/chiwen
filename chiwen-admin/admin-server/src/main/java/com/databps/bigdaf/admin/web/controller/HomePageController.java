package com.databps.bigdaf.admin.web.controller;

import com.databps.bigdaf.admin.manager.HomePageManager;
import com.databps.bigdaf.admin.security.AuthoritiesConstants;
import com.databps.bigdaf.admin.vo.*;
import com.databps.bigdaf.admin.web.controller.base.BaseController;
import com.databps.bigdaf.core.message.ResponseJson;
import com.google.gson.Gson;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/home")
public class HomePageController extends BaseController {

  @Autowired
  private HomePageManager homePageManager;

  private static final Logger LOG = LoggerFactory.getLogger(HomePageController.class);

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @Secured(AuthoritiesConstants.ADMIN)
  public String list(HttpServletRequest request,Model model) {
    //SecurityUtils.getCurrentUser();
    String cmpyId = "5968802a01cbaa46738eee3d"; // getCmpyId(request);
    HomePageVo vo = homePageManager.getHompage(cmpyId);
    model.addAttribute("homeVo",vo);
    return "/home/list";
  }


  @ResponseBody
  @RequestMapping(value = "/query", method = RequestMethod.GET)
  public AuditDailyStatisticHomeVo query(HttpServletRequest request, Model model,HomePageQueryVo homePageQueryVo){
    String cmpId="5968802a01cbaa46738eee3d";
    AuditDailyStatisticHomeVo jsonElement=homePageManager.queryAuditDailyStatistics(cmpId,homePageQueryVo);
    return jsonElement;
  }

}
