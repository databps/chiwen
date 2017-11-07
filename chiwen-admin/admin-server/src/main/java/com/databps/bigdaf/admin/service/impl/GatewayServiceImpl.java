package com.databps.bigdaf.admin.service.impl;

import com.databps.bigdaf.admin.dao.ConfigDao;
import com.databps.bigdaf.admin.dao.PluginsDao;
import com.databps.bigdaf.admin.dao.ServicesDao;
import com.databps.bigdaf.admin.domain.Services;
import com.databps.bigdaf.admin.service.GatewayService;
import com.databps.bigdaf.core.common.AuditType;
import com.databps.bigdaf.core.util.HttpsUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shibingxin
 * @create 2017-09-18 下午4:28
 */
@Service
public class GatewayServiceImpl implements GatewayService{


  @Autowired
  private ConfigDao configDao;
  @Autowired
  private PluginsDao pluginsDao;
  @Autowired
  private ServicesDao servicesDao;

  private Queue<String> queue = new ConcurrentLinkedDeque<>();

  private void initQueue(String cmpyId,String hbaseHttpUrls) {
    queue.clear();
    if(StringUtils.isBlank(hbaseHttpUrls)) {
      Services services = servicesDao.getServiceConfig(cmpyId, AuditType.GATEWAY.getName());
      Map<String,String> servicesMap = services.getServiceConfig();
      if(servicesMap ==null)
        return ;
      hbaseHttpUrls = servicesMap.get("gateway_http_url");
      if(StringUtils.isBlank(hbaseHttpUrls))
        return ;
    }
    String[] hbaseHttpUrl = hbaseHttpUrls.split(",");
    for (String url : hbaseHttpUrl) {
      queue.add(url);
    }
  }

  public int getGatewayServiceStatus(String cmpyId) {
    initQueue(cmpyId,null);
    String element = getGatewayJMX();
    return element ==null?1:0;
  }

  public String getGatewayJMX() {
    String element =null;
    while (!queue.isEmpty()) {
      String host = queue.peek();
      element = getHttpJMX(host);
      if (element == null) {
        queue.poll();
      } else {
        return element;
      }
    }
    return element;
  }
  private String getHttpJMX(String host) {
    String jmxUrl = host ;
    JsonObject jsonOut = new JsonObject();
    String response = HttpsUtils.get(jmxUrl);
    if ("fail".equals(response)) {
      return null;
    }
    return response;
  }
}