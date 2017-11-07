package com.databps.bigdaf.admin.web.controller;

import com.databps.bigdaf.admin.security.AuthoritiesConstants;
import com.databps.bigdaf.admin.security.SecurityUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author merlin
 * @create 2017-08-31 下午5:45
 */
@Controller
@RequestMapping(value = "")
public class IndexController {

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String index(HttpServletRequest request, Model model) {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    Authentication authentication = securityContext.getAuthentication();
    if(SecurityUtils.isCurrentUserInRole(authentication, AuthoritiesConstants.ADMIN)){
      return "redirect:/home/list";
    }
    if(SecurityUtils.isCurrentUserInRole(authentication, AuthoritiesConstants.AUDITOR)){
      return "redirect:/audit/list";
    }

    return "redirect:/home/list";
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  public String sub(HttpServletRequest request, Model model) {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    Authentication authentication = securityContext.getAuthentication();
    if(SecurityUtils.isCurrentUserInRole(authentication, AuthoritiesConstants.ADMIN)){
      return "redirect:/home/list";
    }
    if(SecurityUtils.isCurrentUserInRole(authentication, AuthoritiesConstants.AUDITOR)){
      return "redirect:/audit/list";
    }
    return "redirect:/home/list";
  }

}
