package com.databps.bigdaf.admin.web.controller;

import com.databps.bigdaf.admin.security.AuthoritiesConstants;
import com.databps.bigdaf.admin.security.SecurityUtils;
import com.databps.bigdaf.admin.security.domain.Admin;
import com.databps.bigdaf.admin.security.domain.Authority;
import com.databps.bigdaf.admin.security.domain.SSOAdmin;
import com.databps.bigdaf.admin.service.AdminService;
import com.databps.bigdaf.admin.vo.AdminVo;
import com.databps.bigdaf.admin.web.controller.vm.ManagedUserVM;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.databps.bigdaf.admin.service.UserService;

/**
 * @author merlin
 * @create 2017-07-31 下午7:52
 */
@Controller
@RequestMapping(value = "")
public class AdminController {

  @Autowired
  private UserService userService;

  @Autowired
  private AdminService adminService;


  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public String register(@Valid ManagedUserVM managedUserVM, Model model) {

    if (!checkPasswordLength(managedUserVM.getPassword())) {
      return "redirect:register";
    }

    if (userService.findOneByLogin(managedUserVM.getLogin().toLowerCase()).isPresent()) {
      model.addAttribute("error", "login already in use");

    } else {
      Admin user = userService
              .createUser(managedUserVM.getLogin(), managedUserVM.getPassword(),
                      managedUserVM.getFirstName(), managedUserVM.getLastName(),
                      managedUserVM.getEmail().toLowerCase(), managedUserVM.getImageUrl(),
                      managedUserVM.getLangKey(), AuthoritiesConstants.ADMIN, "0");
    }
    return "redirect:register";

  }

  @RequestMapping(value = "/register", method = RequestMethod.GET)
  public String gets(Model model) {

    return "admin/register";

  }

  @RequestMapping(value = "/admin/list", method = RequestMethod.GET)
  public String list(@RequestParam(required = false) String name, MongoPage pageable, Model model) {

    String id = SecurityUtils.getCurrentUser().getId();

    List<AdminVo> vo = adminService.findAdminPage(pageable, name, id);
    model.addAttribute("page", pageable);
    model.addAttribute("adminvoList", vo);

    return "admin/list";

  }

  @RequestMapping(value = "/admin/edit", method = RequestMethod.GET)
  public String edit(@RequestParam(required = false) String id, Model model) {

    AdminVo vo = adminService.findOne(id);

    model.addAttribute("admin", vo);

    return "admin/edit";

  }

  @RequestMapping(value = "/admin/delete", method = RequestMethod.GET)
  public String delete(@RequestParam(required = false) String id, Model model) {

    adminService.delete(id);

    return "redirect:list";

  }


  @RequestMapping(value = "/admin/add", method = RequestMethod.GET)
  public String add(Model model) {

    return "admin/add";

  }

  @RequestMapping(value = "/admin/edit", method = RequestMethod.POST)
  public String edit(@Valid AdminVo vo, Model model) {
    boolean isUpdate = false;
    if (StringUtils.isBlank(vo.getId())) {
      if (userService.findOneByLogin(vo.getLogin().toLowerCase()).isPresent()) {
        model.addAttribute("error", "login already in use");
        isUpdate=false;
      } else {
        isUpdate = true;
      }
    } else {
      Optional<Admin> admin = userService.findOneByLogin(vo.getLogin().toLowerCase());
      if (admin.isPresent()) {
        if (admin.get().getId().equals(vo.getId())) {
          isUpdate = true;
        } else {
          model.addAttribute("error", "login already in use");
        }
      }else{
        isUpdate=true;
      }
    }

    if (isUpdate) {
      adminService.update(vo);
    }
    return "redirect:list";

  }

  @RequestMapping(value = "/admin/save", method = RequestMethod.POST)
  public String save(@Valid ManagedUserVM managedUserVM, Model model) {

    if (!checkPasswordLength(managedUserVM.getPassword())) {
      return "redirect:add";
    }

    if (userService.findOneByLogin(managedUserVM.getLogin().toLowerCase()).isPresent()) {
      model.addAttribute("error", "login already in use");

    } else {
      SSOAdmin ssoAdmin = SecurityUtils.getCurrentUser();
      Iterator<GrantedAuthority> grantedAuthority = ssoAdmin.getAuthorities().iterator();
      Set<Authority> authorities = new HashSet<>();
      while (grantedAuthority.hasNext()) {
        GrantedAuthority str = grantedAuthority.next();
        Authority authority = new Authority();
        authority.setName(str.getAuthority());
        authorities.add(authority);

      }

      Admin user = userService
              .createUser(managedUserVM.getLogin(), managedUserVM.getPassword(),
                      managedUserVM.getFirstName(), managedUserVM.getLastName(),
                      managedUserVM.getEmail().toLowerCase(), managedUserVM.getImageUrl(),
                      managedUserVM.getLangKey(), authorities, ssoAdmin.getId());
    }
    return "redirect:list";

  }

  @RequestMapping(value = "/admin/checkLogin", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, String> checkLogin(String login, String id, Model model) {

    Map<String, String> map = new HashMap<String, String>();

    if (StringUtils.isBlank(id)) {
      if (userService.findOneByLogin(login.toLowerCase()).isPresent()) {
        map.put("error", "用户名已经存在");

      } else {
        map.put("ok", "");
      }
    } else {
      Optional<Admin> admin = userService.findOneByLogin(login.toLowerCase());
      if (admin.isPresent()) {
        if (admin.get().getId().equals(id)) {
          map.put("ok", "");
        } else {
          map.put("error", "用户名已经存在");
        }
      }else{
        map.put("ok", "");
      }
    }

    return map;

  }


  private boolean checkPasswordLength(String password) {
    return !StringUtils.isEmpty(password) &&
            password.length() >= ManagedUserVM.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH;
  }

}
