package com.databps.bigdaf.admin.service;

import com.databps.bigdaf.admin.vo.GatewayUserVo;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by lgc on 17-8-10.
 */
public interface GatewayUserService {

  List<GatewayUserVo> findAllByName(MongoPage pageable,String name,String gatewayOrHdfs);


  GatewayUserVo findOneById(String id);

  void deleteUserbyName(String name);

  void modifyUserbyName(String name);

  void insert(GatewayUserVo vo);

  void update(GatewayUserVo vo,String mainname);

  void deleteById(String id);

  Map<String, String> checkName(String name,String id);
  GatewayUserVo findCharacterAllByUserId(MongoPage pageable,String id);
  public void addPrivilege(String[] names, String username) ;

  long countUser(String cmpyId);
}
