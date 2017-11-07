package com.databps.bigdaf.admin.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wanghaiepng on 2017/9/1.
 */
public interface HiveService {

  String getPolicy(String cmpyId, String chiWenUUID, String agenttype, HttpServletRequest httpServletRequest);

  int getHiveServiceStatus(String cmpyId) ;

  long countUser(String cmpyId);

  long countPrivilege(String cmpyId);
}
