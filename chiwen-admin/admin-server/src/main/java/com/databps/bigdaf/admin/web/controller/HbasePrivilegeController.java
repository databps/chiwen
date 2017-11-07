package com.databps.bigdaf.admin.web.controller;

import com.databps.bigdaf.admin.domain.GatewayPrivilege;
import com.databps.bigdaf.admin.domain.HbasePrivliege;
import com.databps.bigdaf.admin.security.AuthoritiesConstants;
import com.databps.bigdaf.admin.service.HbasePrivilegeService;
import com.databps.bigdaf.admin.service.GatewayUserService;
import com.databps.bigdaf.admin.service.HbasePrivilegeService;
import com.databps.bigdaf.admin.vo.HbasePrivliegeVo;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author shibingxin
 * @create 2017-09-01 上午10:30
 */
@Controller
@RequestMapping(value = "/hbase/privilege")
public class HbasePrivilegeController extends BaseController {
    @Autowired
    private HbasePrivilegeService hasePrivilegeService ;

    @Autowired
    private GatewayUserService hdpAppUserService;


    @RequestMapping(value = "/list")
    @Secured(AuthoritiesConstants.ADMIN)
    public String listUser(MongoPage page, Model model, @RequestParam(required = false) String name,@RequestParam(required = false) String id) {
        List< HbasePrivliege> gatewayPrivileges = hasePrivilegeService.findHbasePrivliegesPage(page,name,id);
        model.addAttribute("gatewayPrivileges", gatewayPrivileges);
        model.addAttribute("page", page);
        return "hbase/privilege/list";
    }


    @RequestMapping(value = "/getPolicy", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJson getPolicy(@RequestParam(required = false) String privilegeId) {

        HbasePrivliegeVo vo = new HbasePrivliegeVo();

        HbasePrivliege privilege = hasePrivilegeService.findOneByName(privilegeId);
        vo.setDescription(privilege.getDescription());
        vo.setName(privilege.getName());
        vo.setResource( privilege.getResource());
        // vo.setPath(privilege.getResources().getPath().getValues()[0]);
        if(privilege.getPermissions()==null)
        {
            vo.setPermissions(new ArrayList<String>());
        }else {
            vo.setPermissions(privilege.getPermissions());
        }
        return responseMsg(0, vo);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@Valid HbasePrivliegeVo vo, @RequestParam(required = true) String privilegeId) {

        hasePrivilegeService.updateOrInsert(vo, privilegeId);

        return "redirect:list";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(required = false) String privilegeId) {

        hasePrivilegeService.deleteByName(privilegeId);

        return "redirect:list";
    }


    @ResponseBody
    @RequestMapping(value = "/udetail", method = RequestMethod.GET)
    public ResponseJson uDetail(@RequestParam(required = false) String username) {
        ResponseJson json = new ResponseJson();
        PrivilegeForViewVo privilegeForViewVo = hasePrivilegeService.findAllByUserName(null, username);
        json.setCode(0);
        json.setData(privilegeForViewVo);
        return json;
    }

    @ResponseBody
    @RequestMapping(value = "/gdetail", method = RequestMethod.GET)
    public ResponseJson gDetail(@RequestParam(required = false) String groupname) {
        ResponseJson json = new ResponseJson();
        PrivilegeForViewVo privilegeForViewVo = hasePrivilegeService.findAllByGroupName(null, groupname);
        json.setCode(0);
        json.setData(privilegeForViewVo);
        return json;
    }
    @RequestMapping(value = "/checkName", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> checkLogin(String name, String privilegeId, Model model) {

        return hasePrivilegeService.checkName(name,privilegeId);


    }
}