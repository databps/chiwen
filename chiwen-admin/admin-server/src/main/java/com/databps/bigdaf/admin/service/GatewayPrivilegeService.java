package com.databps.bigdaf.admin.service;

import com.databps.bigdaf.admin.domain.GatewayPrivilege;
import com.databps.bigdaf.admin.domain.GatewayCharacter;
import com.databps.bigdaf.admin.domain.Policy;
import com.databps.bigdaf.admin.vo.GatewayPrivilegeVo;
import com.databps.bigdaf.admin.vo.PrivilegeForViewVo;
import com.databps.bigdaf.admin.vo.GatewayPrivilegeVo;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author lvyf
 * @create 2017-08-22 12:10
 */
public interface GatewayPrivilegeService {


    Page<GatewayPrivilegeVo> findAdminPage(Pageable pageable, String name, String id);


    List<GatewayPrivilege> findGateWayPrivilegesPage(MongoPage page, String name,String id);
    List<GatewayCharacter> findGatewayRolesPage(String id);
    GatewayPrivilege findOneGp(String id);
    GatewayCharacter findOneGr(String id);

    Page<GatewayPrivilegeVo> findAllByName(Pageable pageable);

    PrivilegeForViewVo findAllByUserName(Pageable pageable, String userName);

    PrivilegeForViewVo findAllByGroupName(Pageable pageable, String groupName);

    void insert(GatewayPrivilegeVo vo);

    void updateOrInsert(GatewayPrivilegeVo vo,String mainname);

    void deleteByUserName(String username);

    void deleteByName(String name);

    void update(GatewayPrivilegeVo vo);

    Policy findAllByName(Pageable pageable, String name);

    void addPrivilege(String[] names, String username);

    void modifyGroupPrivilege(String[] names, String groupName);

    GatewayPrivilege findOneByName(String name);

    Map<String, String> checkName(String name, String id);

    long countPrivilege(String cmpyId);
}