package com.databps.bigdaf.admin.manager;


import com.databps.bigdaf.admin.config.BigDafConfig;
import com.databps.bigdaf.admin.dao.HbaseDao;
import com.databps.bigdaf.admin.dao.HbaseUserDao;
import com.databps.bigdaf.admin.dao.PolicyDao;
import com.databps.bigdaf.admin.dao.ServicesDao;
import com.databps.bigdaf.admin.domain.*;
import com.databps.bigdaf.admin.security.SecurityUtils;
import com.databps.bigdaf.admin.service.AuditService;
import com.databps.bigdaf.admin.service.GatewayPrivilegeService;
import com.databps.bigdaf.admin.service.GatewayService;
import com.databps.bigdaf.admin.service.GatewayUserService;
import com.databps.bigdaf.admin.service.HadoopService;
import com.databps.bigdaf.admin.service.HbaseService;
import com.databps.bigdaf.admin.service.HbaseUserService;
import com.databps.bigdaf.admin.service.mapper.FileAccessTypeMapper;
import com.databps.bigdaf.admin.service.mapper.PluginStatusMapper;
import com.databps.bigdaf.admin.util.AccessCount;
import com.databps.bigdaf.admin.util.AuditDateType;
import com.databps.bigdaf.admin.util.JVMUtils;
import com.databps.bigdaf.admin.vo.*;
import com.databps.bigdaf.core.common.AuditStatus;
import com.databps.bigdaf.core.common.AuditType;
import com.databps.bigdaf.core.util.HttpsUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shibingxin
 * @create 2017-08-01 上午10:55
 */
@Component
public class HomePageManager {

  @Autowired
  private AuditService auditService;
  @Autowired
  private PluginStatusMapper pluginStatusMapper;
  @Autowired
  private PolicyDao policyDao;
  @Autowired
  BigDafConfig bigDafConfig;
  @Autowired
  private ServicesDao servicesDao;
  @Autowired
  private HadoopService hadoopService;
  @Autowired
  FileAccessTypeMapper fileAccessTypeMapper;

  @Autowired
  private HbaseService hbaseService;
  @Autowired
  private GatewayUserService gatewayUserService;
  @Autowired
  private GatewayPrivilegeService gatewayPrivilegeService;
  @Autowired
  private GatewayService gatewayService;

  public HomePageVo getHomePageVo(String cmpyId) {
    HomePageVo homeVo =new HomePageVo();
    accessCount(cmpyId,homeVo);
    homeVo.setRuntime(JVMUtils.getJvmRumTime());
    setRuntime(homeVo);
    getLoopholeScore(cmpyId,homeVo);
    List<PluginStatusVo> pluginStatusVo =  pluginStatusMapper.toVO(getPluginStatus(cmpyId));
    homeVo.setPluginStatus(pluginStatusVo);
    return homeVo;
  }

  public void accessCount(String cmpyId,HomePageVo homeVo) {
    long todayAccess = auditService.count(cmpyId, AccessCount.TODAY_ACCESS_COUNT);
    homeVo.setTodayAccess((int)todayAccess);
    long ipTotalAccess = auditService.count(cmpyId,AccessCount.IP_TOTAL_COUNT);
    homeVo.setIpTotalAccess((int)ipTotalAccess);
    long todayViolation = auditService.count(cmpyId,AccessCount.TODAY_FAILURE_COUNT);
    homeVo.setTodayViolation((int)todayViolation);
    long totalViolation = auditService.count(cmpyId,AccessCount.IP_FAILURE_COUNT);
    homeVo.setTotalViolation((int)totalViolation);
  }

  private void setRuntime(HomePageVo vo) {
    String runTime = JVMUtils.getJvmRumTime();
    String []rumTimes = runTime.split(":");
    vo.setHighDay(rumTimes[0].substring(0,1));
    vo.setDay(rumTimes[0].substring(1,2));
    vo.setHighhour(rumTimes[1].substring(0,1));
    vo.setHour(rumTimes[1].substring(1,2));
  }
  private List<PluginStatus> getPluginStatus(String cmpyId) {
    List<PluginStatus> pluginStatuses = new ArrayList<>();
    pluginStatuses.add(hdfsPluginStatus(cmpyId));
    pluginStatuses.add(hbasePluginStatus(cmpyId));
    pluginStatuses.add(gatewayPluginStatus(cmpyId));
    return pluginStatuses;
  }

  private PluginStatus hdfsPluginStatus(String cmpyId) {
    PluginStatus hdfsPlugin = new PluginStatus();
    long hdfsAuditCount = auditService.count(cmpyId, AuditType.HDFS);
    hdfsPlugin.setAuditCount((int)hdfsAuditCount);
    hdfsPlugin.setType(AuditType.HDFS.getName());
    hdfsPlugin.setUserCount(countPolicy(cmpyId));
    hdfsPlugin.setAuthCount(countPrivilege(cmpyId));
    hdfsPlugin.setStatus(hadoopStatus(cmpyId));
    return hdfsPlugin;
  }

  private PluginStatus hbasePluginStatus(String cmpyId) {
    PluginStatus hdfsPlugin = new PluginStatus();
    long hbaseAuditCount = auditService.count(cmpyId, AuditType.HBASE);
    hdfsPlugin.setAuditCount((int)hbaseAuditCount);
    hdfsPlugin.setType(AuditType.HBASE.getName());
    hdfsPlugin.setUserCount(countHbaseRole(cmpyId));
    hdfsPlugin.setAuthCount(countHbasePrivilege(cmpyId));
    hdfsPlugin.setStatus(hbaseStatus(cmpyId));
    return hdfsPlugin;
  }

  private PluginStatus gatewayPluginStatus(String cmpyId) {
    PluginStatus gatewayPlugin = new PluginStatus();
    long hbaseAuditCount = auditService.count(cmpyId, AuditType.GATEWAY);
    gatewayPlugin.setAuditCount((int)hbaseAuditCount);
    gatewayPlugin.setType(AuditType.GATEWAY.getName());
    gatewayPlugin.setUserCount(countGateWayRole(cmpyId));
    gatewayPlugin.setAuthCount(countGatewayPrivilege(cmpyId));
    gatewayPlugin.setStatus(gatewayStatus(cmpyId));
    return gatewayPlugin;
  }


  private int hbaseStatus (String cmpyId) {
    return hbaseService.getHbaseServiceStatus(cmpyId);
  }

  private int gatewayStatus(String cmpyId) {
    return gatewayService.getGatewayServiceStatus(cmpyId);
  }
  private int hadoopStatus(String cmpyId) {
    int status =1;
    try {
      hadoopService.getHadoopJmx(cmpyId);
      status =0;
    }catch (IOException e) {
    }
    return status;
  }

  private long countPolicy(String cmpyId) {
    return policyDao.countPolicy(cmpyId,AuditType.HDFS.getName());
  }

  private long countPrivilege(String cmpyId) {
    return policyDao.countPrivilege(cmpyId,AuditType.HDFS.getName());
  }

  private long countHbaseRole(String cmpyId) {
    return hbaseService.countUser(cmpyId);
  }

  private long countGateWayRole(String cmpyId) {
    return gatewayUserService.countUser(cmpyId);
  }

  private long countGatewayPrivilege(String cmpyId) {
    return gatewayPrivilegeService.countPrivilege(cmpyId);
  }

  private long countHbasePrivilege(String cmpyId) {
    return hbaseService.countPrivilege(cmpyId);
  }

  public List<GroupSuccessOrFailure> getAuditStatistics(String cmpyId ,String startTime,String endTime,String status) {
    return auditService.getAuditStatistics(cmpyId,startTime,endTime,status);
  }

  public List<GroupSuccessOrFailure> getAuditStatistics(String cmpyId ,AuditDateType dateType,String status) {
    return auditService.getAuditStatistics(cmpyId,dateType,status);
  }

  public AuditStatisticsHomeVo query(String cmpyId ,HomePageQueryVo homePageQueryVo){
    AuditStatisticsHomeVo auditStatisticsHomeVo = new AuditStatisticsHomeVo();
    List<GroupSuccessOrFailure> groupSuccess=null;
    List<GroupSuccessOrFailure> groupfailure=null;
    AuditDateType auditDateType = AuditDateType.getAuditDateType(homePageQueryVo.getAuditDateType());
    if(auditDateType!=null) {
      groupSuccess = getAuditStatistics(cmpyId,auditDateType,AuditStatus.SUCCESS.getName());
      groupfailure = getAuditStatistics(cmpyId,auditDateType,AuditStatus.FAILURE.getName());
    } else{
      groupSuccess = getAuditStatistics(cmpyId,homePageQueryVo.getStartTime(),homePageQueryVo.getEndTime(),AuditStatus.SUCCESS.getName());
      groupfailure = getAuditStatistics(cmpyId,homePageQueryVo.getStartTime(),homePageQueryVo.getEndTime(),AuditStatus.FAILURE.getName());
    }
    auditStatisticsHomeVo.setGroupSuccessList(groupSuccess);
    auditStatisticsHomeVo.setGroupFailureList(groupfailure);
    List<FileAccessType> fileAccessTypes = auditService.getFileAccessType(cmpyId);
    auditStatisticsHomeVo.setFileAccessTypeVos(fileAccessTypeMapper.toVO(fileAccessTypes));
    return auditStatisticsHomeVo;
  }

  public void  getLoopholeScore(String cmpyId,HomePageVo homePageVo) {
    try{
      int score = 0;
      String hdfsUrl = null;
      String loopholeHostname = bigDafConfig.getLoopholeHostname();
      String loopholeScoreUrl = loopholeHostname + "/scan/score/token";
      Map<String, String> param = new HashMap<>();
      String hdfsHttpUrl = hadoopService.getHdfsHost(cmpyId);
      if (StringUtils.isNoneBlank(hdfsHttpUrl)) {
        String[] url = hdfsHttpUrl.split("//");
        hdfsUrl =url[1];
        param.put("ipAddr", url[1]);
      } else {
        return;
      }
      String response = HttpsUtils.post(loopholeScoreUrl, param);
      if ("fail".equals(response)) {
        return;
      }
      JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
      score = jsonObject.get("score").getAsInt();
      homePageVo.setLoopholeScore(score);
      String loopholeIndexUrl = loopholeHostname+"/scan/index"+"?ip_port="+hdfsUrl+"&type="+1+"&name="+ SecurityUtils
          .getCurrentUserLogin();
      homePageVo.setLoopholeUrl(loopholeIndexUrl);
    }catch (Exception e)
    {

    }
  }

  public AuditDailyStatisticHomeVo queryAuditDailyStatistics(String cmpId, HomePageQueryVo homePageQueryVo) {
    AuditDailyStatisticHomeVo auditDailyStatisticHomeVo=new AuditDailyStatisticHomeVo();
    List<AuditDailyStatistics> auditDailyStatisticsList=auditService.queryAuditDailyStatistics(cmpId,homePageQueryVo);
//    List<FileAccessType> fileAccessTypes = auditService.getFileAccessType(cmpId);
    List<FileAccessType> fileAccessTypes=new ArrayList<>();
    long hdfsValue=0;
    long gateWayValue=0;
    long hbaseValue=0;
    for (AuditDailyStatistics ad:auditDailyStatisticsList) {
      if(ad.getFilureCount()!=null && ad.getAccessType().equals(AuditType.HDFS.getName())){
        hdfsValue=hdfsValue+Math.round(Double.parseDouble(ad.getFilureCount()));
      }else if(ad.getFilureCount()!=null && ad.getAccessType().equals(AuditType.HBASE.getName())){
        hbaseValue=hbaseValue+Math.round(Double.parseDouble(ad.getFilureCount()));
      }else if(ad.getFilureCount()!=null && ad.getAccessType().equals(AuditType.GATEWAY.getName())){
        gateWayValue=gateWayValue+Math.round(Double.parseDouble(ad.getFilureCount()));
      }
    }
    FileAccessType hdfsFileAccessType=new FileAccessType();
    hdfsFileAccessType.setName("hdfs");
    hdfsFileAccessType.setValue(hdfsValue);
    fileAccessTypes.add(hdfsFileAccessType);

    FileAccessType hbasefileAccessType=new FileAccessType();
    hbasefileAccessType.setName("hbase");
    hbasefileAccessType.setValue(hbaseValue);
    fileAccessTypes.add(hbasefileAccessType);

    FileAccessType fileAccessType=new FileAccessType();
    fileAccessType.setName("gateway");
    fileAccessType.setValue(gateWayValue);
    fileAccessTypes.add(fileAccessType);

    auditDailyStatisticHomeVo.setAuditDailyStatisticsList(auditDailyStatisticsList);
    auditDailyStatisticHomeVo.setFileAccessTypeList(fileAccessTypes);

    return auditDailyStatisticHomeVo;
  }

  public HomePageVo getHompage(String cmpyId) {
    HomePageVo homePageVo=new HomePageVo();
    AuditHomePage auditHomePage=auditService.findAuditHomePage(cmpyId);
    if(auditHomePage!=null){
      homePageVo.setDay(auditHomePage.getDay());
      homePageVo.setHighDay(auditHomePage.getHighDay());
      homePageVo.setHighhour(auditHomePage.getHighhour());
      homePageVo.setIpTotalAccess(Integer.parseInt(auditHomePage.getIpTotalAccess()));
      homePageVo.setLoopholeScore(auditHomePage.getLoopholeScore());
      homePageVo.setLoopholeUrl(auditHomePage.getLoopholeUrl());
      homePageVo.setRuntime(auditHomePage.getRuntime());
      homePageVo.setHour(auditHomePage.getHour());
      homePageVo.setTodayAccess(Integer.parseInt(auditHomePage.getTodayAccess()));
      homePageVo.setTodayViolation(Integer.parseInt(auditHomePage.getTodayViolation()));
      homePageVo.setTotalViolation(Integer.parseInt(auditHomePage.getTotalViolation()));
      List<PluginStatusVo> pluginStatusVos=auditService.listPluginStatus(cmpyId);
      homePageVo.setPluginStatus(pluginStatusVos);
      return homePageVo;
    }else{
      homePageVo.setDay("0");
      homePageVo.setHighDay("0");
      homePageVo.setHighhour("0");
      homePageVo.setIpTotalAccess(0);
      homePageVo.setLoopholeScore(0);
      homePageVo.setLoopholeUrl(" ");
      homePageVo.setRuntime("00:00:");
      homePageVo.setHour("0");
      homePageVo.setTodayAccess(0);
      homePageVo.setTodayViolation(0);
      homePageVo.setTotalViolation(0);
      List<PluginStatusVo> pluginStatusVos=InitBankPluginStatus();
      return null;
    }

  }
  public List<PluginStatusVo> InitBankPluginStatus(){
    List<PluginStatusVo> list=new ArrayList<>();
    for (AuditType auditType : AuditType.values()) {
      PluginStatusVo pluginStatusVo=new PluginStatusVo();
      pluginStatusVo.setAuditCount(0);
      pluginStatusVo.setAuthCount(0);
      pluginStatusVo.setStatus(0);
      pluginStatusVo.setType(auditType.getName());
    }
    return list;
  }
}