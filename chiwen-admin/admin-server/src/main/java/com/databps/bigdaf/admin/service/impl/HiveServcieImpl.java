package com.databps.bigdaf.admin.service.impl;

import com.databps.bigdaf.admin.dao.ConfigDao;
import com.databps.bigdaf.admin.dao.HiveDao;
import com.databps.bigdaf.admin.dao.PluginsDao;
import com.databps.bigdaf.admin.dao.ServicesDao;
import com.databps.bigdaf.admin.domain.Config;
import com.databps.bigdaf.admin.domain.HbasePrivliege;
import com.databps.bigdaf.admin.domain.HbaseRole;
import com.databps.bigdaf.admin.domain.HivePrivliege;
import com.databps.bigdaf.admin.domain.HiveRole;
import com.databps.bigdaf.admin.domain.Services;
import com.databps.bigdaf.admin.service.HiveService;
import com.databps.bigdaf.admin.util.HAHttpClientFactory;
import com.databps.bigdaf.admin.vo.HbasePolicyVo;
import com.databps.bigdaf.admin.vo.HbasePolicyVo.HbaseRoleResponse;
import com.databps.bigdaf.admin.vo.HbasePrivilegeVo;
import com.databps.bigdaf.admin.vo.HivePolicyVo;
import com.databps.bigdaf.admin.vo.HivePolicyVo.HiveRoleResponse;
import com.databps.bigdaf.admin.vo.HivePrivilegeVo;
import com.databps.bigdaf.core.common.AuditType;
import com.databps.bigdaf.core.util.DateUtils;
import com.databps.bigdaf.core.util.RequestIPUtil;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @author shibingxin
 * @create 2017-09-01 上午10:25
 */
@Service
public class HiveServcieImpl implements HiveService {

  @Autowired
  private HiveDao hiveDao;
  @Autowired
  private ConfigDao configDao;
  @Autowired
  private PluginsDao pluginsDao;
  @Autowired
  private ServicesDao servicesDao;

  @Autowired
  private HAHttpClientFactory hAHttpClientFactory;

  @Override
  public String getPolicy(String cmpyId, String chiWenUUID,String agenttype,HttpServletRequest httpServletRequest) {
    String ip = RequestIPUtil.getRemoteIp(httpServletRequest);
    pluginsDao.save(cmpyId,chiWenUUID,agenttype,ip);
    JsonObject jsonObject = hiveDao.getJson(cmpyId,chiWenUUID);
    Config config = configDao.findConfig(cmpyId);
    if ("false".equalsIgnoreCase(config.getTestMode())) {
      jsonObject.addProperty("isEnabled","false");
    }else{
      jsonObject.addProperty("isEnabled","true");
    }
    return jsonObject.toString();
  }

  @Override
  public HivePolicyVo getPolicy2(String cmpyId, String chiWenUUID, String agenttype,
      HttpServletRequest httpServletRequest) {

    String ip = RequestIPUtil.getRemoteIp(httpServletRequest);
    pluginsDao.save(cmpyId,chiWenUUID,agenttype,ip);

    HivePolicyVo vo=new HivePolicyVo();
    vo.setServiceId(chiWenUUID);
    vo.setLastVersion(1L);
    vo.setAudited(true);
    vo.setUpdateTime(DateUtils.formatDateNow());
    vo.setServiceType(AuditType.HIVE.getName());

    List<HiveRole> roles=hiveDao.getHiveRoles(cmpyId);
    List<HivePolicyVo.HiveRoleResponse> rolesVo=new ArrayList<>();
    for(HiveRole hr:roles){
      HivePolicyVo.HiveRoleResponse response=new HiveRoleResponse();
      response.setUsers(hr.getUsersName());
      List<HivePrivliege> privliegeList=hiveDao.getPrivilege2(cmpyId,hr.getName());
      List<HivePrivilegeVo> privilegesVo=new ArrayList<>();
      for(HivePrivliege hp:privliegeList){
        HivePrivilegeVo myvo=new HivePrivilegeVo();
        myvo.setName(hp.getName());
        myvo.setPermissions(hp.getPermissions());
        myvo.setResource(hp.getResource());
        myvo.setRolesName(hp.getRolesName());
        privilegesVo.add(myvo);
      }

      response.setPrivileges(privilegesVo);
      rolesVo.add(response);
    }

    vo.setRoles(rolesVo);

    Config config = configDao.findConfig(cmpyId);
    if ("false".equalsIgnoreCase(config.getTestMode())) {
      vo.setEnabled(false);
    }else{
      vo.setEnabled(true);
    }

    return vo;
  }

  public int getHiveServiceStatus(String cmpyId) {
    String httpUrls = getHttpUrls(cmpyId);
    try {
      String httpUrl = hAHttpClientFactory.doGetHaUrl(httpUrls,"/jmx");
      if(StringUtils.isNotBlank(httpUrl))
        return 0;
    } catch (IOException e) {
      e.printStackTrace();
      return 1;
    }
    return 1;
  }

  private String getHttpUrls(String cmpyId) {
    Services service = servicesDao.getServiceConfig(cmpyId, AuditType.HBASE.getName());
    Map<String, String> hiveConfig = service.getServiceConfig();
    if (MapUtils.isEmpty(hiveConfig)) {
      return null;
    }
    String hdfsHttpUrls = hiveConfig.get("hive_http_url");
    return hdfsHttpUrls;
  }

  public long countUser(String cmpyId) {
    return hiveDao.countUser(cmpyId);
  }

  public long countPrivilege(String cmpyId) {
    return hiveDao.countPrivilege(cmpyId);
  }

}