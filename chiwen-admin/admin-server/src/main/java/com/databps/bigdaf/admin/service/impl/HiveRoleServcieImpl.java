package com.databps.bigdaf.admin.service.impl;

import com.databps.bigdaf.admin.dao.HivePrivilegeDao;
import com.databps.bigdaf.admin.dao.HiveRoleDao;
import com.databps.bigdaf.admin.domain.HivePrivliege;
import com.databps.bigdaf.admin.domain.HiveRole;
import com.databps.bigdaf.admin.service.HiveRoleService;
import com.databps.bigdaf.admin.vo.HiveRoleVo;
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
public class HiveRoleServcieImpl implements HiveRoleService {



  @Autowired
  private HiveRoleDao gatewayCharactorDao  ;
  @Autowired
  private HivePrivilegeDao  HivePrivliegeDao;


  @Override
  public List<HiveRole> findGatewayRolesPage(String id) {
    return null;
  }

  @Override
  public HiveRole findOneGr(String id) {
    return null;
  }

  public List<HiveRoleVo> findAllByName(MongoPage page, String name) {

    List<HiveRole> groupList = gatewayCharactorDao.findAllByName(page, name);

    List< HiveRoleVo> voList = new ArrayList<>();

    for (HiveRole group : groupList) {
       HiveRoleVo  HiveRoleVo = new  HiveRoleVo();
       HiveRoleVo.setName(group.getName());
       HiveRoleVo.setDescription(group.getDescription());
       HiveRoleVo.set_id(group.get_id());
       HiveRoleVo.setCreateTime(group.getCreateTime());
      voList.add( HiveRoleVo);
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
    HiveRole group = gatewayCharactorDao.findOneById(id);
    if (group != null) {
      //privilegeDao.deleteByName(group.getName());
      gatewayCharactorDao.deleteById(id);
    }

  }

  @Override
  public  HiveRoleVo findOneById(String id) {
    HiveRole group = gatewayCharactorDao.findOneById(id);
     HiveRoleVo vo = new  HiveRoleVo();
    vo.set_id(group.get_id());
    vo.setDescription(group.getDescription());
    vo.setName(group.getName());
    return vo;
  }

  @Override
  public void modifyGroupbyName(String name) {

  }




  @Override
  public void insert( HiveRoleVo vo) {
    HiveRole group = new HiveRole();
    group.setCreateTime(DateUtils.getNowDateTime());
    group.setName(vo.getName());
    group.setDescription(vo.getDescription());
    group.setUpdateTime(DateUtils.getNowDateTime());
    gatewayCharactorDao.insert(group);
  }

  @Override
  public void update( HiveRoleVo vo, String mainname){
    HiveRole group = new HiveRole();
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
      HiveRole user = gatewayCharactorDao.findOneByName(name);
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
  public  HiveRoleVo findHivePrivliegeAllByRoleId(MongoPage pageable, String id) {
     HiveRoleVo  privilegeForViewVo = new  HiveRoleVo();
    HiveRole gatewayUserrole = gatewayCharactorDao.findOneById(id);
    List<Map<String, String>> privileges= gatewayUserrole.getPrivileges();
    if(privileges==null){
      privileges=new ArrayList<Map<String, String>>();
    }
    Iterator<Map<String, String>> hivePrivlieges=  privileges.iterator();

    while (hivePrivlieges.hasNext()) {
      List<Map> privilegeList = new ArrayList<Map>();
      Map<String, String> gatewayRole2 = hivePrivlieges.next();
      List<HivePrivliege> hivePrivlieges2 = HivePrivliegeDao.findHivePrivliegesPage(null,null,gatewayRole2.get("privilege_id").toString());
     if(hivePrivlieges2.size()>0) {
       privilegeForViewVo.getUsedHivePrivilege().add(hivePrivlieges2.get(0).getName());
     }
    }
    Set<String> UsedRoles  =  privilegeForViewVo.getUsedHivePrivilege();
    List<HivePrivliege> HivePrivlieges = HivePrivliegeDao.findHivePrivliegesPage(null,null,null);
    Iterator<HivePrivliege> gatewayRole2 =  HivePrivlieges.iterator();
    while (gatewayRole2.hasNext()) {
      HivePrivliege HivePrivliege22 = gatewayRole2.next();
      if(!UsedRoles.contains( HivePrivliege22.getName()))
        privilegeForViewVo.getNotUsedHivePrivilege().add(HivePrivliege22.getName());

    }
    return privilegeForViewVo;
  }

  @Override
  public void modifyGroupPrivilege(String[] names, String roleid) {
    HiveRole gatewayUser = gatewayCharactorDao.findOneById(roleid);
    Set<String> nameSet = new HashSet<String>();
    for (int i = 0; i < names.length; i++) {
      HivePrivliege  HiveRole=HivePrivliegeDao.findOneByName2(names[i]) ;
      nameSet.add( HiveRole.get_id());
    }

    //角色的用户（首先循环原角色拥有的权限）
    if(gatewayUser.getPrivileges()!=null) {
      for (int j = 0; j < gatewayUser.getPrivileges().size(); j++) {
        HivePrivliege gatawayCharacter2 = HivePrivliegeDao.findOneById(gatewayUser.getPrivileges().get(j).get("privilege_id"));
        //如果新添加的角色包含旧的角色
        if (!nameSet.contains(gatawayCharacter2.get_id())) {

          //如果不包含就删除掉旧角色的关联的用户
          gatawayCharacter2.getRolesName().remove(gatewayUser.getName());
          HivePrivliegeDao.update(gatawayCharacter2, gatawayCharacter2.get_id());

        }


      }
    }
    //角色的用户（首先循环新添加的角色）
    for (String str : nameSet) {
      HivePrivliege gatawayCharacter2 = HivePrivliegeDao.findOneById(str);
      //如果新添加的角色包含旧的角色
      if(gatawayCharacter2.getRolesName()!=null) {
      if (!gatawayCharacter2.getRolesName().contains(gatewayUser.getName())) {

        //如果不包含就删除掉旧角色的关联的用户

        List<String> username = gatawayCharacter2.getRolesName();
        if (username == null)
          username = new ArrayList<String>();
        username.add(gatewayUser.getName());
        gatawayCharacter2.setRolesName(username);
        HivePrivliegeDao.update(gatawayCharacter2, gatawayCharacter2.get_id());
      }

    }else if(gatawayCharacter2.getRolesName()==null) {
        List<String> username = gatawayCharacter2.getRolesName();
        if (username == null)
          username = new ArrayList<String>();
        username.add(gatewayUser.getName());
        gatawayCharacter2.setRolesName(username);
        HivePrivliegeDao.update(gatawayCharacter2, gatawayCharacter2.get_id());

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
  public HiveRole findOneId(String id) {
    return gatewayCharactorDao.findOneById(id);
  }

}