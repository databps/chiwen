package com.databps.bigdaf.admin.service;

import com.databps.bigdaf.admin.domain.GatewayPrivilege;
import com.databps.bigdaf.admin.vo.GatawayCharacterVo;
import com.databps.bigdaf.admin.domain.GatewayCharacter;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;

import java.util.List;
import java.util.Map;

/**
 * Created by lgc on 17-8-14.
 */
public interface GatewayCharactorService {
  List<GatawayCharacterVo> findAllByName(MongoPage pageable, String name);
  void deleteGroupbyName(String name);

  void deleteById(String id);

  GatawayCharacterVo findOneById(String id);

  void modifyGroupbyName(String name);
  void insert(GatawayCharacterVo vo);
  void update(GatawayCharacterVo vo, String mainname);
  Map<String, String> checkName(String name, String id);

  GatawayCharacterVo findGatewayPrivilegeAllByRoleId(MongoPage pageable, String id);
  public void modifyGroupPrivilege(String[] names, String roleid) ;
  GatewayCharacter findOneId(String id);
}
