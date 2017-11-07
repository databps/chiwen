package com.databps.bigdaf.admin.service;

import com.databps.bigdaf.admin.vo.GroupVo;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import java.util.List;
import java.util.Map;

/**
 * Created by lgc on 17-8-14.
 */
public interface GroupService {
  List<GroupVo> findAllByName(MongoPage pageable,String name);
  void deleteGroupbyName(String name);

  void deleteById(String id);

  GroupVo findOneById(String id);

  void modifyGroupbyName(String name);
  void insert(GroupVo vo);
  void update(GroupVo vo,String mainname );

  Map<String, String> checkName(String name,String id);
}
