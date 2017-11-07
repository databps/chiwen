package com.databps.bigdaf.admin.service.impl;

import com.databps.bigdaf.admin.dao.GatewayPrivilegeDao;
import com.databps.bigdaf.admin.dao.PrivilegeDao;
import com.databps.bigdaf.admin.dao.GatewayCharactorDao;

import com.databps.bigdaf.admin.domain.*;
import com.databps.bigdaf.admin.service.GatewayCharactorService;
import com.databps.bigdaf.admin.vo.GatawayCharacterVo;
import com.databps.bigdaf.admin.vo.GatawayCharacterVo;
import com.databps.bigdaf.admin.vo.GatewayUserVo;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import com.databps.bigdaf.core.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * GroupServiceImpl
 *
 * @author lgc
 * @create 2017-08-14 下午10:41
 */
@Service
public class GatewayCharactorServiceImpl implements GatewayCharactorService {
  @Autowired
  private GatewayPrivilegeDao gatewayPrivilegeDao;
  @Autowired
  private GatewayCharactorDao gatewayCharactorDao  ;

  @Autowired
  private PrivilegeDao privilegeDao;

  public List<GatawayCharacterVo> findAllByName(MongoPage page, String name) {

    List<GatewayCharacter> groupList = gatewayCharactorDao.findAllByName(page, name);

    List<GatawayCharacterVo> voList = new ArrayList<>();

    for (GatewayCharacter group : groupList) {
      GatawayCharacterVo GatawayCharacterVo = new GatawayCharacterVo();
      GatawayCharacterVo.setName(group.getName());
      GatawayCharacterVo.setDescription(group.getDescription());
      GatawayCharacterVo.setId(group.get_id());
      GatawayCharacterVo.setCreateTime(group.getCreate_time());
      voList.add(GatawayCharacterVo);
    }

    return voList;
  }

  @Override
  public void deleteGroupbyName(String name) {
    gatewayCharactorDao.deleteUserbyName(name);
  }

  @Override
  @Transactional
  public void deleteById(String id) {
    GatewayCharacter group = gatewayCharactorDao.findOneById(id);
    if (group != null) {
      //privilegeDao.deleteByName(group.getName());
      gatewayCharactorDao.deleteById(id);
    }

  }

  @Override
  public GatawayCharacterVo findOneById(String id) {
    GatewayCharacter group = gatewayCharactorDao.findOneById(id);
    GatawayCharacterVo vo = new GatawayCharacterVo();
    vo.setId(group.get_id());
    vo.setDescription(group.getDescription());
    vo.setName(group.getName());
    return vo;
  }

  @Override
  public void modifyGroupbyName(String name) {

  }



  @Override
  public void insert(GatawayCharacterVo vo) {
    GatewayCharacter group = new GatewayCharacter();
    group.setCreate_time(DateUtils.getNowDateTime());
    group.setName(vo.getName());
    group.setDescription(vo.getDescription());
   // group.setUpdateTime(DateUtils.getNowDateTime());
    gatewayCharactorDao.insert(group);
  }

  @Override
  public void update(GatawayCharacterVo vo, String mainname){
    GatewayCharacter group = new GatewayCharacter();
    //group.setUpdateTime(DateUtils.getNowDateTime());
    group.setName(vo.getName());
    group.setDescription(vo.getDescription());
    gatewayCharactorDao.update(group, mainname);
  }

  @Override
  public Map<String, String> checkName(String name, String id) {
    Map<String, String> map = new HashMap<String, String>();
    if (StringUtils.isBlank(id)) {
      if (gatewayCharactorDao.findOneByName(name) != null) {
        map.put("error", "角色名已经存在");

      } else {
        map.put("ok", "");
      }
    } else {
      GatewayCharacter user = gatewayCharactorDao.findOneByName(name);
      if (user != null) {
        if (user.get_id().equals(id)) {
          map.put("ok", "");
        } else {
          map.put("error", "角色名已经存在");
        }
      } else {
        map.put("ok", "");
      }
    }
    return map;
  }

  @Override
  public GatawayCharacterVo findGatewayPrivilegeAllByRoleId(MongoPage pageable, String id) {
    GatawayCharacterVo  privilegeForViewVo = new GatawayCharacterVo();
    GatewayCharacter gatewayUserrole = gatewayCharactorDao.findOneById(id);
    List<Map<String, String>> privileges= gatewayUserrole.getPrivileges();
    if(privileges==null){
      privileges=new ArrayList<Map<String, String>>();
    }
    Iterator<Map<String, String>> gatewayprivileges=  privileges.iterator();

    while (gatewayprivileges.hasNext()) {
      List<Map> privilegeList = new ArrayList<Map>();
      Map<String, String> gatewayRole2 = gatewayprivileges.next();
      List<GatewayPrivilege> gatewayPrivileges = gatewayPrivilegeDao.findGateWayPrivilegesPage(null,null,gatewayRole2.get("privilege_id").toString());
      if(gatewayPrivileges.size()>0)
      privilegeForViewVo.getUsedGatewayPrivilege().add(gatewayPrivileges.get(0).getName());
    }
    Set<String> UsedRoles  =  privilegeForViewVo.getUsedGatewayPrivilege();
    List<GatewayPrivilege> gatewayPrivileges = gatewayPrivilegeDao.findGateWayPrivilegesPage(null,null,null);
    Iterator<GatewayPrivilege> gatewayRole2 =  gatewayPrivileges.iterator();
    while (gatewayRole2.hasNext()) {
      GatewayPrivilege gatewayPrivilege22 = gatewayRole2.next();
      if(!UsedRoles.contains( gatewayPrivilege22.getName()))
        privilegeForViewVo.getNotUsedGatewayPrivilege().add(gatewayPrivilege22.getName());

    }
    return privilegeForViewVo;
  }

  @Override
  public void modifyGroupPrivilege(String[] names, String roleid) {
    GatewayCharacter gatewayUser = gatewayCharactorDao.findOneById(roleid);
    Set<String> nameSet = new HashSet<String>();
    for (int i = 0; i < names.length; i++) {
      GatewayPrivilege gatawayCharacter=gatewayPrivilegeDao.findOneByName2(names[i]) ;
      nameSet.add(gatawayCharacter.get_id());
    }
    if( gatewayUser.getPrivileges()!=null) {
       gatewayUser.getPrivileges().clear();
    }
    List<Map<String,String>>   list=  new ArrayList<Map<String,String>>();
    gatewayUser.setPrivileges(list);

    for (String str : nameSet) {
      HashMap<String,String> userMap=new HashMap<String,String>();
      userMap.put("privilege_id",str);
      gatewayUser.getPrivileges().add(userMap);

    }



    gatewayCharactorDao.update(gatewayUser, roleid);
  }

  @Override
  public GatewayCharacter findOneId(String id) {
    return gatewayCharactorDao.findOneById(id);
  }


}