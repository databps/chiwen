package com.databps.bigdaf.admin.service;

import com.databps.bigdaf.admin.domain.HiveRole;
import com.databps.bigdaf.admin.vo.HiveRoleVo;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;

import java.util.List;
import java.util.Map;

/**
 * Created by shibingxin on 2017/9/1.
 */
public interface HiveRoleService {


  List<HiveRole> findGatewayRolesPage(String id);
  HiveRole findOneGr(String id);
  List<HiveRoleVo> findAllByName(MongoPage pageable, String name);
  void deleteGroupbyName(String name);

  void deleteById(String id);

  HiveRoleVo findOneById(String id);

  void modifyGroupbyName(String name);
  void insert(HiveRoleVo vo);
  void update(HiveRoleVo vo, String mainname);
  Map<String, String> checkName(String name, String id);

  HiveRoleVo findHivePrivliegeAllByRoleId(MongoPage pageable, String id);
  public void modifyGroupPrivilege(String[] names, String roleid) ;
  HiveRole findOneId(String id);
}
