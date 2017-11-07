


package com.databps.bigdaf.admin.service.impl;

import com.databps.bigdaf.admin.dao.GatewayPrivilegeDao;
import com.databps.bigdaf.admin.domain.GatewayPrivilege;
import com.databps.bigdaf.admin.domain.GatewayCharacter;
import com.databps.bigdaf.admin.domain.Policy;
import com.databps.bigdaf.admin.domain.Privilege;
import com.databps.bigdaf.admin.service.GatewayPrivilegeService;
import com.databps.bigdaf.admin.vo.GatewayPrivilegeVo;
import com.databps.bigdaf.admin.vo.PrivilegeForViewVo;
import com.databps.bigdaf.admin.vo.PrivilegeVo;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import com.databps.bigdaf.core.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GatewayPrivilegeServiceImpl implements GatewayPrivilegeService {

    @Autowired
    private GatewayPrivilegeDao gatewayPrivilegeDao;
    @Override
    public Page<GatewayPrivilegeVo> findAdminPage(Pageable pageable, String name, String id) {
        return null;
    }

    @Override
    public List<GatewayPrivilege> findGateWayPrivilegesPage(MongoPage page,String name,String id) {
        return gatewayPrivilegeDao.findGateWayPrivilegesPage(page,name,id);
    }

    @Override
    public List<GatewayCharacter> findGatewayRolesPage(String id) {
        return gatewayPrivilegeDao.findGatewayRolesPage(null,null,id);

    }

    @Override
    public GatewayPrivilege findOneGp(String id) {
        return gatewayPrivilegeDao.findOneGp(null,null,id);
    }

    @Override
    public GatewayCharacter findOneGr(String id) {

        return gatewayPrivilegeDao.findOneGr(null,null,id);
    }

    @Override
    public Page<GatewayPrivilegeVo> findAllByName(Pageable pageable) {
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
    public void insert(GatewayPrivilegeVo vo) {

       // GatewayPrivilege oldpolicy = gatewayPrivilegeDao.findByCriteria("type", "hdfs",vo.get_id();
        GatewayPrivilege policy = new GatewayPrivilege();
        String nowDate = DateUtils.getNowDateTime();
        GatewayPrivilege.Source re = new GatewayPrivilege.Source();
        re.setPath(vo.getPath());
        List<String>  list = vo.getOps();
//        for (String access : list) {
//            accessesList.add(accesse);
//            privilegeItems.setAccesses(accessesList);
//            privilegeItems.setUsers(new HashSet<String>());
//            privilegeItems.setGroups(new HashSet<String>());
//            privilegeItemsList.add(privilegeItems);
//
//        }
        re.setOps(list);
        policy.setName(vo.getName());
        policy.setDescription(vo.getDescription());
        policy.setSources(re);
       //olicy.setCreateTime(nowDate);
        policy.setType("hdfs");
       // GatewayPrivilege privilege = getPrivilege(vo, nowDate, nowDate);



        //if (oldpolicy == null) {

        gatewayPrivilegeDao.insert(policy);
//        } else {
//            GatewayPrivilege privileges = gatewayPrivilegeDao.findByCriteria("_id", vo.get_id();
//            if (privileges == null) {
//                gatewayPrivilegeDao.upset("hdfs", privilege);
//            }
//        }

    }

    @Override
    public void updateOrInsert(GatewayPrivilegeVo vo, String mainid) {

        if (StringUtils.isNotBlank(mainid)) {//判断是否修改,不为空是更新，为空是插入
            GatewayPrivilege privilege = gatewayPrivilegeDao.findOneByName(mainid);
            String nowDate = DateUtils.getNowDateTime();
            if (privilege != null) {
                if (privilege.getName().equals(mainid)) {//说明是在更新，但是不应该能够重复添加相同的权限名字
                    gatewayPrivilegeDao.updateByName("hdfs", getPrivilege(vo, null, nowDate),
                            privilege.getName());
                }else {
                    gatewayPrivilegeDao.updateByName("hdfs", getPrivilege(vo, null, nowDate),
                            mainid);
                }
            } else {
            }

        } else {
            insert(vo);
        }

    }
    private GatewayPrivilege getPrivilege(GatewayPrivilegeVo vo, String nowDate, String updateDate) {
        GatewayPrivilege privilege = new GatewayPrivilege();
        privilege.setName(vo.getName());
        privilege.setDescription(vo.getDescription());
        //privilege.setCreateTime(nowDate);
        //privilege.setUpdateTime(nowDate);
        GatewayPrivilege.Source re = new GatewayPrivilege.Source();

        re.setPath(vo.getPath());
        List<String>  list = vo.getOps();
        re.setOps(list);
        privilege.setSources(re);
    return privilege;
    }
    @Override
    public void deleteByUserName(String username) {

    }

    @Override
    public void deleteByName(String name) {

        gatewayPrivilegeDao.deleteByName(name);

    }

    @Override
    public void update(GatewayPrivilegeVo vo) {

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
    public GatewayPrivilege findOneByName(String mainid) {
        GatewayPrivilege privilege = gatewayPrivilegeDao.findOneByName(mainid);

        return privilege;
    }


    @Override
    public Map<String, String> checkName(String name, String id) {
        Map<String, String> map = new HashMap<String, String>();
        if (StringUtils.isBlank(id)) {
            if (gatewayPrivilegeDao.findOneByName2(name) != null) {
                map.put("error", "权限名已经存在");

            } else {
                map.put("ok", "");
            }
        } else {
            GatewayPrivilege user = gatewayPrivilegeDao.findOneByName2(name);
            if (user != null) {
                if (user.get_id().equals(id)) {
                    map.put("ok", "");
                } else {
                    map.put("error", "权限名已经存在");
                }
            } else {
                map.put("ok", "");
            }
        }
        return map;
    }

    public long countPrivilege(String cmpyId) {
        return gatewayPrivilegeDao.countPrivilege(cmpyId);
    }

}
