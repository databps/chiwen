package com.databps.bigdaf.admin.service.impl;

import com.databps.bigdaf.admin.dao.*;
import com.databps.bigdaf.admin.domain.Config;
import com.databps.bigdaf.admin.domain.HbaseRole;
import com.databps.bigdaf.admin.domain. HbaseUser;
import com.databps.bigdaf.admin.domain.Services;
import com.databps.bigdaf.admin.service.HbaseUserService;
import com.databps.bigdaf.admin.vo. HbaseUserVo;
import com.databps.bigdaf.core.common.AuditType;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import com.databps.bigdaf.core.util.DateUtils;
import com.databps.bigdaf.core.util.HttpsUtils;
import com.databps.bigdaf.core.util.RequestIPUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author shibingxin
 * @create 2017-09-01 上午10:25
 */
@Service
public class HbaseUserServcieImpl implements HbaseUserService {

  @Autowired
  private HbaseUserDao hbaseDao;
  @Autowired
  private ConfigDao configDao;
  @Autowired
  private PluginsDao pluginsDao;
  @Autowired
  private ServicesDao servicesDao;




  @Autowired
  private com.databps.bigdaf.admin.dao. HbaseUserDao  HbaseUserDao;
  @Autowired
  private HbaseRoleDao  gatewayCharactorDao  ;
  @Autowired
  private PrivilegeDao privilegeDao;
  @Autowired
  private HbasePrivilegeDao  gatewayPrivilegeDao;


  public List< HbaseUserVo> findAllByName(MongoPage pageable, String name) {

    List< HbaseUser> userList =  HbaseUserDao.findAllByName(pageable, name);

    List< HbaseUserVo> voList = new ArrayList<>();

    for ( HbaseUser  HbaseUser : userList) {
       HbaseUserVo  HbaseUserVo = new  HbaseUserVo();
       HbaseUserVo.setId( HbaseUser.get_id());
       HbaseUserVo.setName( HbaseUser.getName());
       HbaseUserVo.setDescription( HbaseUser.getDescription());
       HbaseUserVo.setCreateTime( HbaseUser.getCreateTime());
      List<Map<String, String>> roles=  HbaseUser.getRoles();
       Set<String> usedRoles = new HashSet<String>();

      if(roles==null){
        roles=new ArrayList<Map<String, String>>();
      }
      Iterator<Map<String, String>> gatewayRole =  roles.iterator();

      while (gatewayRole.hasNext()) {
        List<Map> privilegeList = new ArrayList<Map>();
        Map<String, String> gatewayRole2 = gatewayRole.next();
        List<HbaseRole> gatewayRoles = gatewayCharactorDao.findGatewayRolesPage(null,null,gatewayRole2.get("role_id").toString());
        if(gatewayRoles.size()>0) {
          usedRoles.add(gatewayRoles.get(0).getName());
        }
      }

      HbaseUserVo.setUsedRoles(usedRoles);
      HbaseUserVo.setRoles(usedRoles.toString().replace("[","").replace("]",""));

      //fangzhimeng add 2017-09-08
      // HbaseUserVo.setUPassword( HbaseUser.getUPassword());
      // HbaseUserVo.setRoles( HbaseUser.getRoles());
      voList.add( HbaseUserVo);
    }

    return voList;
  }



  @Override
  public  HbaseUserVo findOneById(String id) {
     HbaseUserVo vo = new  HbaseUserVo();
     HbaseUser user =  HbaseUserDao.findOneById(id);
    vo.setCreateTime(user.getCreateTime());
    vo.setDescription(user.getDescription());
    vo.setName(user.getName());
    vo.setId(user.get_id());
    return vo;
  }

  @Override
  public void deleteUserbyName(String name) {
     HbaseUserDao.deleteUser(name);
  }

  @Override
  public void modifyUserbyName(String name) {

  }

  @Override
  public void insert( HbaseUserVo vo) {
     HbaseUser user = new  HbaseUser();
    user.setCreateTime(DateUtils.getNowDateTime());
    user.setName(vo.getName());
    user.setDescription(vo.getDescription());
     HbaseUserDao.insert(user);

  }

  @Override
  public void update( HbaseUserVo vo, String mainname) {
     HbaseUser user = new  HbaseUser();
    user.setCreateTime(DateUtils.getNowDateTime());
    user.setName(vo.getName());
    user.setDescription(vo.getDescription());
    user.set_id(vo.getId());
     HbaseUserDao.update(user, mainname);
  }

  @Override
  @Transactional
  public void deleteById(String id) {
     HbaseUser user =  HbaseUserDao.findOneById(id);
    if (user != null) {
      // privilegeDao.deleteByGroupName(user.getName());
       HbaseUserDao.deleteById(id);

    }

  }

  @Override
  public Map<String, String> checkName(String name, String id) {
    Map<String, String> map = new HashMap<String, String>();
    if (org.apache.commons.lang3.StringUtils.isBlank(id)) {
      if ( HbaseUserDao.findOneByName(name) != null) {
        map.put("error", "用户名已经存在");

      } else {
        map.put("ok", "");
      }
    } else {
       HbaseUser  user =  HbaseUserDao.findOneByName(name);
      if (user!=null) {
        if (user.get_id().equals(id)) {
          map.put("ok", "");
        } else {
          map.put("error", "用户名已经存在");
        }
      } else {
        map.put("ok", "");
      }
    }
    return map;
  }

  @Override
  public  HbaseUserVo findCharacterAllByUserId(MongoPage pageable, String id) {


     HbaseUserVo  privilegeForViewVo = new  HbaseUserVo();
     HbaseUser  HbaseUser =  HbaseUserDao.findOneById(id);
    List<Map<String, String>> roles=  HbaseUser.getRoles();
    if(roles==null){
      roles=new ArrayList<Map<String, String>>();
    }
    Iterator<Map<String, String>> gatewayRole =  roles.iterator();

    while (gatewayRole.hasNext()) {
      List<Map> privilegeList = new ArrayList<Map>();
      Map<String, String> gatewayRole2 = gatewayRole.next();
      List<HbaseRole> gatewayRoles = gatewayCharactorDao.findGatewayRolesPage(null,null,gatewayRole2.get("role_id").toString());
     if(gatewayRoles.size()>0) {
       privilegeForViewVo.getUsedRoles().add(gatewayRoles.get(0).getName());
     }
    }
    Set<String> UsedRoles  =  privilegeForViewVo.getUsedRoles();
    List<HbaseRole> gatewayRoles = gatewayCharactorDao.findGatewayRolesPage(null,null,null);
    Iterator<HbaseRole> gatewayRole2 =  gatewayRoles.iterator();
    while (gatewayRole2.hasNext()) {
      HbaseRole gatewayRole22 = gatewayRole2.next();
      if(!UsedRoles.contains( gatewayRole22.getName()))
        privilegeForViewVo.getNotUsedRoles().add(gatewayRole22.getName());

    }
    return privilegeForViewVo;


  }

  @Override
  public void addPrivilege(String[] names, String userid) {


    HbaseUser  HbaseUser =  HbaseUserDao.findOneById(userid);
    Set<String> nameSet = new HashSet<String>();
    for (int i = 0; i < names.length; i++) {
      HbaseRole gatawayCharacter=gatewayCharactorDao.findOneByName(names[i]) ;

      nameSet.add(gatawayCharacter.get_id());
    }

    //角色的用户（首先循环原用户拥有的角色）
    if(HbaseUser.getRoles()!=null) {
      for (int j = 0; j < HbaseUser.getRoles().size(); j++) {
        HbaseRole gatawayCharacter2 = gatewayCharactorDao.findOneById(HbaseUser.getRoles().get(j).get("role_id"));
        //如果新添加的角色包含旧的角色
        if (!nameSet.contains(gatawayCharacter2.get_id())) {

          //如果不包含就删除掉旧角色的关联的用户
          gatawayCharacter2.getUsersName().remove(HbaseUser.getName());
          gatewayCharactorDao.update(gatawayCharacter2, gatawayCharacter2.get_id());

        }


      }
    }
    //角色的用户（首先循环新添加的角色）
    for (String str : nameSet) {
      HbaseRole gatawayCharacter2 = gatewayCharactorDao.findOneById(str);
      //如果新添加的角色包含旧的角色
      if (gatawayCharacter2.getUsersName() != null) {
        if (!gatawayCharacter2.getUsersName().contains(HbaseUser.getName())) {

          //如果不包含就删除掉旧角色的关联的用户

          List<String> username = gatawayCharacter2.getUsersName();
          if (username == null)
            username = new ArrayList<String>();
          username.add(HbaseUser.getName());
          gatawayCharacter2.setUsersName(username);
          gatewayCharactorDao.update(gatawayCharacter2, gatawayCharacter2.get_id());
        }


      }else if (gatawayCharacter2.getUsersName() == null){
        //如果不包含就删除掉旧角色的关联的用户

        List<String> username = gatawayCharacter2.getUsersName();
        if (username == null)
          username = new ArrayList<String>();
        username.add(HbaseUser.getName());
        gatawayCharacter2.setUsersName(username);
        gatewayCharactorDao.update(gatawayCharacter2, gatawayCharacter2.get_id());
      }
    }


    if( HbaseUser.getRoles()!=null){
      HbaseUser.getRoles().clear();
    }
    List<Map<String,String>>   list=  new ArrayList<Map<String,String>>();
    HbaseUser.setRoles(list);

    for (String str : nameSet) {
      HashMap<String,String> userMap=new HashMap<String,String>();
      userMap.put("role_id",str);
      userMap.put("role_type","gateway");
      HbaseUser.getRoles().add(userMap);

    }



    HbaseUserDao.update( HbaseUser, userid);
  }

}