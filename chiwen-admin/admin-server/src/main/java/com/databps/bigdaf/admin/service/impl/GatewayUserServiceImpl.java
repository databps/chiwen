package com.databps.bigdaf.admin.service.impl;

import com.databps.bigdaf.admin.dao.GatewayCharactorDao;
import com.databps.bigdaf.admin.dao.GatewayPrivilegeDao;
import com.databps.bigdaf.admin.dao.GatewayUserDao;
import com.databps.bigdaf.admin.dao.PrivilegeDao;
import com.databps.bigdaf.admin.domain.GatewayCharacter;
import com.databps.bigdaf.admin.domain.GatewayUser;
import com.databps.bigdaf.admin.service.GatewayUserService;
import com.databps.bigdaf.admin.vo.GatewayUserVo;
import com.databps.bigdaf.admin.vo.PrivilegeForViewVo;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import com.databps.bigdaf.core.util.DateUtils;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * GatewayUserServiceImpl
 *
 * @author lgc
 * @create 2017-08-10 下午2:25
 */

@Service
public class GatewayUserServiceImpl implements GatewayUserService {

  @Autowired
  private GatewayUserDao GatewayUserDao;
  @Autowired
  private GatewayCharactorDao gatewayCharactorDao  ;
  @Autowired
  private PrivilegeDao privilegeDao;
  @Autowired
  private GatewayPrivilegeDao gatewayPrivilegeDao;


  public List<GatewayUserVo> findAllByName(MongoPage pageable, String name,String gatewayOrHdfs) {

//    List<GatewayUser> userList = GatewayUserDao.findAllByName(pageable, name);
    List<GatewayUser> userList = GatewayUserDao.findAllByName(pageable, name,gatewayOrHdfs);
    List<GatewayUserVo> voList = new ArrayList<>();

    for (GatewayUser gatewayUser : userList) {
      GatewayUserVo GatewayUserVo = new GatewayUserVo();
      GatewayUserVo.setId(gatewayUser.getId());
      GatewayUserVo.setUsername(gatewayUser.getuName());
      GatewayUserVo.setDescription(gatewayUser.getDescription());
      GatewayUserVo.setCreateTime(gatewayUser.getCreateTime());
      //fangzhimeng add 2017-09-08
      GatewayUserVo.setUPassword(gatewayUser.getUPassword());
      GatewayUserVo.setRoles(gatewayUser.getRoles());
      List<Map<String, String>> roles= gatewayUser.getRoles();
      Set<String> usedRoles = new HashSet<String>();

      if(roles==null){
        roles=new ArrayList<Map<String, String>>();
      }
      Iterator<Map<String, String>> gatewayRole =  roles.iterator();

      while (gatewayRole.hasNext()) {
        List<Map> privilegeList = new ArrayList<Map>();
        Map<String, String> gatewayRole2 = gatewayRole.next();
        List<GatewayCharacter> gatewayRoles = gatewayPrivilegeDao.findGatewayRolesPage(null,null,gatewayRole2.get("role_id").toString());

        if(gatewayRoles.size()>0) {
          usedRoles.add(gatewayRoles.get(0).getName());
        }
      }
      GatewayUserVo.setOwnRoles(usedRoles.toString().replace("[","").replace("]",""));
      voList.add(GatewayUserVo);
    }

    return voList;
  }



  @Override
  public GatewayUserVo findOneById(String id) {
    GatewayUserVo vo = new GatewayUserVo();
    GatewayUser user = GatewayUserDao.findOneById(id);
    vo.setCreateTime(user.getCreateTime());
    vo.setDescription(user.getDescription());
    vo.setUsername(user.getuName());
    vo.setId(user.getId());
    return vo;
  }

  @Override
  public void deleteUserbyName(String name) {
    GatewayUserDao.deleteUser(name);
  }

  @Override
  public void modifyUserbyName(String name) {

  }

  @Override
  public void insert(GatewayUserVo vo) {
    GatewayUser user = new GatewayUser();
    user.setCreateTime(DateUtils.getNowDateTime());
    user.setuName(vo.getUsername());
    user.setDescription(vo.getDescription());
    user.setUPassword(vo.getUPassword());
    user.setGatewayOrHdfs(vo.getGatewayOrHdfs());
    GatewayUserDao.insert(user);

  }

  @Override
  public void update(GatewayUserVo vo, String mainname) {
    GatewayUser user = new GatewayUser();
    user.setCreateTime(DateUtils.getNowDateTime());
    user.setuName(vo.getUsername());
    user.setDescription(vo.getDescription());
    user.setId(vo.getId());
    user.setUPassword(vo.getUPassword());
    GatewayUserDao.update(user, mainname);
  }

  @Override
  @Transactional
  public void deleteById(String id) {
    GatewayUser user = GatewayUserDao.findOneById(id);
    if (user != null) {
     // privilegeDao.deleteByGroupName(user.getuName());
      GatewayUserDao.deleteById(id);

    }

  }

  @Override
  public Map<String, String> checkName(String name, String id) {
    Map<String, String> map = new HashMap<String, String>();
    if (StringUtils.isBlank(id)) {
      if (GatewayUserDao.findOneByName(name) != null) {
        map.put("error", "用户名已经存在");

      } else {
        map.put("ok", "");
      }
    } else {
      GatewayUser  user = GatewayUserDao.findOneByName(name);
      if (user!=null) {
        if (user.getId().equals(id)) {
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
  public GatewayUserVo findCharacterAllByUserId(MongoPage pageable, String id) {


    GatewayUserVo  privilegeForViewVo = new GatewayUserVo();
    GatewayUser gatewayUser = GatewayUserDao.findOneById(id);
    List<Map<String, String>> roles= gatewayUser.getRoles();
    if(roles==null){
      roles=new ArrayList<Map<String, String>>();
    }
    Iterator<Map<String, String>> gatewayRole =  roles.iterator();
    while (gatewayRole.hasNext()) {
      List<Map> privilegeList = new ArrayList<Map>();
      Map<String, String> gatewayRole2 = gatewayRole.next();
      List<GatewayCharacter> gatewayRoles = gatewayPrivilegeDao.findGatewayRolesPage(null,null,gatewayRole2.get("role_id").toString());
     if(gatewayRoles.size()>0) {
       privilegeForViewVo.getUsedRoles().add(gatewayRoles.get(0).getName());
     }
    }
    Set<String> UsedRoles  =  privilegeForViewVo.getUsedRoles();
    List<GatewayCharacter> gatewayRoles = gatewayPrivilegeDao.findGatewayRolesPage(null,null,null);
    Iterator<GatewayCharacter> gatewayRole2 =  gatewayRoles.iterator();
    while (gatewayRole2.hasNext()) {
      GatewayCharacter gatewayRole22 = gatewayRole2.next();
         if(!UsedRoles.contains( gatewayRole22.getName()))
           privilegeForViewVo.getNotUsedRoles().add(gatewayRole22.getName());

    }
    return privilegeForViewVo;


  }

  @Override
  public void addPrivilege(String[] names, String userid) {
    GatewayUser gatewayUser = GatewayUserDao.findOneById(userid);
    Set<String> nameSet = new HashSet<String>();
    for (int i = 0; i < names.length; i++) {
      GatewayCharacter gatawayCharacter=gatewayCharactorDao.findOneByName(names[i]) ;
      nameSet.add(gatawayCharacter.get_id());
    }

    if(gatewayUser.getRoles()!=null){
    gatewayUser.getRoles().clear();
    }
    List<Map<String,String>>   list=  new ArrayList<Map<String,String>>();
    gatewayUser.setRoles(list);

    for (String str : nameSet) {
      HashMap<String,String> userMap=new HashMap<String,String>();
      userMap.put("role_id",str);
      userMap.put("role_type","gateway");
      gatewayUser.getRoles().add(userMap);

    }



    GatewayUserDao.update(gatewayUser, userid);
  }

  public long countUser(String cmpyId) {
    return GatewayUserDao.countUser(cmpyId);
  }
}