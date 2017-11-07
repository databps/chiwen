package com.databps.bigdaf.admin.web.controller;

import com.databps.bigdaf.admin.security.AuthoritiesConstants;
import com.databps.bigdaf.admin.service.GatewayUserService;
import com.databps.bigdaf.admin.service.PrivilegeService;
import com.databps.bigdaf.admin.vo.GatewayUserVo;
import com.databps.bigdaf.admin.web.controller.base.BaseController;
import com.databps.bigdaf.core.message.ResponseJson;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;

import java.util.List;
import java.util.Map;
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
 * HdpAppUserManagerController
 *
 * @author lgc
 * @create 2017-08-09 下午7:40
 */


@Controller
@RequestMapping(value = "/authority/user")
public class UserController extends BaseController {


  @Autowired
  private GatewayUserService hdpAppUserService;

  @Autowired
  private PrivilegeService privilegeService;

  @RequestMapping(value = "/list")
  @Secured(AuthoritiesConstants.ADMIN)
  public String listUser(MongoPage page, @RequestParam(required = false) String name,Model model) {
    List<GatewayUserVo> userVoList = hdpAppUserService.findAllByName(page,name,"hdfs");
    model.addAttribute("userVoList", userVoList);
    model.addAttribute("page", page);
    return "user/list";
  }

  @RequestMapping(value = "/edit", method = RequestMethod.GET)
  @ResponseBody
  public ResponseJson edit(@RequestParam(required = false) String id) {

    GatewayUserVo vo = hdpAppUserService.findOneById(id);

    return responseMsg(0, vo);
  }


  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public String save(@Valid GatewayUserVo userVo,@RequestParam(required = false) String mainname) {

    if (StringUtils.isBlank(mainname)) {
      hdpAppUserService.insert(userVo);
    } else {
      hdpAppUserService.update(userVo,mainname);
    }
    return "redirect:list";
  }

  @RequestMapping(value = "/delete", method = RequestMethod.GET)
  public String delete(@RequestParam(required = false) String id) {

    hdpAppUserService.deleteById(id);

    return "redirect:list";
  }


  @RequestMapping(value = "/privilege", method = RequestMethod.POST)
  public String privilege(String[] names,String username) {
    if(names==null){
      String[] names1= new String[]{};
      privilegeService.addPrivilege(names1,username);
    }else{
      privilegeService.addPrivilege(names,username);
    }
    return "redirect:list";
  }

  @RequestMapping(value = "/checkName", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, String> checkLogin(String username, String mainname, Model model) {

    return hdpAppUserService.checkName(username,mainname);


  }

  @RequestMapping(value = "/delete")
  public String deleteUserbyName(String name){
    hdpAppUserService.deleteUserbyName(name);
    return "redirect:list";
  }

//  @RequestMapping(value = "/findbyuname")
//  public String findUserbyName(Pageable pageable, String name, Model model) {
//    model.addAttribute("hdpAppUserPage", hdpAppUserService.findUserbyName(pageable, name));
//    return "jurisdiction/jurisdiction";
//  }

//  @RequestMapping(value = "/addu")
//  public String addUser(Pageable pageable,String name,Model model){

//  }
  /**
   @RequestMapping(value = "/deleteu")
   public String deleteUserbyName(Pageable pageable,String name,Model model){
   hdpAppUserService.deleteUserbyName(name);
   model.addAttribute("hdpAppUserPage",hdpAppUserService.getUserList(pageable));
   return "jurisdiction/jurisdiction";
   }
   @RequestMapping(value = "/modifyu")
   public String modifyUserbyName(Pageable pageable,String name,Model model){
   hdpAppUserService.modifyUserbyName(name);
   model.addAttribute("hdpAppUserPage",hdpAppUserService.getUserList(pageable));
   return "jurisdiction/jurisdiction";
   }
   */


}