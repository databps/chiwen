package com.databps.bigdaf.admin.service.impl;

import com.databps.bigdaf.admin.dao.PolicyDao;
import com.databps.bigdaf.admin.dao.PrivilegeDao;
import com.databps.bigdaf.admin.domain.HdpAppUser;
import com.databps.bigdaf.admin.domain.Policy;
import com.databps.bigdaf.admin.domain.Privilege;
import com.databps.bigdaf.admin.domain.Privilege.Accesse;
import com.databps.bigdaf.admin.domain.Privilege.PrivilegeItems;
import com.databps.bigdaf.admin.service.PrivilegeService;
import com.databps.bigdaf.admin.vo.PrivilegeForViewVo;
import com.databps.bigdaf.admin.vo.PrivilegeVo;
import com.databps.bigdaf.core.util.DateUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * HdpAppUserServiceImpl
 *
 * @author lgc
 * @create 2017-08-10 下午2:25
 */

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

  @Autowired
  private PrivilegeDao privilegeDao;

  @Autowired
  private PolicyDao policyDao;

  public Page<PrivilegeVo> findAllByName(Pageable pageable) {

    return null;
  }

  @Override
  public void insert(PrivilegeVo vo) {

    Policy oldpolicy = privilegeDao.findByCriteria("type", "hdfs");
    Policy policy = new Policy();
    String nowDate = DateUtils.getNowDateTime();
    policy.setCreateTime(nowDate);
    policy.setType("hdfs");
    policy.setPrivileges(null);

    List<Privilege> privilegeList = new ArrayList<>();

    Privilege privilege = getPrivilege(vo, nowDate, nowDate);

    privilegeList.add(privilege);

    policy.setPrivileges(privilegeList);

    if (oldpolicy == null) {

      privilegeDao.insert(policy);
    } else {
      Policy privileges = privilegeDao.findByCriteria("privileges.name", vo.getName());
      if (privileges == null) {
        privilegeDao.upset("hdfs", privilege);
      }
    }


  }

  private Privilege getPrivilege(PrivilegeVo vo, String nowDate, String updateDate) {
    Privilege privilege = new Privilege();
    privilege.setName(vo.getName());
    privilege.setDescription(vo.getDescription());
    privilege.setCreateTime(nowDate);
    privilege.setUpdateTime(nowDate);
    Privilege.Resource re = new Privilege.Resource();
    Privilege.Path path = new Privilege.Path();
    path.setValues(new String[]{vo.getPath()});
    re.setPath(path);
    privilege.setResources(re);

    String[] list = vo.getAccesses();
    List<PrivilegeItems> privilegeItemsList = new ArrayList<>();
    for (String access : list) {
      PrivilegeItems privilegeItems = new PrivilegeItems();
      List<Accesse> accessesList = new ArrayList<>();
      Accesse accesse = new Accesse();
      accesse.setType(access);
      accessesList.add(accesse);
      privilegeItems.setAccesses(accessesList);
      privilegeItems.setUsers(new HashSet<String>());
      privilegeItems.setGroups(new HashSet<String>());
      privilegeItemsList.add(privilegeItems);

    }
    privilege.setPrivilege_items(privilegeItemsList);
    return privilege;
  }

  @Override
  public void updateOrInsert(PrivilegeVo vo, String mainname) {
    if (StringUtils.isNotBlank(mainname)) {//判断是否修改,不为空是更新，为空是插入
      Policy privileges = privilegeDao.findOneByName(mainname);
      String nowDate = DateUtils.getNowDateTime();

      if (privileges != null) {
        Privilege privilege = privileges.getPrivileges().get(0);

        if (privilege.getName().equals(mainname)) {//说明是在更新，但是不应该能够重复添加相同的权限名字
          privilegeDao.updateByName("hdfs", getPrivilege(vo, privilege.getCreateTime(), nowDate),
              privilege.getName());
        }else {
          privilegeDao.updateByName("hdfs", getPrivilege(vo, privilege.getCreateTime(), nowDate),
              mainname);
        }
      } else {
      }

    } else {
      insert(vo);
    }
  }

  @Override
  public void deleteByUserName(String username) {
    privilegeDao.deleteByUserName(username);

  }

  @Override
  public void deleteByName(String name) {
    privilegeDao.deleteByName(name);
  }

  @Override
  public void update(PrivilegeVo vo) {

  }

  @Override
  public Policy findAllByName(Pageable pageable, String name) {
    Policy oldpolicy = privilegeDao.findByCriteria(name);
    return oldpolicy;
  }

  @Override
  public PrivilegeForViewVo findAllByUserName(Pageable pageable, String userName) {
    PrivilegeForViewVo privilegeForViewVo = new PrivilegeForViewVo();
    Policy oldpolicy = privilegeDao.findByCriteria("type", "hdfs");
    List<Privilege> privilegeList = oldpolicy.getPrivileges();
    for (Privilege privilege : privilegeList) {
      boolean flag = true;
      for (PrivilegeItems PrivilegeItems : privilege.getPrivilege_items()) {
        Set<String> userlist = PrivilegeItems.getUsers();
        if (userlist.contains(userName)) {
          privilegeForViewVo.getUsedPrivileges().add(privilege.getName());
          flag = false;
          break;
        }
      }
      if (flag) {
        privilegeForViewVo.getNotUsedPrivileges().add(privilege.getName());
      }
    }
    return privilegeForViewVo;
  }

  @Override
  public PrivilegeForViewVo findAllByGroupName(Pageable pageable, String groupName) {
    PrivilegeForViewVo privilegeForViewVo = new PrivilegeForViewVo();
    Policy oldpolicy = privilegeDao.findByCriteria("type", "hdfs");
    List<Privilege> privilegeList = oldpolicy.getPrivileges();
    for (Privilege privilege : privilegeList) {
      boolean flag = true;
      for (PrivilegeItems PrivilegeItems : privilege.getPrivilege_items()) {
        Set<String> grouplist = PrivilegeItems.getGroups();
        if (grouplist.contains(groupName)) {
          privilegeForViewVo.getUsedPrivileges().add(privilege.getName());
          flag = false;
          break;
        }
      }
      if (flag) {
        privilegeForViewVo.getNotUsedPrivileges().add(privilege.getName());
      }
    }
    return privilegeForViewVo;
  }

  @Override
  public void addPrivilege(String[] names, String username) {
    Set<String> nameSet = new HashSet<String>();
    for (int i = 0; i < names.length; i++) {
      nameSet.add(names[i]);
    }
    Policy oldpolicy = privilegeDao.findByCriteria("type", "hdfs");
    List<Privilege> privilegeList = oldpolicy.getPrivileges();
    for (Privilege privilege : privilegeList) {
      if (nameSet.contains(privilege.getName())) {
        if (privilege.getPrivilege_items().get(0).getUsers().contains(username)) {
          nameSet.remove(privilege.getName());
          continue;
        }
      } else {
        if (privilege.getPrivilege_items().get(0).getUsers().contains(username)) {
          for (PrivilegeItems PrivilegeItems : privilege.getPrivilege_items()) {
            PrivilegeItems.getUsers().remove(username);
          }
        }
      }
    }
    if (!nameSet.isEmpty()) {
      for (Privilege privilege : privilegeList) {
        if (nameSet.contains(privilege.getName())) {
          for (PrivilegeItems PrivilegeItems : privilege.getPrivilege_items()) {
            PrivilegeItems.getUsers().add(username);
          }
        }
      }
    }
    policyDao.updatePrivileges("hdfs", oldpolicy);
  }

  @Override
  public void modifyGroupPrivilege(String[] names, String groupName) {
    Set<String> nameSet = new HashSet<String>();
    for (int i = 0; i < names.length; i++) {
      nameSet.add(names[i]);
    }
    Policy oldpolicy = privilegeDao.findByCriteria("type", "hdfs");
    List<Privilege> privilegeList = oldpolicy.getPrivileges();
    for (Privilege privilege : privilegeList) {
      if (nameSet.contains(privilege.getName())) {
        if (privilege.getPrivilege_items().get(0).getGroups().contains(groupName)) {
          nameSet.remove(privilege.getName());
          continue;
        }
      } else {
        if (privilege.getPrivilege_items().get(0).getGroups().contains(groupName)) {
          for (PrivilegeItems PrivilegeItems : privilege.getPrivilege_items()) {
            PrivilegeItems.getGroups().remove(groupName);
          }
        }

      }
    }
    if (!nameSet.isEmpty()) {
      for (Privilege privilege : privilegeList) {
        if (nameSet.contains(privilege.getName())) {
          for (PrivilegeItems PrivilegeItems : privilege.getPrivilege_items()) {
            PrivilegeItems.getGroups().add(groupName);
          }
        }
      }
    }
    policyDao.updatePrivileges("hdfs", oldpolicy);
  }

  @Override
  public Policy findOneByName(String name) {
    return privilegeDao.findOneByName(name);
  }

  @Override
  public Map<String, String> checkName(String name, String id) {
    Map<String, String> map = new HashMap<String, String>();
    if (StringUtils.isBlank(id)) {
      if (privilegeDao.findOneByName(name) != null) {
        map.put("error", "权限名已经存在");

      } else {
        map.put("ok", "");
      }
    } else {
      Policy user = privilegeDao.findOneByName(name);
      if (user!=null) {
        if (user.getPrivileges().get(0).getName().equals(id)) {
          map.put("ok", "");
        } else {
          map.put("error", "权限名已经存在");
        }
      } else {
        map.put("ok", "");
      }
    }
    return map;
  }

}
