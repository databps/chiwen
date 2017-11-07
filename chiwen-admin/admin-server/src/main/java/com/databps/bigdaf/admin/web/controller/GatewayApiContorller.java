package com.databps.bigdaf.admin.web.controller;

import com.databps.bigdaf.admin.domain.GatewayCharacter;
import com.databps.bigdaf.admin.domain.GatewayPrivilege;
import com.databps.bigdaf.admin.security.AuthoritiesConstants;
import com.databps.bigdaf.admin.service.GatewayPrivilegeService;
import com.databps.bigdaf.admin.service.GatewayUserService;
import com.databps.bigdaf.admin.vo.GatewayPrivilegeVo;
import com.databps.bigdaf.admin.vo.GatewayUserVo;
import com.databps.bigdaf.admin.vo.PrivilegeForViewVo;
import com.databps.bigdaf.admin.web.controller.base.BaseController;
import com.databps.bigdaf.core.message.ResponseJson;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping(value = "/api/user",method = RequestMethod.GET)
public class GatewayApiContorller extends BaseController {
    @Autowired
    private GatewayPrivilegeService gatewayService ;


    @Autowired
    private GatewayUserService hdpAppUserService;




    @RequestMapping(value = "/privileges",method = RequestMethod.GET)
    @ResponseBody
    public Object getUserPrivilege() {

        GatewayPrivilegeVo gatewayGatewayPrivilegeVo=new GatewayPrivilegeVo();
        List<Map<String, Object>> user=new ArrayList<Map<String, Object>>();
        gatewayGatewayPrivilegeVo.setUser(user);
       // Page<GatewayUserVo> page = hdpAppUserService.findAllByName(null,null);
        List<GatewayUserVo> page = hdpAppUserService.findAllByName(null,null,null);
        Iterator<GatewayUserVo>   GatewayUserVo  = page.iterator();
        while(GatewayUserVo.hasNext()){
            Map map1=new HashMap<>();
            List<Map> roleList=new ArrayList<Map>();
            GatewayUserVo  GatewayUserVo2=  GatewayUserVo.next();
             List<Map<String, String>> roles= GatewayUserVo2.getRoles();
            if(roles==null)
                roles=new ArrayList<Map<String, String>>();
            Iterator<Map<String, String>>  gatewayRole =  roles.iterator();


            while (gatewayRole.hasNext()) {
                List<Map> privilegeList = new ArrayList<Map>();
                Map<String, String> gatewayRole2 = gatewayRole.next();
                List<GatewayCharacter> gatewayRoles = gatewayService.findGatewayRolesPage(gatewayRole2.get("role_id").toString());

                if (gatewayRoles.size() > 0) {

                    Map<String, Object> mapR1 = new HashMap<String, Object>();

                    List<Map<String, String>> privileges = gatewayRoles.get(0).getPrivileges();
                      if(privileges!=null) {

                          Iterator<Map<String, String>> gatewayprivileges222 = privileges.iterator();


                          //
                          while (gatewayprivileges222.hasNext()) {
                              Map<String, Object> mapP1 = new HashMap<String, Object>();
                              Map<String, Object> mapP2 = new HashMap<String, Object>();
                              Map<String, String> gp = gatewayprivileges222.next();
                              List<GatewayPrivilege> gatewayPrivileges = gatewayService.findGateWayPrivilegesPage(null, null, gp.get("privilege_id").toString());

                              if(gatewayPrivileges.size()>0) {
                                  mapP2.put(gatewayPrivileges.get(0).getSources().getPath(), gatewayPrivileges.get(0).getSources().getOps());
                                  mapP1.put(gatewayPrivileges.get(0).getName(), mapP2);
                                  privilegeList.add(mapP1);
                              }
                          }

                        if(privilegeList.size()>0) {
                            mapR1.put(gatewayRoles.get(0).getName(), privilegeList);
                            roleList.add(mapR1);
                        }
                      }

                }
            }
           if(roleList.size()>0) {
               map1.put(GatewayUserVo2.getUsername() + ":" + GatewayUserVo2.getUPassword(), roleList);
               gatewayGatewayPrivilegeVo.getUser().add(map1);
           }
        }





        return  gatewayGatewayPrivilegeVo.getUser();

    }

    @RequestMapping(value = "/list")
    @Secured(AuthoritiesConstants.ADMIN)
    public String listUser(MongoPage page, Model model, @RequestParam(required = false) String name,@RequestParam(required = false) String id) {
        List<GatewayPrivilege> gatewayPrivileges = gatewayService.findGateWayPrivilegesPage(page,name,id);
        model.addAttribute("gatewayPrivileges", gatewayPrivileges);
        model.addAttribute("page", page);
        return "gatewayPrivilege/list";
    }



    @RequestMapping(value = "/getPolicy", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJson getPolicy(@RequestParam(required = false) String privilegeId) {

        GatewayPrivilegeVo vo = new GatewayPrivilegeVo();

        GatewayPrivilege privilege = gatewayService.findOneByName(privilegeId);
        vo.setDescription(privilege.getDescription());
        vo.setName(privilege.getName());
        vo.setPath( privilege.getSources().getPath());
       // vo.setPath(privilege.getResources().getPath().getValues()[0]);
        List<String> items=new ArrayList<String>();
        if(privilege.getSources().getOps()!=null) {
            items = privilege.getSources().getOps();
        }
        vo.setOps(items);
        return responseMsg(0, vo);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@Valid GatewayPrivilegeVo vo, @RequestParam(required = true) String privilegeId) {

        gatewayService.updateOrInsert(vo, privilegeId);

        return "redirect:list";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(required = false) String privilegeId) {

        gatewayService.deleteByName(privilegeId);

        return "redirect:list";
    }


    @ResponseBody
    @RequestMapping(value = "/udetail", method = RequestMethod.GET)
    public ResponseJson uDetail(@RequestParam(required = false) String username) {
        ResponseJson json = new ResponseJson();
        PrivilegeForViewVo privilegeForViewVo = gatewayService.findAllByUserName(null, username);
        json.setCode(0);
        json.setData(privilegeForViewVo);
        return json;
    }

    @ResponseBody
    @RequestMapping(value = "/gdetail", method = RequestMethod.GET)
    public ResponseJson gDetail(@RequestParam(required = false) String groupname) {
        ResponseJson json = new ResponseJson();
        PrivilegeForViewVo privilegeForViewVo = gatewayService.findAllByGroupName(null, groupname);
        json.setCode(0);
        json.setData(privilegeForViewVo);
        return json;
    }
    @RequestMapping(value = "/checkName", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> checkLogin(String name, String privilegeId, Model model) {

        return gatewayService.checkName(name,privilegeId);


    }
}
