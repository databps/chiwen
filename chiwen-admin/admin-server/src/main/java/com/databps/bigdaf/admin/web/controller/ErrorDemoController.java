package com.databps.bigdaf.admin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author merlin
 * @create 2017-09-29 下午4:07
 */
@Controller
@RequestMapping(value = "/errordemo")
public class ErrorDemoController {

  @RequestMapping(value = "", method = RequestMethod.GET)
  public String clean(int date) {

    String name=null;

    name.substring(2);

    return "/user/list";
  }
}
