package com.databps.bigdaf.admin.manager;

import static sun.security.jca.GetInstance.getService;

import com.databps.bigdaf.admin.dao.PluginsDao;
import com.databps.bigdaf.admin.dao.ServicesDao;
import com.databps.bigdaf.admin.domain.*;
import com.databps.bigdaf.admin.service.GatewayService;
import com.databps.bigdaf.admin.service.HadoopService;
import com.databps.bigdaf.admin.service.HbaseService;
import com.databps.bigdaf.admin.service.HbaseUserService;
import com.databps.bigdaf.admin.service.mapper.PluginsMapper;
import com.databps.bigdaf.admin.service.mapper.ServiceHdfsConfigMapper;
import com.databps.bigdaf.admin.vo.*;
import com.databps.bigdaf.admin.vo.HdfsJMXVo.Runtime;
import com.databps.bigdaf.core.common.AuditType;
import com.databps.bigdaf.core.util.DateUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shibingxin
 * @create 2017-08-11 上午11:11
 */
@Component
public class ServicesManager {

  @Autowired
  private HadoopService hadoopService;
  @Autowired
  private ServicesDao serviveDao;
  @Autowired
  private PluginsMapper pluginsMapper;
  @Autowired
  private ServiceHdfsConfigMapper serviceHdfsConfigMapper;
  @Autowired
  private PluginsDao pluginsDao;
  @Autowired
  private HbaseService hbaseService;
  @Autowired
  private GatewayService gatewayService;

  public List<ServicesVo> getServices(String cmpyId) {
    List<Services> services = serviveDao.getServices(cmpyId);
    if (services == null || services.isEmpty()) {
      services = initServcies(cmpyId);
    }
    List<ServicesVo> vos = setVo(services);
    return vos;
  }

  public ServicesVo getHbaseServcie(String cmpyId) {
    Services services = serviveDao.getService(cmpyId,AuditType.HBASE.getName());
    ServicesVo vo = hbaseServicesVo(services);
    return vo;
  }

  public ServicesVo getGatewayServcie(String cmpyId) {
    Services services = serviveDao.getService(cmpyId,AuditType.GATEWAY.getName());
    ServicesVo vo = hbaseServicesVo(services);
    return vo;
  }

  public List<Services> initServcies(String cmpyId) {
    for (AuditType auditType : AuditType.values()) {
      Services service = new Services();
      service.setCmpyId(cmpyId);
      service.setName(auditType.getName());
      service.setCreateTime(DateUtils.formatDateNow());
      service.setUpdateTime(DateUtils.formatDateNow());
      service.setPlugins(null);
      service.setServiceConfig(null);
      serviveDao.saveService(service);
    }
    return serviveDao.getServices(cmpyId);
  }

  private List<ServicesVo> setVo(List<Services> services) {
    List<ServicesVo> vos = new ArrayList<>();

    for (Services service : services) {
      String name = service.getName();
      if (AuditType.HDFS.getName().equals(name)) {
        ServicesVo vo = hdfsServicesVo(service);
        vos.add(vo);
      } else if (AuditType.HIVE.getName().equals(name)) {
        ServicesVo vo = hiveServicesVo(service);
        vos.add(vo);
      } else if (AuditType.HBASE.getName().equals(name)) {
        ServicesVo vo = hbaseServicesVo(service);
        vos.add(vo);
      } else if (AuditType.GATEWAY.getName().equals(name)) {
        ServicesVo vo = gatewayServicesVo(service);
        vos.add(vo);
      }
      service.getServiceConfig();
    }

    return vos;
  }

  private ServicesVo hdfsServicesVo(Services service) {
    ServicesVo vo = new ServicesVo();
    vo.set_id(service.get_id());
    vo.setName(service.getName());
    vo.setStatus(1);
    Map<String, String> hdfsConfig = service.getServiceConfig();
    JsonElement jsonElement = null;
    if (MapUtils.isNotEmpty(hdfsConfig)) {
      try {
        jsonElement = hadoopService.getHadoopJmx(service.getCmpyId());

      } catch (IOException e) {

      }
      HdfsJMXVo hdfsJMXVo = new Gson().fromJson(jsonElement, HdfsJMXVo.class);
      //毫秒转换成days,hours,minutes
      Runtime runtime = new Runtime();
      if(hdfsJMXVo !=null) {
        runtime.setDays(hdfsJMXVo.getUptime() / (86400000));//1000 * 60 * 60 * 24
        runtime.setHours((hdfsJMXVo.getUptime() % (86400000)) / (3600000));//1000 * 60 * 60
        runtime.setMinutes((hdfsJMXVo.getUptime() % (3600000)) / (60000));//1000 * 60 * 60
        hdfsJMXVo.setRuntime(runtime);
      }
      vo.setServicesJMXVo(hdfsJMXVo);
      vo.setServiceConfig(service.getServiceConfig());
      if (jsonElement != null) {
        vo.setStatus(0);
      }
    }

    List<Plugins> plugins = pluginsDao.getPlugins(service.getCmpyId(), service.getName());
    if (CollectionUtils.isNotEmpty(plugins)) {
      vo.setPlugins(getPluginsVos(plugins));
    }
    return vo;
  }

  private ServicesVo hiveServicesVo(Services service) {
    ServicesVo vo = new ServicesVo();
    vo.set_id(service.get_id());
    vo.setName(service.getName());
    vo.setStatus(1);
    return vo;
  }

  private ServicesVo hbaseServicesVo(Services service) {
    ServicesVo vo = new ServicesVo();
    vo.set_id(service.get_id());
    vo.setName(service.getName());
    vo.setStatus(1);
    vo.setServiceConfig(service.getServiceConfig());
    Map<String, String> hbaseConfig = service.getServiceConfig();
    if (MapUtils.isNotEmpty(hbaseConfig)) {
    int status = hbaseService.getHbaseServiceStatus(service.getCmpyId());
      vo.setStatus(status);
      List<Plugins> plugins = pluginsDao.getPlugins(service.getCmpyId(), service.getName());
      if (CollectionUtils.isNotEmpty(plugins)) {
        vo.setPlugins(getPluginsVos(plugins));
      }
    }

    return vo;
  }
  private ServicesVo gatewayServicesVo(Services service) {
    ServicesVo vo = new ServicesVo();
    vo.set_id(service.get_id());
    vo.setName(service.getName());
    vo.setStatus(1);
    vo.setServiceConfig(service.getServiceConfig());
    Map<String, String> gatewayConfig = service.getServiceConfig();
    if (MapUtils.isNotEmpty(gatewayConfig)) {
      int status = gatewayService.getGatewayServiceStatus(service.getCmpyId());
      vo.setStatus(status);
      List<Plugins> plugins = pluginsDao.getPlugins(service.getCmpyId(), service.getName());
      if (CollectionUtils.isNotEmpty(plugins)) {
        vo.setPlugins(getPluginsVos(plugins));
      }
    }

    return vo;
  }


  private List<PluginsVo> getPluginsVos(List<Plugins> plugins) {
    List<PluginsVo> vos = pluginsMapper.toVO(plugins);
    List<PluginsVo> resultVo = new ArrayList<>();
    for (PluginsVo vo : vos) {
      Date date = DateUtils.parse(vo.getUpdateTime(), DateUtils.YYYYMMDDHHMMSSSSS);
      long differ = System.currentTimeMillis() - date.getTime();
      if (differ < 100 * 1000L) {
        vo.setStatus(String.valueOf(0));
      } else {
        vo.setStatus(String.valueOf(1));
      }
      vo.setUpdateTime(DateFormatUtils.format(date.getTime(), DateUtils.YYYY_MM_DD_HH_MM_SS));
      resultVo.add(vo);
    }
    return resultVo;
  }

  public List<PluginsVo> getPluginsVos(String cmpyId, String name) {
    List<Plugins> plugins = serviveDao.getPlugins(cmpyId, name);
    List<PluginsVo> vos = pluginsMapper.toVO(plugins);
    return vos;
  }

  public Services getServiceConfig(String cmpyId, String name) {
    Services serviceConfig = serviveDao.getServiceConfig(cmpyId, name);
    return serviceConfig;
  }

  public void saveServices(String cmpyId, Services service) {
    service.setCmpyId(cmpyId);
    serviveDao.saveService(service);
  }

  public void saveServicesConfig(String cmpyId, ServiceHdfsConfigVo serviceConfig) {
    ServiceHdfsConfig config = serviceHdfsConfigMapper.toEntity(serviceConfig);
    serviveDao.saveServiceConfig(cmpyId, AuditType.HDFS.getName(), config);
  }

  public void saveHbaseConfig(String cmpyId, ServicesHbaseConfigVo serviceConfig) {
    ServicesHBaseConfig config =new ServicesHBaseConfig();
    config.setHbaseHttpUrls(serviceConfig.getHbaseHttpUrls());
    serviveDao.saveServiceConfig(cmpyId, AuditType.HBASE.getName(), config);
  }
  public void saveGatewayConfig(String cmpyId, ServicesGatewayConfigVo serviceConfig) {
    ServicesGatewayConfig config =new ServicesGatewayConfig();
    config.setGatewayHttpUrls(serviceConfig.getGatewayHttpUrls());
    serviveDao.saveServiceConfig(cmpyId, AuditType.GATEWAY.getName(), config);
  }

    public ServicesVo getHiveServcie(String cmpyId) {
      Services services = serviveDao.getService(cmpyId,AuditType.HIVE.getName());
      ServicesVo vo = hiveServicesVo(services);
      return vo;
    }


  public void saveHiveConfig(String cmpyId, ServicesHiveConfigVo serviceConfig) {
    ServicesHiveConfig config =new ServicesHiveConfig();
    config.setHiveHttpUrls(serviceConfig.getHiveHttpUrls());
    serviveDao.saveServiceConfig(cmpyId, AuditType.HIVE.getName(), config);
  }
}