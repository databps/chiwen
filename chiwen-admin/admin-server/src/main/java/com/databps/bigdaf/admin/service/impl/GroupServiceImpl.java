package com.databps.bigdaf.admin.service.impl;

import com.databps.bigdaf.admin.dao.GroupDao;
import com.databps.bigdaf.admin.dao.PrivilegeDao;
import com.databps.bigdaf.admin.domain.HdpAppUser;
import com.databps.bigdaf.admin.domain.UGroup;
import com.databps.bigdaf.admin.service.GroupService;
import com.databps.bigdaf.admin.vo.GroupVo;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import com.databps.bigdaf.core.util.DateUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * GroupServiceImpl
 *
 * @author lgc
 * @create 2017-08-14 下午10:41
 */
@Service
public class GroupServiceImpl implements GroupService {

  @Autowired
  private GroupDao groupDao;

  @Autowired
  private PrivilegeDao privilegeDao;

  public List<GroupVo> findAllByName(MongoPage page, String name) {

    List<UGroup> groupList = groupDao.findAllByName(page, name);

    List<GroupVo> voList = new ArrayList<>();

    for (UGroup group : groupList) {
      GroupVo groupVo = new GroupVo();
      groupVo.setGroupName(group.getGroupName());
      groupVo.setDescription(group.getDescription());
      groupVo.setId(group.getId());
      groupVo.setCreateTime(group.getCreateTime());
      voList.add(groupVo);
    }

    return voList;
  }

  @Override
  public void deleteGroupbyName(String name) {
    groupDao.deleteUserbyName(name);
  }

  @Override
  @Transactional
  public void deleteById(String id) {
    UGroup group = groupDao.findOneById(id);
    if (group != null) {
      privilegeDao.deleteByGroupName(group.getGroupName());
      groupDao.deleteById(id);
    }

  }

  @Override
  public GroupVo findOneById(String id) {
    UGroup group = groupDao.findOneById(id);
    GroupVo vo = new GroupVo();
    vo.setId(group.getId());
    vo.setDescription(group.getDescription());
    vo.setGroupName(group.getGroupName());
    return vo;
  }

  @Override
  public void modifyGroupbyName(String name) {

  }

  @Override
  public void insert(GroupVo vo) {
    UGroup group = new UGroup();
    group.setCreateTime(DateUtils.getNowDateTime());
    group.setGroupName(vo.getGroupName());
    group.setDescription(vo.getDescription());
    group.setUpdateTime(DateUtils.getNowDateTime());
    groupDao.insert(group);
  }

  @Override
  public void update(GroupVo vo, String mainname) {
    UGroup group = new UGroup();
    group.setUpdateTime(DateUtils.getNowDateTime());
    group.setGroupName(vo.getGroupName());
    group.setDescription(vo.getDescription());
    groupDao.update(group, mainname);
  }

  @Override
  public Map<String, String> checkName(String name, String id) {
    Map<String, String> map = new HashMap<String, String>();
    if (StringUtils.isBlank(id)) {
      if (groupDao.findOneByName(name) != null) {
        map.put("error", "组名已经存在");

      } else {
        map.put("ok", "");
      }
    } else {
      UGroup user = groupDao.findOneByName(name);
      if (user != null) {
        if (user.getId().equals(id)) {
          map.put("ok", "");
        } else {
          map.put("error", "组名已经存在");
        }
      } else {
        map.put("ok", "");
      }
    }
    return map;
  }
}