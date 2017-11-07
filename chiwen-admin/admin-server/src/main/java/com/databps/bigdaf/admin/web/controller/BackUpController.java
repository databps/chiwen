package com.databps.bigdaf.admin.web.controller;

import com.databps.bigdaf.admin.security.AuthoritiesConstants;
import com.databps.bigdaf.admin.service.BackUpService;
import com.databps.bigdaf.admin.util.DiskUtils;
import com.databps.bigdaf.admin.vo.BackUpVo;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author merlin
 * @create 2017-09-07 下午2:03
 */
@Controller
@RequestMapping(value = "/backup")
public class BackUpController {

  @Autowired
  private BackUpService backUpService;

  @RequestMapping(value = "/audit/list", method = RequestMethod.GET)
  @Secured(AuthoritiesConstants.ADMIN)
  public String history(MongoPage pageable, String startDate,String endDate,Model model) {

    List<BackUpVo> backUpVoList = backUpService.findBackUpPage(pageable,startDate,endDate);
    model.addAttribute("page", pageable);
    model.addAttribute("backUpVoList", backUpVoList);
    model.addAttribute("avail", DiskUtils.getAvail());
    return "backup/list";
  }


}
