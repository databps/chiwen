package com.databps.bigdaf.admin.web.controller;

import com.databps.bigdaf.admin.domain.Policy;
import com.databps.bigdaf.admin.domain.Privilege;
import com.databps.bigdaf.admin.domain.Privilege.PrivilegeItems;
import com.databps.bigdaf.admin.security.AuthoritiesConstants;
import com.databps.bigdaf.admin.service.PrivilegeService;
import com.databps.bigdaf.admin.vo.PrivilegeForViewVo;
import com.databps.bigdaf.admin.vo.PrivilegeVo;
import com.databps.bigdaf.admin.web.controller.base.BaseController;
import com.databps.bigdaf.core.message.ResponseJson;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
@RequestMapping(value = "/authority/privilege")
public class PrivilegeController extends BaseController {

  @Autowired
  private PrivilegeService privilegeService;

  @RequestMapping(value = "/list")
  @Secured(AuthoritiesConstants.ADMIN)
  public String listUser(Pageable pageable, Model model,@RequestParam(required = false) String name) {
    Policy policy = privilegeService.findAllByName(pageable, name);
    if(policy!=null){
      List<Privilege> privilegeList = policy.getPrivileges();
      privilegeList.sort((p1,p2)-> p2.getCreateTime().compareTo(p1.getCreateTime()));
    }

    model.addAttribute("policy", policy);
    return "policy/list";
  }

  @RequestMapping(value = "/checkName", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, String> checkLogin(String name, String mainname, Model model) {

    return privilegeService.checkName(name,mainname);


  }


  @RequestMapping(value = "/getPolicy", method = RequestMethod.GET)
  @ResponseBody
  public ResponseJson getPolicy(@RequestParam(required = false) String name) {

    PrivilegeVo vo = new PrivilegeVo();

    Policy policy = privilegeService.findOneByName(name);
    Privilege privilege = policy.getPrivileges().get(0);
    vo.setDescription(privilege.getDescription());
    vo.setName(privilege.getName());
    vo.setPath(privilege.getResources().getPath().getValues()[0]);

    List<PrivilegeItems> items = policy.getPrivileges().get(0).getPrivilege_items();

    int itemsSize = items.size();

    String[] accesses = new String[itemsSize];

    for (int i = 0; i < itemsSize; i++) {
      accesses[i] = items.get(i).getAccesses().get(0).getType();

    }
    vo.setAccesses(accesses);

    return responseMsg(0, vo);
  }

  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public String save(@Valid PrivilegeVo vo, @RequestParam(required = false) String mainname) {

    privilegeService.updateOrInsert(vo, mainname);

    return "redirect:list";
  }

  @RequestMapping(value = "/delete", method = RequestMethod.GET)
  public String delete(@RequestParam(required = false) String name) {

    privilegeService.deleteByName(name);

    return "redirect:list";
  }


  @ResponseBody
  @RequestMapping(value = "/udetail", method = RequestMethod.GET)
  public ResponseJson uDetail(@RequestParam(required = false) String username) {
    ResponseJson json = new ResponseJson();
    PrivilegeForViewVo privilegeForViewVo = privilegeService.findAllByUserName(null, username);
    json.setCode(0);
    json.setData(privilegeForViewVo);
    return json;
  }

  @ResponseBody
  @RequestMapping(value = "/gdetail", method = RequestMethod.GET)
  public ResponseJson gDetail(@RequestParam(required = false) String groupname) {
    ResponseJson json = new ResponseJson();
    PrivilegeForViewVo privilegeForViewVo = privilegeService.findAllByGroupName(null, groupname);
    json.setCode(0);
    json.setData(privilegeForViewVo);
    return json;
  }

//  @Autowired
//  private PrivilegeService privilegeService;
//
//  @RequestMapping(value = "/list")
//  public String listUser(Pageable pageable, Model model) {
//    Page<HdpAppUserVo> page = privilegeService.findAllByName(pageable);
//    model.addAttribute("page", page);
//    return "user/list";
//  }
//
//
//  @RequestMapping(value = "/save", method = RequestMethod.POST)
//  public String save(@Valid HdpAppUserVo userVo) {
//
//    if (StringUtils.isBlank(userVo.getId())) {
//      privilegeService.insert(userVo);
//    } else {
//      privilegeService.update(userVo);
//    }
//
//    return "redirect:list";
//  }


}