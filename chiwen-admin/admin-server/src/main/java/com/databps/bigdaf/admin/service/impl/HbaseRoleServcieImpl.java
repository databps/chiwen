package com.databps.bigdaf.admin.service.impl;

import com.databps.bigdaf.admin.dao.*;
import com.databps.bigdaf.admin.domain.*;
import com.databps.bigdaf.admin.service.HbaseRoleService;
import com.databps.bigdaf.admin.service.HbaseUserService;
import com.databps.bigdaf.admin.vo.HbaseRoleVo;
import com.databps.bigdaf.admin.vo. HbaseRoleVo;
import com.databps.bigdaf.core.common.AuditType;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import com.databps.bigdaf.core.util.DateUtils;
import com.databps.bigdaf.core.util.HttpsUtils;
import com.databps.bigdaf.core.util.RequestIPUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author shibingxin
 * @create 2017-09-01 上午10:25
 */
@Service
public class HbaseRoleServcieImpl implements HbaseRoleService {



  @Autowired
  private HbaseRoleDao gatewayCharactorDao  ;
  @Autowired
  private HbasePrivilegeDao  HbasePrivliegeDao;


  @Override
  public List<HbaseRole> findGatewayRolesPage(String id) {
    return null;
  }

  @Override
  public HbaseRole findOneGr(String id) {
    return null;
  }

  public List<HbaseRoleVo> findAllByName(MongoPage page, String name) {

    List<HbaseRole> groupList = gatewayCharactorDao.findAllByName(page, name);

    List< HbaseRoleVo> voList = new ArrayList<>();

    for (HbaseRole group : groupList) {
       HbaseRoleVo  HbaseRoleVo = new  HbaseRoleVo();
       HbaseRoleVo.setName(group.getName());
       HbaseRoleVo.setDescription(group.getDescription());
       HbaseRoleVo.set_id(group.get_id());
       HbaseRoleVo.setCreateTime(group.getCreateTime());
      voList.add( HbaseRoleVo);
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
    HbaseRole group = gatewayCharactorDao.findOneById(id);
    if (group != null) {
      //privilegeDao.deleteByName(group.getName());
      gatewayCharactorDao.deleteById(id);
    }

  }

  @Override
  public  HbaseRoleVo findOneById(String id) {
    HbaseRole group = gatewayCharactorDao.findOneById(id);
     HbaseRoleVo vo = new  HbaseRoleVo();
    vo.set_id(group.get_id());
    vo.setDescription(group.getDescription());
    vo.setName(group.getName());
    return vo;
  }

  @Override
  public void modifyGroupbyName(String name) {

  }




  @Override
  public void insert( HbaseRoleVo vo) {
    HbaseRole group = new HbaseRole();
    group.setCreateTime(DateUtils.getNowDateTime());
    group.setName(vo.getName());
    group.setDescription(vo.getDescription());
    group.setUpdateTime(DateUtils.getNowDateTime());
    gatewayCharactorDao.insert(group);
  }

  @Override
  public void update( HbaseRoleVo vo, String mainname){
    HbaseRole group = new HbaseRole();
    //group.setUpdateTime(DateUtils.getNowDateTime());
    group.setName(vo.getName());
    group.setDescription(vo.getDescription());
    gatewayCharactorDao.update(group, mainname);
  }

  @Override
  public Map<String, String> checkName(String name, String id) {
    Map<String, String> map = new HashMap<String, String>();
    if (org.apache.commons.lang3.StringUtils.isBlank(id)) {
      if (gatewayCharactorDao.findOneByName(name) != null) {
        map.put("error", "组名已经存在");

      } else {
        map.put("ok", "");
      }
    } else {
      HbaseRole user = gatewayCharactorDao.findOneByName(name);
      if (user != null) {
        if (user.get_id().equals(id)) {
          map.put("ok", "");
        } else {
          map.put("error", "组名已经存在");
        }
      } else {
        map.put("ok", "");
      }
    }
    return map;
  }



  @Override
  public  HbaseRoleVo findHbasePrivliegeAllByRoleId(MongoPage pageable, String id) {
     HbaseRoleVo  privilegeForViewVo = new  HbaseRoleVo();
    HbaseRole gatewayUserrole = gatewayCharactorDao.findOneById(id);
    List<Map<String, String>> privileges= gatewayUserrole.getPrivileges();
    if(privileges==null){
      privileges=new ArrayList<Map<String, String>>();
    }
    Iterator<Map<String, String>> hbasePrivlieges=  privileges.iterator();

    while (hbasePrivlieges.hasNext()) {
      List<Map> privilegeList = new ArrayList<Map>();
      Map<String, String> gatewayRole2 = hbasePrivlieges.next();
      List<HbasePrivliege> hbasePrivlieges2 = HbasePrivliegeDao.findHbasePrivliegesPage(null,null,gatewayRole2.get("privilege_id").toString());
     if(hbasePrivlieges2.size()>0) {
       privilegeForViewVo.getUsedHbasePrivilege().add(hbasePrivlieges2.get(0).getName());
     }
    }
    Set<String> UsedRoles  =  privilegeForViewVo.getUsedHbasePrivilege();
    List<HbasePrivliege> HbasePrivlieges = HbasePrivliegeDao.findHbasePrivliegesPage(null,null,null);
    Iterator<HbasePrivliege> gatewayRole2 =  HbasePrivlieges.iterator();
    while (gatewayRole2.hasNext()) {
      HbasePrivliege HbasePrivliege22 = gatewayRole2.next();
      if(!UsedRoles.contains( HbasePrivliege22.getName()))
        privilegeForViewVo.getNotUsedHbasePrivilege().add(HbasePrivliege22.getName());

    }
    return privilegeForViewVo;
  }

  @Override
  public void modifyGroupPrivilege(String[] names, String roleid) {
    HbaseRole gatewayUser = gatewayCharactorDao.findOneById(roleid);
    Set<String> nameSet = new HashSet<String>();
    for (int i = 0; i < names.length; i++) {
      HbasePrivliege  HbaseRole=HbasePrivliegeDao.findOneByName2(names[i]) ;
      nameSet.add( HbaseRole.get_id());
    }

    //角色的用户（首先循环原角色拥有的权限）
    if(gatewayUser.getPrivileges()!=null) {
      for (int j = 0; j < gatewayUser.getPrivileges().size(); j++) {
        HbasePrivliege gatawayCharacter2 = HbasePrivliegeDao.findOneById(gatewayUser.getPrivileges().get(j).get("privilege_id"));
        //如果新添加的角色包含旧的角色
        if (!nameSet.contains(gatawayCharacter2.get_id())) {

          //如果不包含就删除掉旧角色的关联的用户
          gatawayCharacter2.getRolesName().remove(gatewayUser.getName());
          HbasePrivliegeDao.update(gatawayCharacter2, gatawayCharacter2.get_id());

        }


      }
    }
    //角色的用户（首先循环新添加的角色）
    for (String str : nameSet) {
      HbasePrivliege gatawayCharacter2 = HbasePrivliegeDao.findOneById(str);
      //如果新添加的角色包含旧的角色
      if(gatawayCharacter2.getRolesName()!=null) {
      if (!gatawayCharacter2.getRolesName().contains(gatewayUser.getName())) {

        //如果不包含就删除掉旧角色的关联的用户

        List<String> username = gatawayCharacter2.getRolesName();
        if (username == null)
          username = new ArrayList<String>();
        username.add(gatewayUser.getName());
        gatawayCharacter2.setRolesName(username);
        HbasePrivliegeDao.update(gatawayCharacter2, gatawayCharacter2.get_id());
      }

    }else if(gatawayCharacter2.getRolesName()==null) {
        List<String> username = gatawayCharacter2.getRolesName();
        if (username == null)
          username = new ArrayList<String>();
        username.add(gatewayUser.getName());
        gatawayCharacter2.setRolesName(username);
        HbasePrivliegeDao.update(gatawayCharacter2, gatawayCharacter2.get_id());

      }
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
  public HbaseRole findOneId(String id) {
    return gatewayCharactorDao.findOneById(id);
  }

}