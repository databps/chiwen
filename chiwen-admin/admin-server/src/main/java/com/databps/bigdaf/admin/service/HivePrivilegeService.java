package com.databps.bigdaf.admin.service;

import com.databps.bigdaf.admin.domain.HivePrivliege;
import com.databps.bigdaf.admin.domain.Policy;
import com.databps.bigdaf.admin.vo.HivePrivliegeVo;
import com.databps.bigdaf.admin.vo.PrivilegeForViewVo;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by shibingxin on 2017/9/1.
 */
public interface HivePrivilegeService {

  public HivePrivliege findOneGp(String id);
  Page<HivePrivliegeVo> findAdminPage(Pageable pageable, String name, String id);


  List<HivePrivliege> findHivePrivliegesPage(MongoPage page, String name, String id);


  Page<HivePrivliegeVo> findAllByName(Pageable pageable);

  PrivilegeForViewVo findAllByUserName(Pageable pageable, String userName);

  PrivilegeForViewVo findAllByGroupName(Pageable pageable, String groupName);

  void insert(HivePrivliegeVo vo);

  void updateOrInsert(HivePrivliegeVo vo, String mainname);

  void deleteByUserName(String username);

  void deleteByName(String name);

  void update(HivePrivliegeVo vo);

  Policy findAllByName(Pageable pageable, String name);

  void addPrivilege(String[] names, String username);

  void modifyGroupPrivilege(String[] names, String groupName);

  HivePrivliege findOneByName(String name);

  Map<String, String> checkName(String name, String id);



}
