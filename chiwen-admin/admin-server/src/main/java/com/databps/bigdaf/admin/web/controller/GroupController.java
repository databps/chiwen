package com.databps.bigdaf.admin.web.controller;

import com.databps.bigdaf.admin.service.GroupService;
import com.databps.bigdaf.admin.service.PrivilegeService;
import com.databps.bigdaf.admin.vo.GroupVo;
import com.databps.bigdaf.admin.web.controller.base.BaseController;
import com.databps.bigdaf.core.message.ResponseJson;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * GroupController
 *
 * @author lgc
 * @create 2017-08-14 下午10:27
 */

@Controller
@RequestMapping(value = "/authority/group")
public class GroupController extends BaseController {

  @Autowired
  private GroupService groupService;

  @Autowired
  private PrivilegeService privilegeService;

  @RequestMapping(value = "/list")
  public String listUser(MongoPage page, @RequestParam(required = false) String name,Model model) {
    List<GroupVo> groupVoList = groupService.findAllByName(page,name);
    model.addAttribute("page", page);
    model.addAttribute("groupVoList", groupVoList);
    return "group/list";
  }

  @RequestMapping(value = "/edit", method = RequestMethod.GET)
  @ResponseBody
  public ResponseJson edit(@RequestParam(required = false) String id) {

    GroupVo vo = groupService.findOneById(id);

    return responseMsg(0, vo);
  }


  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public String save(@Valid GroupVo GroupVo, @RequestParam(required = false) String mainname) {

    if (StringUtils.isBlank(mainname)) {
      groupService.insert(GroupVo);
    } else {
      groupService.update(GroupVo, mainname);
    }

    return "redirect:list";
  }

  @RequestMapping(value = "/delete", method = RequestMethod.GET)
  public String delete(@RequestParam(required = false) String id) {

    groupService.deleteById(id);

    return "redirect:list";
  }

  @RequestMapping(value = "/checkName", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, String> checkLogin(String groupName, String mainname, Model model) {

    return groupService.checkName(groupName,mainname);


  }

  @RequestMapping(value = "/privilege", method = RequestMethod.POST)
  public String privilege(String[] names, String groupname) {
    if (names == null) {
      String[] names1 = new String[]{};
      privilegeService.modifyGroupPrivilege(names1, groupname);
    } else {
      privilegeService.modifyGroupPrivilege(names, groupname);
    }
    return "redirect:list";
  }
}