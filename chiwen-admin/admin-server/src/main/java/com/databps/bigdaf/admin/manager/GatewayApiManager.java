package com.databps.bigdaf.admin.manager;

import com.databps.bigdaf.admin.domain.GatewayCharacter;
import com.databps.bigdaf.admin.domain.GatewayPrivilege;
import com.databps.bigdaf.admin.service.GatewayPrivilegeService;
import com.databps.bigdaf.admin.service.GatewayUserService;
import com.databps.bigdaf.admin.vo.GatewayPrivilegeVo;
import com.databps.bigdaf.admin.vo.GatewayUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
@Component
public class GatewayApiManager {

    @Autowired
    private GatewayPrivilegeService gatewayService;


    @Autowired
    private GatewayUserService hdpAppUserService;


    public Object getUserPrivilege() {

        GatewayPrivilegeVo gatewayGatewayPrivilegeVo = new GatewayPrivilegeVo();
        List<Map<String, Object>> user = new ArrayList<Map<String, Object>>();
        gatewayGatewayPrivilegeVo.setUser(user);
        // Page<GatewayUserVo> page = hdpAppUserService.findAllByName(null,null);
        List<GatewayUserVo> page = hdpAppUserService.findAllByName(null, null,null);
        Iterator<GatewayUserVo> GatewayUserVo = page.iterator();
        while (GatewayUserVo.hasNext()) {
            Map map1 = new HashMap<>();
            List<Map> roleList = new ArrayList<Map>();
            GatewayUserVo GatewayUserVo2 = GatewayUserVo.next();
            List<Map<String, String>> roles = GatewayUserVo2.getRoles();
            if (roles == null)
                roles = new ArrayList<Map<String, String>>();
            Iterator<Map<String, String>> gatewayRole = roles.iterator();


            while (gatewayRole.hasNext()) {
                List<Map> privilegeList = new ArrayList<Map>();
                Map<String, String> gatewayRole2 = gatewayRole.next();
                List<GatewayCharacter> gatewayRoles = gatewayService.findGatewayRolesPage(gatewayRole2.get("role_id").toString());

                if (gatewayRoles.size() > 0) {

                    Map<String, Object> mapR1 = new HashMap<String, Object>();

                    List<Map<String, String>> privileges = gatewayRoles.get(0).getPrivileges();
                    if (privileges != null) {

                        Iterator<Map<String, String>> gatewayprivileges222 = privileges.iterator();


                        //
                        while (gatewayprivileges222.hasNext()) {
                            Map<String, Object> mapP1 = new HashMap<String, Object>();
                            Map<String, Object> mapP2 = new HashMap<String, Object>();
                            Map<String, String> gp = gatewayprivileges222.next();
                            List<GatewayPrivilege> gatewayPrivileges = gatewayService.findGateWayPrivilegesPage(null, null, gp.get("privilege_id").toString());

                            if (gatewayPrivileges.size() > 0) {
                                mapP2.put(gatewayPrivileges.get(0).getSources().getPath(), gatewayPrivileges.get(0).getSources().getOps());
                                mapP1.put(gatewayPrivileges.get(0).getName(), mapP2);
                                privilegeList.add(mapP1);
                            }
                        }

                        if (privilegeList.size() > 0) {
                            mapR1.put(gatewayRoles.get(0).getName(), privilegeList);
                            roleList.add(mapR1);
                        }
                    }

                }
            }
            if (roleList.size() > 0) {
                map1.put(GatewayUserVo2.getUsername() + ":" + GatewayUserVo2.getUPassword(), roleList);
                gatewayGatewayPrivilegeVo.getUser().add(map1);
            }


        }
        return  gatewayGatewayPrivilegeVo.getUser();

    }

}

