package com.databps.bigdaf.admin.web.controller;

import com.databps.bigdaf.admin.vo.HdpAppUserVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author shibingxin
 * @create 2017-08-14 下午10:23
 */
@Controller
@RequestMapping(value = "/log")
public class LogController {

  @RequestMapping(value = "/list")
  public String listUser(Pageable pageable, Model model) {
    return "log/list";
  }
}