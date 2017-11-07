package com.databps.bigdaf.admin.web.controller;

import com.databps.bigdaf.admin.domain.GatewayPrivilege;
import com.databps.bigdaf.admin.service.GatewayCharactorService;
import com.databps.bigdaf.admin.service.PrivilegeService;
import com.databps.bigdaf.admin.vo.GatawayCharacterVo;
import com.databps.bigdaf.admin.domain.GatewayCharacter;

import com.databps.bigdaf.admin.vo.GatewayPrivilegeVo;
import com.databps.bigdaf.admin.vo.GatewayUserVo;
import com.databps.bigdaf.admin.web.controller.base.BaseController;
import com.databps.bigdaf.core.message.ResponseJson;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author lvyf
 * @create 2017-08-25 14:15
 */

@Controller
@RequestMapping(value = "/character")
public class GatewayCharacterController  extends BaseController {

  @Autowired
  GatewayCharactorService   gatawayCharacterService;

  @Autowired
  private PrivilegeService privilegeService;

  @RequestMapping(value = "/list")
  public String listUser(MongoPage page,@RequestParam(required = false) String name, Model model) {
    List<GatawayCharacterVo> gatawayCharacterVoList = gatawayCharacterService.findAllByName(page,name);
    model.addAttribute("gatawayCharacterVoList", gatawayCharacterVoList);
    model.addAttribute("page", page);
    return "character/list";
  }

  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public String save(@Valid GatawayCharacterVo gatewayCharacter, @RequestParam(required = false) String roleIdEd) {

    if (StringUtils.isBlank(roleIdEd)) {
      gatawayCharacterService.insert(gatewayCharacter);
    } else {
      gatawayCharacterService.update(gatewayCharacter,roleIdEd);
    }

    return "redirect:list";
  }


  @RequestMapping(value = "/delete", method = RequestMethod.GET)
  public String delete(@RequestParam(required = false) String roleIdEd) {

    gatawayCharacterService.deleteById(roleIdEd);

    return "redirect:list";
  }



  @RequestMapping(value = "/privilege", method = RequestMethod.POST)
  public String privilege(String[] names,String roleid) {
    if(names==null){
      String[] names1= new String[]{};
      gatawayCharacterService.modifyGroupPrivilege(names1,roleid);
    }else{
      gatawayCharacterService.modifyGroupPrivilege(names,roleid);
    }
    return "redirect:list";
  }
  @ResponseBody
  @RequestMapping(value = "/udetail", method = RequestMethod.GET)
  public ResponseJson uDetail(@RequestParam(required = true) String roleid) {

    ResponseJson json = new ResponseJson();
    GatawayCharacterVo privilegeForViewVo = gatawayCharacterService.findGatewayPrivilegeAllByRoleId(null, roleid);
    json.setCode(0);
    json.setData(privilegeForViewVo);
    return json;
  }
  @RequestMapping(value = "/getPolicy", method = RequestMethod.GET)
  @ResponseBody
  public ResponseJson getPolicy(@RequestParam(required = false) String roleIdEd) {

    GatawayCharacterVo vo = new GatawayCharacterVo();

    GatewayCharacter privilege = gatawayCharacterService.findOneId(roleIdEd);
    vo.setDescription(privilege.getDescription());
    vo.setName(privilege.getName());
    return responseMsg(0, vo);
  }
  @RequestMapping(value = "/checkName", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, String> checkLogin(String name, String roleIdEd, Model model) {

    return gatawayCharacterService.checkName(name,roleIdEd);


  }
}