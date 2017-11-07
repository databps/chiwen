package com.databps.bigdaf.admin.service;

import com.databps.bigdaf.admin.domain.GatewayCharacter;
import com.databps.bigdaf.admin.domain.HbasePrivliege;
import com.databps.bigdaf.admin.domain.Policy;
import com.databps.bigdaf.admin.vo.HbasePrivliegeVo;
import com.databps.bigdaf.admin.vo.HbasePrivliegeVo;
import com.databps.bigdaf.admin.vo.PrivilegeForViewVo;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by shibingxin on 2017/9/1.
 */
public interface HbasePrivilegeService {

  public HbasePrivliege findOneGp(String id);
  Page<HbasePrivliegeVo> findAdminPage(Pageable pageable, String name, String id);


  List<HbasePrivliege> findHbasePrivliegesPage(MongoPage page, String name, String id);


  Page<HbasePrivliegeVo> findAllByName(Pageable pageable);

  PrivilegeForViewVo findAllByUserName(Pageable pageable, String userName);

  PrivilegeForViewVo findAllByGroupName(Pageable pageable, String groupName);

  void insert(HbasePrivliegeVo vo);

  void updateOrInsert(HbasePrivliegeVo vo,String mainname);

  void deleteByUserName(String username);

  void deleteByName(String name);

  void update(HbasePrivliegeVo vo);

  Policy findAllByName(Pageable pageable, String name);

  void addPrivilege(String[] names, String username);

  void modifyGroupPrivilege(String[] names, String groupName);

  HbasePrivliege findOneByName(String name);

  Map<String, String> checkName(String name, String id);



}
