package com.databps.bigdaf.admin.service.impl;

import com.databps.bigdaf.admin.dao.ConfigDao;
import com.databps.bigdaf.admin.dao.HiveDao;
import com.databps.bigdaf.admin.dao.PluginsDao;
import com.databps.bigdaf.admin.dao.ServicesDao;
import com.databps.bigdaf.admin.domain.Config;
import com.databps.bigdaf.admin.domain.Services;
import com.databps.bigdaf.admin.service.HiveService;
import com.databps.bigdaf.admin.util.HAHttpClientFactory;
import com.databps.bigdaf.core.common.AuditType;
import com.databps.bigdaf.core.util.RequestIPUtil;
import com.google.gson.JsonObject;
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