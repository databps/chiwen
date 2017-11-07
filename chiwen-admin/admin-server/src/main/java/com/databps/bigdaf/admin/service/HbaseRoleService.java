package com.databps.bigdaf.admin.service;

import com.databps.bigdaf.admin.domain.HbaseRole;
import com.databps.bigdaf.admin.domain.HbasePrivliege;
import com.databps.bigdaf.admin.vo.HbaseRoleVo;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by shibingxin on 2017/9/1.
 */
public interface HbaseRoleService {


  List<HbaseRole> findGatewayRolesPage(String id);
  HbaseRole findOneGr(String id);
  List<HbaseRoleVo> findAllByName(MongoPage pageable, String name);
  void deleteGroupbyName(String name);

  void deleteById(String id);

  HbaseRoleVo findOneById(String id);

  void modifyGroupbyName(String name);
  void insert(HbaseRoleVo vo);
  void update(HbaseRoleVo vo, String mainname);
  Map<String, String> checkName(String name, String id);

  HbaseRoleVo findHbasePrivliegeAllByRoleId(MongoPage pageable, String id);
  public void modifyGroupPrivilege(String[] names, String roleid) ;
  HbaseRole findOneId(String id);
}
