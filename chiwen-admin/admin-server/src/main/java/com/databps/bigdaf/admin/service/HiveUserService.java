package com.databps.bigdaf.admin.service;

import com.databps.bigdaf.admin.vo.HiveUserVo;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;

import java.util.List;
import java.util.Map;

/**
 * Created by shibingxin on 2017/9/1.
 */
public interface HiveUserService {


  List< HiveUserVo> findAllByName(MongoPage pageable, String name);


   HiveUserVo findOneById(String id);

  void deleteUserbyName(String name);

  void modifyUserbyName(String name);

  void insert(HiveUserVo vo);

  void update(HiveUserVo vo, String mainname);

  void deleteById(String id);

  Map<String, String> checkName(String name, String id);
   HiveUserVo findCharacterAllByUserId(MongoPage pageable, String id);
  public void addPrivilege(String[] names, String username) ;

}
