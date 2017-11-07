package com.databps.bigdaf.admin.service.impl;

import com.databps.bigdaf.admin.dao.*;
import com.databps.bigdaf.admin.domain.HiveRole;
import com.databps.bigdaf.admin.domain.HiveUser;
import com.databps.bigdaf.admin.service.HiveUserService;
import com.databps.bigdaf.admin.vo.HiveUserVo;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import com.databps.bigdaf.core.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author shibingxin
 * @create 2017-09-01 上午10:25
 */
@Service
public class HiveUserServcieImpl implements HiveUserService {

  @Autowired
  private HiveUserDao hiveDao;
  @Autowired
  private ConfigDao configDao;
  @Autowired
  private PluginsDao pluginsDao;
  @Autowired
  private ServicesDao servicesDao;




  @Autowired
  private com.databps.bigdaf.admin.dao. HiveUserDao  HiveUserDao;
  @Autowired
  private HiveRoleDao  gatewayCharactorDao  ;
  @Autowired
  private PrivilegeDao privilegeDao;
  @Autowired
  private HivePrivilegeDao  gatewayPrivilegeDao;


  public List< HiveUserVo> findAllByName(MongoPage pageable, String name) {

    List< HiveUser> userList =  HiveUserDao.findAllByName(pageable, name);

    List< HiveUserVo> voList = new ArrayList<>();

    for ( HiveUser  HiveUser : userList) {
       HiveUserVo  HiveUserVo = new  HiveUserVo();
       HiveUserVo.setId( HiveUser.get_id());
       HiveUserVo.setName( HiveUser.getName());
       HiveUserVo.setDescription( HiveUser.getDescription());
       HiveUserVo.setCreateTime( HiveUser.getCreateTime());
      List<Map<String, String>> roles=  HiveUser.getRoles();
       Set<String> usedRoles = new HashSet<String>();

      if(roles==null){
        roles=new ArrayList<Map<String, String>>();
      }
      Iterator<Map<String, String>> gatewayRole =  roles.iterator();

      while (gatewayRole.hasNext()) {
        List<Map> privilegeList = new ArrayList<Map>();
        Map<String, String> gatewayRole2 = gatewayRole.next();
        List<HiveRole> gatewayRoles = gatewayCharactorDao.findGatewayRolesPage(null,null,gatewayRole2.get("role_id").toString());
        if(gatewayRoles.size()>0) {
          usedRoles.add(gatewayRoles.get(0).getName());
        }
      }

      HiveUserVo.setUsedRoles(usedRoles);
      HiveUserVo.setRoles(usedRoles.toString().replace("[","").replace("]",""));

      //fangzhimeng add 2017-09-08
      // HiveUserVo.setUPassword( HiveUser.getUPassword());
      // HiveUserVo.setRoles( HiveUser.getRoles());
      voList.add( HiveUserVo);
    }

    return voList;
  }



  @Override
  public  HiveUserVo findOneById(String id) {
     HiveUserVo vo = new  HiveUserVo();
     HiveUser user =  HiveUserDao.findOneById(id);
    vo.setCreateTime(user.getCreateTime());
    vo.setDescription(user.getDescription());
    vo.setName(user.getName());
    vo.setId(user.get_id());
    return vo;
  }

  @Override
  public void deleteUserbyName(String name) {
     HiveUserDao.deleteUser(name);
  }

  @Override
  public void modifyUserbyName(String name) {

  }

  @Override
  public void insert( HiveUserVo vo) {
     HiveUser user = new  HiveUser();
    user.setCreateTime(DateUtils.getNowDateTime());
    user.setName(vo.getName());
    user.setDescription(vo.getDescription());
     HiveUserDao.insert(user);

  }

  @Override
  public void update( HiveUserVo vo, String mainname) {
     HiveUser user = new  HiveUser();
    user.setCreateTime(DateUtils.getNowDateTime());
    user.setName(vo.getName());
    user.setDescription(vo.getDescription());
    user.set_id(vo.getId());
     HiveUserDao.update(user, mainname);
  }

  @Override
  @Transactional
  public void deleteById(String id) {
     HiveUser user =  HiveUserDao.findOneById(id);
    if (user != null) {
      // privilegeDao.deleteByGroupName(user.getName());
       HiveUserDao.deleteById(id);

    }

  }

  @Override
  public Map<String, String> checkName(String name, String id) {
    Map<String, String> map = new HashMap<String, String>();
    if (org.apache.commons.lang3.StringUtils.isBlank(id)) {
      if ( HiveUserDao.findOneByName(name) != null) {
        map.put("error", "用户名已经存在");

      } else {
        map.put("ok", "");
      }
    } else {
       HiveUser  user =  HiveUserDao.findOneByName(name);
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
  public  HiveUserVo findCharacterAllByUserId(MongoPage pageable, String id) {


     HiveUserVo  privilegeForViewVo = new  HiveUserVo();
     HiveUser  HiveUser =  HiveUserDao.findOneById(id);
    List<Map<String, String>> roles=  HiveUser.getRoles();
    if(roles==null){
      roles=new ArrayList<Map<String, String>>();
    }
    Iterator<Map<String, String>> gatewayRole =  roles.iterator();

    while (gatewayRole.hasNext()) {
      List<Map> privilegeList = new ArrayList<Map>();
      Map<String, String> gatewayRole2 = gatewayRole.next();
      List<HiveRole> gatewayRoles = gatewayCharactorDao.findGatewayRolesPage(null,null,gatewayRole2.get("role_id").toString());
     if(gatewayRoles.size()>0) {
       privilegeForViewVo.getUsedRoles().add(gatewayRoles.get(0).getName());
     }
    }
    Set<String> UsedRoles  =  privilegeForViewVo.getUsedRoles();
    List<HiveRole> gatewayRoles = gatewayCharactorDao.findGatewayRolesPage(null,null,null);
    Iterator<HiveRole> gatewayRole2 =  gatewayRoles.iterator();
    while (gatewayRole2.hasNext()) {
      HiveRole gatewayRole22 = gatewayRole2.next();
      if(!UsedRoles.contains( gatewayRole22.getName()))
        privilegeForViewVo.getNotUsedRoles().add(gatewayRole22.getName());

    }
    return privilegeForViewVo;


  }

  @Override
  public void addPrivilege(String[] names, String userid) {


    HiveUser  HiveUser =  HiveUserDao.findOneById(userid);
    Set<String> nameSet = new HashSet<String>();
    for (int i = 0; i < names.length; i++) {
      HiveRole gatawayCharacter=gatewayCharactorDao.findOneByName(names[i]) ;

      nameSet.add(gatawayCharacter.get_id());
    }

    //角色的用户（首先循环原用户拥有的角色）
    if(HiveUser.getRoles()!=null) {
      for (int j = 0; j < HiveUser.getRoles().size(); j++) {
        HiveRole gatawayCharacter2 = gatewayCharactorDao.findOneById(HiveUser.getRoles().get(j).get("role_id"));
        //如果新添加的角色包含旧的角色
        if (!nameSet.contains(gatawayCharacter2.get_id())) {

          //如果不包含就删除掉旧角色的关联的用户
          gatawayCharacter2.getUsersName().remove(HiveUser.getName());
          gatewayCharactorDao.update(gatawayCharacter2, gatawayCharacter2.get_id());

        }


      }
    }
    //角色的用户（首先循环新添加的角色）
    for (String str : nameSet) {
      HiveRole gatawayCharacter2 = gatewayCharactorDao.findOneById(str);
      //如果新添加的角色包含旧的角色
      if (gatawayCharacter2.getUsersName() != null) {
        if (!gatawayCharacter2.getUsersName().contains(HiveUser.getName())) {

          //如果不包含就删除掉旧角色的关联的用户

          List<String> username = gatawayCharacter2.getUsersName();
          if (username == null)
            username = new ArrayList<String>();
          username.add(HiveUser.getName());
          gatawayCharacter2.setUsersName(username);
          gatewayCharactorDao.update(gatawayCharacter2, gatawayCharacter2.get_id());
        }


      }else if (gatawayCharacter2.getUsersName() == null){
        //如果不包含就删除掉旧角色的关联的用户

        List<String> username = gatawayCharacter2.getUsersName();
        if (username == null)
          username = new ArrayList<String>();
        username.add(HiveUser.getName());
        gatawayCharacter2.setUsersName(username);
        gatewayCharactorDao.update(gatawayCharacter2, gatawayCharacter2.get_id());
      }
    }


    if( HiveUser.getRoles()!=null){
      HiveUser.getRoles().clear();
    }
    List<Map<String,String>>   list=  new ArrayList<Map<String,String>>();
    HiveUser.setRoles(list);

    for (String str : nameSet) {
      HashMap<String,String> userMap=new HashMap<String,String>();
      userMap.put("role_id",str);
      userMap.put("role_type","gateway");
      HiveUser.getRoles().add(userMap);

    }



    HiveUserDao.update( HiveUser, userid);
  }

}