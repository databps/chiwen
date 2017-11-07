package com.databps.bigdaf.admin.manager;

import com.databps.bigdaf.admin.dao.PluginsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author lvyf
 * @create 2017-08-22 11:37
 */
@Component
public class GatewayManager {
  @Autowired
  private PluginsDao pluginsDao;

  public void save(String cmpyId, String uuid,String agenttype,String hostname) {
    pluginsDao.save(cmpyId,uuid,agenttype,hostname);
  }
}