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

  @Override
  public ConfigVo findStrategy() {
    Config config = configDao.findStrategy();
    return configMapper.toVO(config);
  }

  @Override
  public void editStrategy(ConfigVo configVo,String part) {
    //Config config = configMapper.toEntity(configVo);
    Config config = configDao.findStrategy();
    ConfigVo vos = configMapper.toVO(config);
    if(part=="hdfs"){
      vos.setHdfs_strategy(configVo.getHdfs_strategy());
    }
    else if (part=="hbase"){
      vos.setHbase_strategy(configVo.getHbase_strategy());
    }
    else if (part=="gateway"){
      vos.setGateway_strategy(configVo.getGateway_strategy());
    }
    else if (part=="hive"){
      vos.setHive_strategy(configVo.getHive_strategy());
    }
    configDao.editStrategy(configMapper.toEntity(vos));
  }
}