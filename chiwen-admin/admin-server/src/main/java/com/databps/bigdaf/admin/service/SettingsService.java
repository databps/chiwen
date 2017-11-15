package com.databps.bigdaf.admin.service;

import com.databps.bigdaf.admin.vo.ConfigVo;

/**
 * 设置
 *
 * @author shibingxin
 * @create 2017-08-30 下午1:39
 */
public interface SettingsService {

  ConfigVo getConfig(String cmpyId);

  ConfigVo saveConfigVo(ConfigVo configVo);

  ConfigVo saveKerberosEnable(String cmpyId , int kerberosEnable);

  ConfigVo findStrategy();

  void editStrategy(ConfigVo configVo,String part);
}