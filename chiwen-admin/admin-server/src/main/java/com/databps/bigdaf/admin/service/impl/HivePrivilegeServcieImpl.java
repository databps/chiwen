package com.databps.bigdaf.admin.service.impl;

import com.databps.bigdaf.admin.dao.*;
import com.databps.bigdaf.admin.domain.HivePrivliege;
import com.databps.bigdaf.admin.domain.Policy;
import com.databps.bigdaf.admin.service.HivePrivilegeService;
import com.databps.bigdaf.admin.vo.HivePrivliegeVo;
import com.databps.bigdaf.admin.vo.PrivilegeForViewVo;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import com.databps.bigdaf.core.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shibingxin
 * @create 2017-09-01 上午10:25
 */
@Service
public class HivePrivilegeServcieImpl implements HivePrivilegeService {

  @Autowired
  private HiveUserDao hiveDao;
  @Autowired
  private ConfigDao configDao;
  @Autowired
  private PluginsDao pluginsDao;
  @Autowired
  private ServicesDao servicesDao;


  @Autowired
  private HivePrivilegeDao HivePrivliegeDao;
  @Override
  public Page<HivePrivliegeVo> findAdminPage(Pageable pageable, String name, String id) {
    return null;
  }

  @Override
  public List<HivePrivliege> findHivePrivliegesPage(MongoPage page, String name, String id) {
    return HivePrivliegeDao.findHivePrivliegesPage(page,name,id);
  }


  @Override
  public HivePrivliege findOneGp(String id) {
    return HivePrivliegeDao.findOneGp(null,null,id);
  }



  @Override
  public Page<HivePrivliegeVo> findAllByName(Pageable pageable) {
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
  public void insert(HivePrivliegeVo vo) {

    // HivePrivliege oldpolicy = HivePrivliegeDao.findByCriteria("type", "hdfs",vo.get_id();
    HivePrivliege policy = new HivePrivliege();
    String nowDate = DateUtils.getNowDateTime();

    policy.setResource(vo.getResource());
    policy.setName(vo.getName());
    policy.setDescription(vo.getDescription());
    policy.setPermissions(vo.getPermissions());
    //olicy.setCreateTime(nowDate);
    //policy.setType("hdfs");
    // HivePrivliege privilege = getPrivilege(vo, nowDate, nowDate);



    //if (oldpolicy == null) {

    HivePrivliegeDao.insert(policy);
//        } else {
//            HivePrivliege privileges = HivePrivliegeDao.findByCriteria("_id", vo.get_id();
//            if (privileges == null) {
//                HivePrivliegeDao.upset("hdfs", privilege);
//            }
//        }

  }

  @Override
  public void updateOrInsert(HivePrivliegeVo vo, String mainid) {

    if (org.apache.commons.lang3.StringUtils.isNotBlank(mainid)) {//判断是否修改,不为空是更新，为空是插入
      HivePrivliege privilege = HivePrivliegeDao.findOneByName(mainid);
      String nowDate = DateUtils.getNowDateTime();
      if (privilege != null) {
        if (privilege.getName().equals(mainid)) {//说明是在更新，但是不应该能够重复添加相同的权限名字
          HivePrivliegeDao.updateByName("hdfs", getPrivilege(vo, null, nowDate),
                  privilege.getName());
        }else {
          HivePrivliegeDao.updateByName("hdfs", getPrivilege(vo, null, nowDate),
                  mainid);
        }
      } else {
      }

    } else {
      insert(vo);
    }

  }
  private HivePrivliege getPrivilege(HivePrivliegeVo vo, String nowDate, String updateDate) {
    HivePrivliege privilege = new HivePrivliege();
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

    HivePrivliegeDao.deleteByName(name);

  }

  @Override
  public void update(HivePrivliegeVo vo) {

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
  public HivePrivliege findOneByName(String mainid) {
    HivePrivliege privilege = HivePrivliegeDao.findOneByName(mainid);

    return privilege;
  }


  @Override
  public Map<String, String> checkName(String name, String id) {
    Map<String, String> map = new HashMap<String, String>();
    if (org.apache.commons.lang3.StringUtils.isBlank(id)) {
      if (HivePrivliegeDao.findOneByName2(name) != null) {
        map.put("error", "组名已经存在");

      } else {
        map.put("ok", "");
      }
    } else {
      HivePrivliege user = HivePrivliegeDao.findOneByName2(name);
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