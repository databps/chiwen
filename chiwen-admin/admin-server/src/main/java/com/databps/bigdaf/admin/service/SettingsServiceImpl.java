package com.databps.bigdaf.admin.service;

import com.databps.bigdaf.admin.dao.ConfigDao;
import com.databps.bigdaf.admin.domain.Config;
import com.databps.bigdaf.admin.service.mapper.ConfigMapper;
import com.databps.bigdaf.admin.vo.ConfigVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shibingxin
 * @create 2017-08-30 下午1:39
 */
@Service
public class SettingsServiceImpl implements SettingsService {

  @Autowired
  private ConfigDao configDao;
  @Autowired
  private ConfigMapper configMapper;

  public ConfigVo getConfig(String cmpyId) {
    Config config = configDao.findConfig(cmpyId);
    return configMapper.toVO(config);
  }

  public ConfigVo saveConfigVo(ConfigVo configVo) {
    String mode = configVo.getTestMode() == null ? "true" : "false";
    Config config = configMapper.toEntity(configVo);
    config.setTestMode(mode);
    configDao.saveConfig(config);
    return getConfig(configVo.getCmpyId());
  }

  @Override
  public ConfigVo saveKerberosEnable(String cmpyId, int kerberosEnable) {

    configDao.saveKerberos(cmpyId, kerberosEnable);
    Config config = configDao.findConfig(cmpyId);
    return configMapper.toVO(config);
  }
}