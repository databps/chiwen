package com.databps.bigdaf.admin.web.controller;

import com.databps.bigdaf.admin.service.GatewayUserService;
import com.databps.bigdaf.admin.service.HbaseUserService;
import com.databps.bigdaf.admin.service.PrivilegeService;
import com.databps.bigdaf.admin.vo.GatewayUserVo;
import com.databps.bigdaf.admin.vo.HbaseUserVo;
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
 * @author shibingxin
 * @create 2017-09-01 上午10:30
 */
@Controller
@RequestMapping(value = "/authority/hbase/user")
public class HbaseUserController extends BaseController {
    @Autowired
    private HbaseUserService hdpAppUserService;

    @Autowired
    private PrivilegeService privilegeService;

    @RequestMapping(value = "/list")
    // @Secured(AuthoritiesConstants.AUDITOR)
    public String listUser(MongoPage page, @RequestParam(required = false) String name, Model model) {


        List<HbaseUserVo> hdpAppUserPage = hdpAppUserService.findAllByName(page,name);
        model.addAttribute("GatewayUserVoList", hdpAppUserPage);
        model.addAttribute("page", page);
        return "hbase/user/list";
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@Valid HbaseUserVo userVo, @RequestParam(required = false) String id) {

        if (StringUtils.isBlank(userVo.getId())) {
            hdpAppUserService.insert(userVo);
        } else {
            hdpAppUserService.update(userVo,userVo.getId());
        }
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


    @RequestMapping(value = "/delete")
    public String deleteUserbyName(@RequestParam(required = false)   String userId){
        hdpAppUserService.deleteById(userId);
        return "redirect:list";
    }


    @ResponseBody
    @RequestMapping(value = "/udetail", method = RequestMethod.GET)
    public ResponseJson uDetail(@RequestParam(required = true) String userid) {
        ResponseJson json = new ResponseJson();
        HbaseUserVo privilegeForViewVo = hdpAppUserService.findCharacterAllByUserId(null, userid);
        json.setCode(0);
        json.setData(privilegeForViewVo);
        return json;
    }


    @RequestMapping(value = "/gprivilege", method = RequestMethod.POST)
    public String gprivilege(String[] names,String userid) {
        if(names==null){
            String[] names1= new String[]{};
            hdpAppUserService.addPrivilege(names1,userid);
        }else{
            hdpAppUserService.addPrivilege(names,userid);
        }
        return "redirect:list";
    }

    @RequestMapping(value = "/getPolicy", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJson getPolicy(@RequestParam(required = false) String userIdEd) {


        HbaseUserVo vo = hdpAppUserService.findOneById(userIdEd);

        return responseMsg(0, vo);
    }



    @RequestMapping(value = "/checkName", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> checkLogin(String name, String id, Model model) {

       return hdpAppUserService.checkName(name,id);

    }
}