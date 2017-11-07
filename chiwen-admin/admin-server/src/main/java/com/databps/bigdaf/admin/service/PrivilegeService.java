package com.databps.bigdaf.admin.service;

import com.databps.bigdaf.admin.domain.Policy;
import com.databps.bigdaf.admin.vo.PrivilegeForViewVo;
import com.databps.bigdaf.admin.vo.PrivilegeVo;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by lgc on 17-8-10.
 */
public interface PrivilegeService {

  Page<PrivilegeVo> findAllByName(Pageable pageable);

  PrivilegeForViewVo findAllByUserName(Pageable pageable, String userName);

  PrivilegeForViewVo findAllByGroupName(Pageable pageable, String groupName);

  void insert(PrivilegeVo vo);

  void updateOrInsert(PrivilegeVo vo,String mainname);

  void deleteByUserName(String username);

  void deleteByName(String name);

  void update(PrivilegeVo vo);

  Policy findAllByName(Pageable pageable, String name);

  void addPrivilege(String[] names, String username);

  void modifyGroupPrivilege(String[] names, String groupName);

  Policy findOneByName(String name);

  Map<String, String> checkName(String name,String id);

}
