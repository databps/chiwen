package com.databps.bigdaf.admin.service.impl;

import com.databps.bigdaf.admin.dao.*;
import com.databps.bigdaf.admin.domain.Config;
import com.databps.bigdaf.admin.domain.HbasePrivliege;
import com.databps.bigdaf.admin.domain.HbaseRole;
import com.databps.bigdaf.admin.domain.Services;
import com.databps.bigdaf.admin.service.HbaseService;
import com.databps.bigdaf.admin.util.HAHttpClientFactory;
import com.databps.bigdaf.admin.vo.HbasePolicyVo;
import com.databps.bigdaf.admin.vo.HbasePolicyVo.HbaseRoleResponse;
import com.databps.bigdaf.admin.vo.HbasePrivilegeVo;
import com.databps.bigdaf.core.common.AuditType;
import com.databps.bigdaf.core.util.DateUtils;
import com.databps.bigdaf.core.util.RequestIPUtil;
import com.google.gson.JsonObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * @author shibingxin
 * @create 2017-09-01 上午10:25
 */
@Service
public class HbaseServcieImpl implements HbaseService {

  @Autowired
  private HbaseDao hbaseDao;
  @Autowired
  private ConfigDao configDao;
  @Autowired
  private PluginsDao pluginsDao;
  @Autowired
  private ServicesDao servicesDao;

  @Autowired
  private HAHttpClientFactory hAHttpClientFactory;

  @Override
  @Deprecated
  public String getPolicy(String cmpyId, String chiWenUUID,String agenttype,HttpServletRequest httpServletRequest) {
    String ip = RequestIPUtil.getRemoteIp(httpServletRequest);
    pluginsDao.save(cmpyId,chiWenUUID,agenttype,ip);
    JsonObject jsonObject = hbaseDao.getJson(cmpyId,chiWenUUID);
    Config config = configDao.findConfig(cmpyId);
    if ("false".equalsIgnoreCase(config.getTestMode())) {
      jsonObject.addProperty("isEnabled","false");
    }else{
      jsonObject.addProperty("isEnabled","true");
    }
    return jsonObject.toString();
  }



  @Override
  public HbasePolicyVo getPolicy2(String cmpyId, String chiWenUUID,String agenttype,HttpServletRequest httpServletRequest) {
    String ip = RequestIPUtil.getRemoteIp(httpServletRequest);
    pluginsDao.save(cmpyId,chiWenUUID,agenttype,ip);

    HbasePolicyVo vo=new HbasePolicyVo();
    vo.setServiceId(chiWenUUID);
    vo.setLastVersion(1L);
    vo.setAudited(true);
    vo.setUpdateTime(DateUtils.formatDateNow());
    vo.setServiceType(AuditType.HBASE.getName());

    List<HbaseRole> roles=hbaseDao.getHbaseRoles(cmpyId);
    List<HbasePolicyVo.HbaseRoleResponse> rolesVo=new ArrayList<>();
    for(HbaseRole hr:roles){
      HbasePolicyVo.HbaseRoleResponse response=new HbaseRoleResponse();
      response.setUsers(hr.getUsersName());
      List<HbasePrivliege> privliegeList=hbaseDao.getPrivilege2(cmpyId,hr.getName());
      List<HbasePrivilegeVo> privilegesVo=new ArrayList<>();
      for(HbasePrivliege hp:privliegeList){
        HbasePrivilegeVo myvo=new HbasePrivilegeVo();
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
    if(config!=null) {
      if ("false".equalsIgnoreCase(config.getTestMode())) {
        vo.setEnabled(false);
      } else {
        vo.setEnabled(true);
      }
      if(config.getHbase_strategy()!=null){
        vo.setAudited(config.getHbase_strategy());
      }
    }
    return vo;
  }



  public int getHbaseServiceStatus(String cmpyId) {
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
    Map<String, String> hbaseConfig = service.getServiceConfig();
    if (MapUtils.isEmpty(hbaseConfig)) {
      return null;
    }
    String hdfsHttpUrls = hbaseConfig.get("hbase_http_url");
    return hdfsHttpUrls;
  }

  public long countUser(String cmpyId) {
    return hbaseDao.countUser(cmpyId);
  }

  public long countPrivilege(String cmpyId) {
    return hbaseDao.countPrivilege(cmpyId);
  }

}