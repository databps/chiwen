package com.databps.bigdaf.admin.service;

import com.databps.bigdaf.admin.vo.GatewayUserVo;
import com.databps.bigdaf.admin.vo.HbasePolicyVo;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by shibingxin on 2017/9/1.
 */
public interface HbaseService {

  String getPolicy(String cmpyId, String chiWenUUID, String agenttype, HttpServletRequest httpServletRequest);

  HbasePolicyVo getPolicy2(String cmpyId, String chiWenUUID, String agenttype, HttpServletRequest httpServletRequest);

  int getHbaseServiceStatus(String cmpyId) ;

  long countUser(String cmpyId);

  long countPrivilege(String cmpyId);
}
