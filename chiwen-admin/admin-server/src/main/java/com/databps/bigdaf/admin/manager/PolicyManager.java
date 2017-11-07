package com.databps.bigdaf.admin.manager;

import com.databps.bigdaf.admin.dao.ConfigDao;
import com.databps.bigdaf.admin.dao.PolicyDao;
import com.databps.bigdaf.admin.domain.Config;
import com.databps.bigdaf.admin.domain.model.ChiWenPolicy;
import com.databps.bigdaf.admin.vo.PolicyVo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * PolicyManager
 *
 * @author lgc
 * @create 2017-08-08 下午1:50
 */

@Component
public class PolicyManager {

  @Autowired
  private PolicyDao policyDao;
  @Autowired
  private ConfigDao configDao;


  public ChiWenPolicy getPolicy2() {
    ChiWenPolicy chiWenPolicy = policyDao.getPolicy();


    return chiWenPolicy;
  }


  public PolicyVo getPolicy() {
    Gson gson = new GsonBuilder().setDateFormat("yyyyMMddHHmmssSSS").setPrettyPrinting().create();
    PolicyVo pVo = new PolicyVo();
    ChiWenPolicy chiWenPolicy = policyDao.getPolicy();
    Config config = configDao.findConfig("5968802a01cbaa46738eee3d");
    if ("false".equalsIgnoreCase(config.getTestMode())) {
      chiWenPolicy.setEnabled(false);
    }else{
      chiWenPolicy.setEnabled(true);
    }
    pVo.setPolicyjsonStr(gson.toJson(chiWenPolicy));
    return pVo;
  }
}