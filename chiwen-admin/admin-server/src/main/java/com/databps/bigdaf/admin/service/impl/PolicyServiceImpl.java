package com.databps.bigdaf.admin.service.impl;

import com.databps.bigdaf.admin.dao.ConfigDao;
import com.databps.bigdaf.admin.dao.PluginsDao;
import com.databps.bigdaf.admin.dao.PolicyDao;
import com.databps.bigdaf.admin.domain.Config;
import com.databps.bigdaf.admin.domain.Policy;
import com.databps.bigdaf.admin.domain.model.ChiWenPolicy;
import com.databps.bigdaf.admin.manager.PolicyManager;
import com.databps.bigdaf.admin.service.PolicyService;
import com.databps.bigdaf.admin.vo.ChiWenPolicyPluginVo;
import com.databps.bigdaf.admin.vo.PolicyFormVo;
import com.databps.bigdaf.admin.vo.PolicyVo;
import com.databps.bigdaf.core.util.DateUtils;
import com.databps.bigdaf.core.util.RequestIPUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * PolicyServiceImpl
 *
 * @author lgc
 * @create 2017-08-08 下午12:08
 */

@Service
public class PolicyServiceImpl implements PolicyService {

  @Autowired
  private PolicyDao policyDao;
  @Autowired
  private PluginsDao pluginsDao;

  @Autowired
  private ConfigDao configDao;


  @Deprecated
  @Override
  public PolicyVo getPolicy(String cmpyId, String chiWenUUID,String agenttype,HttpServletRequest httpServletRequest) {

    String ip = RequestIPUtil.getRemoteIp(httpServletRequest);
    pluginsDao.save(cmpyId,chiWenUUID,agenttype,ip);

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

  @Override
  public ChiWenPolicyPluginVo getPolicy2(String cmpyId, String chiWenUUID,String agenttype,HttpServletRequest httpServletRequest) {

    String ip = RequestIPUtil.getRemoteIp(httpServletRequest);
    pluginsDao.save(cmpyId,chiWenUUID,agenttype,ip);
    ChiWenPolicy policy=policyDao.getPolicy();
    ChiWenPolicyPluginVo vo=new ChiWenPolicyPluginVo();
    vo.setServiceType("hdfs");
    vo.setAudited(true);
    vo.setServiceId(chiWenUUID);
    Config config = configDao.findConfig("5968802a01cbaa46738eee3d");
    if(config!=null){
      if ("false".equalsIgnoreCase(config.getTestMode())) {
        vo.setEnabled(false);
      }else{
        vo.setEnabled(true);
      }
    }


    if(policy!=null){

      vo.setLastVersion(policy.getLastVersion());
      vo.setPrivileges(policy.getPrivileges());
      vo.setUpdateTime(policy.getUpdateTime());


    }else{
      vo.setUpdateTime(DateUtils.getNowDateTime());

    }

    return vo;
  }

  @Override
  public void insert(PolicyFormVo vo) {


  }

  @Override
  public List<PolicyVo> findAllByName(Pageable pageable, String name) {

    return null;
  }
  @Override
  public Policy findStrategy(String type) {
    Policy policy = policyDao.findStrategy(type);
    return policy;
  }
  @Override
  public void editStrategy(Policy policy) {
    policyDao.updateStrategy("hdfs",policy);
  }

}