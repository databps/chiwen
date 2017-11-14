package com.databps.bigdaf.admin.web.controller;


import com.databps.bigdaf.admin.security.AuthoritiesConstants;
import com.databps.bigdaf.admin.security.SecurityUtils;
import com.databps.bigdaf.admin.security.jwt.TokenProvider;
import com.databps.bigdaf.admin.web.controller.vm.LoginVM;
import com.databps.bigdaf.admin.web.controller.vm.UpdateUserVM;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.databps.bigdaf.admin.service.UserService;

/**
 * Controller to authenticate users.
 */
@Controller
@RequestMapping("")
public class UserJWTController {

  private final Logger log = LoggerFactory.getLogger(UserJWTController.class);

  private final TokenProvider tokenProvider;

  private final AuthenticationManager authenticationManager;

  private final UserService userService;

  @Autowired
  DefaultKaptcha defaultKaptcha;

  public UserJWTController(TokenProvider tokenProvider,
      AuthenticationManager authenticationManager, UserService userService) {
    this.tokenProvider = tokenProvider;
    this.authenticationManager = authenticationManager;
    this.userService = userService;
  }

  @RequestMapping(value = "/authenticate", method = RequestMethod.GET)
  public String getLoginPage(Model model) {
    return "login/authenticate";
  }

  @RequestMapping(value = "/defaultKaptcha", method = RequestMethod.GET)
  public void defaultKaptcha(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws Exception{
    byte[] captchaChallengeAsJpeg = null;
    ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
    try {
      //生产验证码字符串并保存到session中
      String createText = defaultKaptcha.createText();
      httpServletRequest.getSession().setAttribute("vrifyCode", createText);
      //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
      BufferedImage challenge = defaultKaptcha.createImage(createText);
      ImageIO.write(challenge, "jpg", jpegOutputStream);
    } catch (IllegalArgumentException e) {
      httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
      return ;
    }

    //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
    captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
    httpServletResponse.setHeader("Cache-Control", "no-store");
    httpServletResponse.setHeader("Pragma", "no-cache");
    httpServletResponse.setDateHeader("Expires", 0);
    httpServletResponse.setContentType("image/jpeg");

    ServletOutputStream responseOutputStream =
            httpServletResponse.getOutputStream();
    responseOutputStream.write(captchaChallengeAsJpeg);
    responseOutputStream.flush();
    responseOutputStream.close();
  }
//  @RequestMapping("/imgvrifyControllerDefaultKaptcha")
  private boolean imgvrifyControllerDefaultKaptcha(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse){
    String captchaId = (String) httpServletRequest.getSession().getAttribute("vrifyCode");
    String parameter = httpServletRequest.getParameter("vrifyCode");

    if (!captchaId.equals(parameter)) {
      return false;
    } else {
      return true;
    }
  }

  @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
  public String authorize(@Valid LoginVM loginVM,BindingResult result, HttpServletRequest request,
      HttpServletResponse response,
      RedirectAttributes attr) {

    if(result.hasErrors()){
      attr.addFlashAttribute("error", "用户名或者密码不能为空");
      return "redirect:authenticate";
    }
    if(!imgvrifyControllerDefaultKaptcha(request,response)){
      attr.addFlashAttribute("error", "请输入正确验证码");
      return "redirect:authenticate";
    }
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

    try {
      Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
      String jwt = tokenProvider.createToken(authentication, rememberMe);
      tokenProvider.create(request, response, jwt, false, 3600, "localhost");


      if(SecurityUtils.isCurrentUserInRole(authentication, AuthoritiesConstants.ADMIN)){
        return "redirect:/home/list";
      }
      if(SecurityUtils.isCurrentUserInRole(authentication, AuthoritiesConstants.AUDITOR)){
        return "redirect:/audit/list";
      }
    } catch (AuthenticationException ae) {
      log.debug("Authentication exception trace: {}", ae);
      attr.addFlashAttribute("error", ae.getMessage().equals("Bad credentials")?"用户名或者密码错误":ae.getMessage());
    }
    return "redirect:authenticate";
  }

  @RequestMapping(value = "/logout2", method = RequestMethod.GET)
  public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      tokenProvider.clear(response);
      new SecurityContextLogoutHandler().logout(request, response, auth);
    }
    return "redirect:authenticate";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
  }

  @RequestMapping(value = "/updatepwd", method = RequestMethod.GET)
  public String changePwdView(Model model) {
    return "login/editPwd";
  }

  @RequestMapping(value = "/updatepwd", method = RequestMethod.POST)
  public String changePwd(@Valid UpdateUserVM vm, BindingResult result, Model model,RedirectAttributes attributes) {

    if(!result.hasErrors()){
      userService.updatePwd(SecurityUtils.getCurrentUserLogin(),vm.getNewpassword());

      attributes.addFlashAttribute("message","密码修改成功");
      attributes.addFlashAttribute("code","0");
      return "redirect:updatepwd";
    }
    attributes.addFlashAttribute("message","密码修改失败");
    attributes.addFlashAttribute("code","1");
    return "redirect:updatepwd";
  }


  @RequestMapping(value = "/checkPwd", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, String> checkPwd(@RequestParam(required = false) String oldpassword,
      HttpServletRequest request) {

    Map<String, String> map = new HashMap<String, String>();
    String login = SecurityUtils.getCurrentUserLogin();

    boolean check = userService.checkPwd(login, oldpassword);

    if (check) {
      map.put("ok", "");
    } else {
      map.put("error", "原始密码不对");
    }

    return map;
  }


}
