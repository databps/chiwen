package com.databps.bigdaf.admin.service;

import com.databps.bigdaf.admin.vo. HbaseUserVo;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by shibingxin on 2017/9/1.
 */
public interface HbaseUserService {


  List< HbaseUserVo> findAllByName(MongoPage pageable, String name);


   HbaseUserVo findOneById(String id);

  void deleteUserbyName(String name);

  void modifyUserbyName(String name);

  void insert( HbaseUserVo vo);

  void update( HbaseUserVo vo,String mainname);

  void deleteById(String id);

  Map<String, String> checkName(String name, String id);
   HbaseUserVo findCharacterAllByUserId(MongoPage pageable,String id);
  public void addPrivilege(String[] names, String username) ;

}
