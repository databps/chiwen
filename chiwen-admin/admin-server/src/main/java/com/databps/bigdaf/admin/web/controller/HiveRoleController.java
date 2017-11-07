package com.databps.bigdaf.admin.web.controller;

import com.databps.bigdaf.admin.domain.HiveRole;
import com.databps.bigdaf.admin.service.HiveRoleService;
import com.databps.bigdaf.admin.service.PrivilegeService;
import com.databps.bigdaf.admin.vo.HiveRoleVo;
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
 * @author wanghaipeng
 * @create 2017-09-01 上午10:30
 */
@Controller
@RequestMapping(value = "/hive/role")
public class HiveRoleController extends BaseController {
    @Autowired
   HiveRoleService  HiveRoleService;

    @Autowired
    private PrivilegeService privilegeService;

    @RequestMapping(value = "/list")
    public String listUser(MongoPage page, @RequestParam(required = false) String name, Model model) {
        List<HiveRoleVo> HiveRoleVoList = HiveRoleService.findAllByName(page,name);
        model.addAttribute("HiveRoleVoList", HiveRoleVoList);
        model.addAttribute("page", page);
        return "hive/role/list";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@Valid HiveRoleVo HiveRole, @RequestParam(required = false) String roleIdEd) {

        if (StringUtils.isBlank(roleIdEd)) {
            HiveRoleService.insert(HiveRole);
        } else {
            HiveRoleService.update(HiveRole,roleIdEd);
        }

        return "redirect:list";
    }


    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(required = false) String roleIdEd) {

        HiveRoleService.deleteById(roleIdEd);

        return "redirect:list";
    }



    @RequestMapping(value = "/privilege", method = RequestMethod.POST)
    public String privilege(String[] names,String roleid) {
        if(names==null){
            String[] names1= new String[]{};
            HiveRoleService.modifyGroupPrivilege(names1,roleid);
        }else{
            HiveRoleService.modifyGroupPrivilege(names,roleid);
        }
        return "redirect:list";
    }
    @ResponseBody
    @RequestMapping(value = "/udetail", method = RequestMethod.GET)
    public ResponseJson uDetail(@RequestParam(required = true) String roleid) {

        ResponseJson json = new ResponseJson();
        HiveRoleVo privilegeForViewVo = HiveRoleService.findHivePrivliegeAllByRoleId(null, roleid);
        json.setCode(0);
        json.setData(privilegeForViewVo);
        return json;
    }
    @RequestMapping(value = "/getPolicy", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJson getPolicy(@RequestParam(required = false) String roleIdEd) {

        HiveRoleVo vo = new HiveRoleVo();

        HiveRole privilege = HiveRoleService.findOneId(roleIdEd);
        vo.setDescription(privilege.getDescription());
        vo.setName(privilege.getName());
        return responseMsg(0, vo);
    }
    @RequestMapping(value = "/checkName", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> checkLogin(String name, String roleIdEd, Model model) {

       return HiveRoleService.checkName(name,roleIdEd);


    }
}