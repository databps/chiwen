package com.databps.bigdaf.admin.service.impl;

import com.databps.bigdaf.admin.dao.*;
import com.databps.bigdaf.admin.domain.*;
import com.databps.bigdaf.admin.service.HbasePrivilegeService;
import com.databps.bigdaf.admin.service.HbaseUserService;
import com.databps.bigdaf.admin.vo.HbasePrivliegeVo;
import com.databps.bigdaf.admin.vo.PrivilegeForViewVo;
import com.databps.bigdaf.core.common.AuditType;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import com.databps.bigdaf.core.util.DateUtils;
import com.databps.bigdaf.core.util.HttpsUtils;
import com.databps.bigdaf.core.util.RequestIPUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author shibingxin
 * @create 2017-09-01 上午10:25
 */
@Service
public class HbasePrivilegeServcieImpl implements HbasePrivilegeService {

  @Autowired
  private HbaseUserDao hbaseDao;
  @Autowired
  private ConfigDao configDao;
  @Autowired
  private PluginsDao pluginsDao;
  @Autowired
  private ServicesDao servicesDao;


  @Autowired
  private HbasePrivilegeDao HbasePrivliegeDao;
  @Override
  public Page<HbasePrivliegeVo> findAdminPage(Pageable pageable, String name, String id) {
    return null;
  }

  @Override
  public List<HbasePrivliege> findHbasePrivliegesPage(MongoPage page, String name, String id) {
    return HbasePrivliegeDao.findHbasePrivliegesPage(page,name,id);
  }


  @Override
  public HbasePrivliege findOneGp(String id) {
    return HbasePrivliegeDao.findOneGp(null,null,id);
  }



  @Override
  public Page<HbasePrivliegeVo> findAllByName(Pageable pageable) {
    return null;
  }

  @Override
  public PrivilegeForViewVo findAllByUserName(Pageable pageable, String userName) {
    return null;
  }

  @Override
  public PrivilegeForViewVo findAllByGroupName(Pageable pageable, String groupName) {
    return null;
  }

  @Override
  public void insert(HbasePrivliegeVo vo) {

    // HbasePrivliege oldpolicy = HbasePrivliegeDao.findByCriteria("type", "hdfs",vo.get_id();
    HbasePrivliege policy = new HbasePrivliege();
    String nowDate = DateUtils.getNowDateTime();

    policy.setResource(vo.getResource());
    policy.setName(vo.getName());
    policy.setDescription(vo.getDescription());
    policy.setPermissions(vo.getPermissions());
    //olicy.setCreateTime(nowDate);
    //policy.setType("hdfs");
    // HbasePrivliege privilege = getPrivilege(vo, nowDate, nowDate);



    //if (oldpolicy == null) {

    HbasePrivliegeDao.insert(policy);
//        } else {
//            HbasePrivliege privileges = HbasePrivliegeDao.findByCriteria("_id", vo.get_id();
//            if (privileges == null) {
//                HbasePrivliegeDao.upset("hdfs", privilege);
//            }
//        }

  }

  @Override
  public void updateOrInsert(HbasePrivliegeVo vo, String mainid) {

    if (org.apache.commons.lang3.StringUtils.isNotBlank(mainid)) {//判断是否修改,不为空是更新，为空是插入
      HbasePrivliege privilege = HbasePrivliegeDao.findOneByName(mainid);
      String nowDate = DateUtils.getNowDateTime();
      if (privilege != null) {
        if (privilege.getName().equals(mainid)) {//说明是在更新，但是不应该能够重复添加相同的权限名字
          HbasePrivliegeDao.updateByName("hdfs", getPrivilege(vo, null, nowDate),
                  privilege.getName());
        }else {
          HbasePrivliegeDao.updateByName("hdfs", getPrivilege(vo, null, nowDate),
                  mainid);
        }
      } else {
      }

    } else {
      insert(vo);
    }

  }
  private HbasePrivliege getPrivilege(HbasePrivliegeVo vo, String nowDate, String updateDate) {
    HbasePrivliege privilege = new HbasePrivliege();
    privilege.setName(vo.getName());
    privilege.setDescription(vo.getDescription());
    privilege.setResource(vo.getResource());

    //privilege.setCreateTime(nowDate);
    //privilege.setUpdateTime(nowDate);
    List<String>  list = vo.getPermissions();
    privilege.setPermissions(list);
    return privilege;
  }
  @Override
  public void deleteByUserName(String username) {

  }

  @Override
  public void deleteByName(String name) {

    HbasePrivliegeDao.deleteByName(name);

  }

  @Override
  public void update(HbasePrivliegeVo vo) {

  }

  @Override
  public Policy findAllByName(Pageable pageable, String name) {
    return null;
  }

  @Override
  public void addPrivilege(String[] names, String username) {

  }

  @Override
  public void modifyGroupPrivilege(String[] names, String groupName) {

  }

  @Override
  public HbasePrivliege findOneByName(String mainid) {
    HbasePrivliege privilege = HbasePrivliegeDao.findOneByName(mainid);

    return privilege;
  }


  @Override
  public Map<String, String> checkName(String name, String id) {
    Map<String, String> map = new HashMap<String, String>();
    if (org.apache.commons.lang3.StringUtils.isBlank(id)) {
      if (HbasePrivliegeDao.findOneByName2(name) != null) {
        map.put("error", "组名已经存在");

      } else {
        map.put("ok", "");
      }
    } else {
      HbasePrivliege user = HbasePrivliegeDao.findOneByName2(name);
      if (user != null) {
        if (user.get_id().equals(id)) {
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