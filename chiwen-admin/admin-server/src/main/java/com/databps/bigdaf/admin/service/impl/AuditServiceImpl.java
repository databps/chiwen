package com.databps.bigdaf.admin.service.impl;

import com.databps.bigdaf.admin.audit.domain.PersistentAuditEvent;
import com.databps.bigdaf.admin.dao.AuditDao;
import com.databps.bigdaf.admin.dao.AuditEventDao;
import com.databps.bigdaf.admin.dao.BackUpDao;
import com.databps.bigdaf.admin.dao.PluginStatusDao;
import com.databps.bigdaf.admin.data.domain.MongoPageRequest;
import com.databps.bigdaf.admin.domain.*;
import com.databps.bigdaf.admin.domain.BackUp.Catalog;
import com.databps.bigdaf.admin.domain.BackUp.Script;
import com.databps.bigdaf.admin.repository.CustomAuditEventRepository;
import com.databps.bigdaf.admin.service.AuditService;
import com.databps.bigdaf.admin.service.mapper.AuditDailyStatisticsMapper;
import com.databps.bigdaf.admin.service.mapper.AuditMapper;
import com.databps.bigdaf.admin.service.mapper.PersistentAuditEventMapper;
import com.databps.bigdaf.admin.service.mapper.PluginStatusMapper;
import com.databps.bigdaf.admin.util.AccessCount;
import com.databps.bigdaf.admin.util.AuditDateType;
import com.databps.bigdaf.admin.util.ShellCommandUtils;
import com.databps.bigdaf.admin.util.SystemUtils;
import com.databps.bigdaf.admin.vo.*;
import com.databps.bigdaf.core.common.AuditType;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import com.databps.bigdaf.core.util.DateUtils;
import java.io.File;
import java.io.IOException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditServiceImpl implements AuditService {

  @Autowired
  private AuditDao auditDao;
  @Autowired
  private AuditEventDao auditEventDao;
  @Autowired
  private PluginStatusDao pluginStatusDao;
  @Autowired
  private AuditMapper auditMapper;

  @Autowired
  private AuditDailyStatisticsMapper auditDailyStatisticsMapper;

  @Autowired
  private PluginStatusMapper pluginStatusMapper;
  @Autowired
  private PersistentAuditEventMapper persistentAuditEventMapper;

  @Autowired
  private CustomAuditEventRepository customAuditEventRepository;

  @Autowired
  private File calcLauncherDir;

  @Autowired
  private BackUpDao backUpDao;

  @Autowired
  private MongoProperties mongoProperties;

  public AuditServiceImpl() {
  }

  public List<GroupSuccessOrFailure> getAuditStatistics(String cmpyId, String startTime,
      String endTime,
      String status) {
    AuditDateType dateType = AuditDateType.OTHER;
    List<AuditStatisticsGroup> groups = auditDao
        .getAuditStatistics(cmpyId, dateType.getStartTime(), dateType.getEndTime(),
            dateType.getBound(), dateType.getLimit(), status,dateType);
    return toJsonElement(groups);
  }

  public List<GroupSuccessOrFailure> getAuditStatistics(String cmpyId, AuditDateType dateType,
      String status) {
    List<AuditStatisticsGroup> groups = auditDao
        .getAuditStatistics(cmpyId, dateType.getStartTime(), dateType.getEndTime(),
            dateType.getBound(), dateType.getLimit(), status,dateType);
    return toJsonElement(groups);
  }

  private List<GroupSuccessOrFailure> toJsonElement(List<AuditStatisticsGroup> groups) {
    List<GroupSuccessOrFailure> groupSuccessList = new ArrayList<>();
    for (AuditStatisticsGroup group : groups) {
      GroupSuccessOrFailure groupSuccess = new GroupSuccessOrFailure();
      if (group == null) {
        continue;
      }

      groupSuccess.setAuditType(group.getAuditType());
      List<String> counts = new ArrayList<>();
      List<String> times = new ArrayList<>();
      for (AuditStatistics statistic : group.getStatistics()) {
        counts.add(String.valueOf(statistic.getCount()));
        times.add(statistic.getDateTime());
      }

      groupSuccess.setCounts(counts);
      groupSuccess.setTimes(times);

      groupSuccessList.add(groupSuccess);
    }
    return groupSuccessList;
  }

  @Override
  public long count(String cmpyId, AccessCount accessType) {
    return auditDao.count(cmpyId, accessType);
  }

  public long count(String cmpyId, AuditType auditType) {
    return auditDao.count(cmpyId, auditType);
  }

  @Override
  public void AuditLog(AuditVo auditVo) {


    Audit audit=new Audit();
    audit.setAccessResult(auditVo.getAccessResult());
    audit.setAccessTime(auditVo.getAccessTime());
    audit.setAccessType(auditVo.getAccessType());
    audit.setAclEnforcer(auditVo.getAclEnforcer());
    audit.setAgentHostname(auditVo.getAgentHostname());
    audit.setClientIPAddress(auditVo.getClientIPAddress());
    audit.setCreateTime(DateUtils.formatDateNow());
    audit.setServiceId(auditVo.getServiceId());
    audit.setSeqNum(auditVo.getSeqNum());
    audit.setUser(auditVo.getUser());
    audit.setCmpyId(auditVo.getCmpyId());
    audit.setResourcePath(auditVo.getResourcePath());
    audit.setResourceType(auditVo.getResourceType());
    audit.setLogType(auditVo.getLogType());
    audit.setServiceType(auditVo.getServiceType());
    audit.setPluginIp(auditVo.getPluginIp());
    audit.setAction(auditVo.getAction());
    audit.setEventId(auditVo.getEventId());
    audit.setUserGroups(auditVo.getUserGroups());
    audit.setRequestData(auditVo.getRequestData());
    auditDao.insert(audit);
  }

  public List<FileAccessType> getFileAccessType(String cmpyId) {
    return auditDao.getFileAccessType(cmpyId);
  }

  @Override
  public void deleteInBatch(String[] ids) {
    auditDao.deleteInBatch(ids);
  }


  @Override
  @Transactional
  public void clean(int date) {

    String path = calcLauncherDir.getParent();
    String mongodumpPath = path + "/bin";
    if (SystemUtils.isLINUX()) {
      mongodumpPath = mongodumpPath + "/linux/mongodump";
    }
    if (SystemUtils.isMAC()) {
      mongodumpPath = mongodumpPath + "/mac/mongodump";
    }
    if (SystemUtils.isWIN()) {
      mongodumpPath = mongodumpPath + "/win/mongodump";
    }

    String nowDateTime = DateUtils.getNowDateTime();

    String oPath = path + "/data/backup/mongodb/" + nowDateTime;

    List<String> command = new ArrayList<>();
    command.add(mongodumpPath);
    command.add("-h");
    command.add(mongoProperties.getHost() + ":" + mongoProperties.getPort());
    command.add("-u");
    command.add(mongoProperties.getUsername() != null ? mongoProperties.getUsername() : "");
    command.add("-p");
    command.add(mongoProperties.getPassword() != null && mongoProperties.getPassword().length > 0
        ? mongoProperties.getPassword().toString() : "");
    command.add("-d");
    command.add(mongoProperties.getDatabase());
    command.add("-c");
    command.add("audit");
    command.add("-q");
    String startDate = DateUtils.getBeforeDate(date);
    String endDate = DateUtils.getStartDate();
    command.add("{\"access_time\":{$gt:\"" + startDate + "\",$lt:\"" + endDate
        + "\"}}");
    command.add("-o");
    command.add(oPath);

    boolean shellStatus = true;
    String err = "";
    ShellCommandUtils.Result result = null;
    try {
      if (date >= 1) {
        result = ShellCommandUtils
            .runCommand(command.toArray(new String[command.size()]));
      }

    } catch (IOException e) {
      err = e.getMessage();
      shellStatus = false;
    } catch (InterruptedException e) {
      err = e.getMessage();
      shellStatus = false;
    }
    BackUp backUp = new BackUp();
    backUp.setCreateTime(nowDateTime);
    backUp.setErr(err);

    BackUp.Script script=new Script();
    script.setBean(mongodumpPath);
    Map<String,String> param=new HashMap<String,String>();
    param.put("startDate",startDate);
    param.put("endDate",endDate);
    script.setParam(param);
    backUp.setScript(script);
    BackUp.Catalog catalog = new Catalog();

    if (shellStatus && result != null) {
      backUp.setCode(result.getExitCode());
      backUp.setOut(result.getStdout());
      backUp.setErr(result.getStderr());
      catalog.setPath(oPath);
      if (result.getExitCode() == 0) {
        catalog.setSize(getDirSize(new File(oPath)));
        //delete
        long count = auditDao.clean(startDate, endDate);
        catalog.setCount(count);
      }
      backUp.setCatalog(catalog);
      backUpDao.insert(backUp);
    } else {
      backUp.setCatalog(catalog);
      backUp.setCode(1);
      backUpDao.insert(backUp);
    }


  }

  // 递归方式 计算文件的大小
  private double getDirSize(final File file) {
    //判断文件是否存在
    if (file.exists()) {
      //如果是目录则递归计算其内容的总大小
      if (file.isDirectory()) {
        File[] children = file.listFiles();
        double size = 0;
        for (File f : children) {
          size += getDirSize(f);
        }
        return size;
      } else {//如果是文件则直接返回其大小,以“兆”为单位
        double size = (double) file.length() / 1024 / 1024;
        return size;
      }
    } else {
      return 0.0;
    }
  }

  public List<AuditVo> findAuditsPage(MongoPage page, LogQueryVo criteria) {

    List<Audit> audits = auditDao.findAuditsPage(page, criteria);

    return  auditMapper.toVO(audits);
  }

  public List<PersistentAuditEventVo> findPersistentAuditEvent(MongoPage pageable) {
    List<PersistentAuditEvent> auditEvents = auditEventDao.findPersistentAuditEvent(pageable);
    List<PersistentAuditEventVo> voList = new ArrayList<>();
    for (PersistentAuditEvent event : auditEvents) {
      PersistentAuditEventVo vo = persistentAuditEventMapper.toVO(event);
      voList.add(vo);
    }
    return voList;
  }

  public List<AuditStatistics> getDailyAuditStatistics(String cmpId){

    List<AuditStatistics> dailyAuditStatistics=auditDao.getDailyAuditStatistics(cmpId);
    return dailyAuditStatistics;
  }

  @Override
  public void insertDailyAuditStatistics(Map<String, AuditDailyStatisticsVo> map) {
    for (AuditDailyStatisticsVo vo:map.values()) {
      AuditDailyStatistics auditDailyStatistics=auditDao.getDailyAuditStatisticsByDate(DateUtils.format(new Date(),DateUtils.YYYYMMDDHHMMSSSSS),vo.getAccessType());
      if(auditDailyStatistics==null){
        auditDao.insertDailyAuditStatistics(auditDailyStatisticsMapper.toEntity(vo));
      }else{
        auditDao.updateDailyAuditStatisticsByEntity(vo);
      }
    }
  }

  @Override
  public List<AuditDailyStatistics> queryAuditDailyStatistics(String cmpId, HomePageQueryVo homePageQueryVo) {
    AuditDateType auditDateType = AuditDateType.getAuditDateType(homePageQueryVo.getAuditDateType());
    List<AuditDailyStatistics> list= auditDao.queryAuditDailyStatistics(cmpId,auditDateType);
    return list;
  }

  @Override
  public void inserDailyNull() {
    for (AuditType auditType:AuditType.values()) {
      AuditDailyStatistics auditDaily=auditDao.getDailyAuditStatisticsByDate(DateUtils.format(new Date(),DateUtils.YYYYMMDDHHMMSSSSS),auditType.getName());
      if(auditDaily==null) {
        AuditDailyStatistics auditDailyStatistics = new AuditDailyStatistics();
        auditDailyStatistics.setAccessType(auditType.getName());
        auditDailyStatistics.setStatisticDate(DateUtils.format(new Date(), DateUtils.YYYYMMDDHHMMSSSSS));
        auditDailyStatistics.setFilureCount("0");
        auditDailyStatistics.setSuccessCount("0");
        auditDao.insertDailyAuditStatistics(auditDailyStatistics);
      }
    }
  }

  @Override
  public void insertAuditHomePage(HomePageVo vo) {
    if(vo!=null){
      AuditHomePage auditHomePage=new AuditHomePage();
      auditHomePage.setCmpyId(vo.getCmpId());
      auditHomePage.setDay(vo.getDay());
      auditHomePage.setHighDay(vo.getHighDay());
      auditHomePage.setHighhour(vo.getHighhour());
      auditHomePage.setHour(vo.getHour());
      auditHomePage.setLoopholeScore(vo.getLoopholeScore());
      auditHomePage.setLoopholeUrl(vo.getLoopholeUrl());
      auditHomePage.setTodayAccess(String.valueOf(vo.getTodayAccess()));
      auditHomePage.setTodayViolation(String.valueOf(vo.getTodayViolation()));
      auditHomePage.setTotalViolation(String.valueOf(vo.getTotalViolation()));
      auditHomePage.setRuntime(vo.getRuntime());
      auditHomePage.setIpTotalAccess(String.valueOf(vo.getIpTotalAccess()));
      auditDao.insertAuditHomePage(auditHomePage);
      if(vo.getPluginStatus()!=null){
        List<PluginStatusVo> pluginStatusVos=vo.getPluginStatus();
        for (PluginStatusVo pluginStatusVo:pluginStatusVos){
          pluginStatusDao.insertOrUpdate(pluginStatusMapper.toEntity(pluginStatusVo));
        }
      }
    }
  }

  @Override
  public AuditHomePage findAuditHomePage(String cmpyId) {
    return auditDao.findAuditHomePage(cmpyId);
  }

  @Override
  public List<PluginStatusVo> listPluginStatus(String cmpyId) {
    List<PluginStatus> pluginStatuses=pluginStatusDao.listPluginStatus(cmpyId);
    List<PluginStatusVo> pluginStatusVos=new ArrayList<>();
    for (PluginStatus pluginStatus:pluginStatuses) {
      pluginStatusVos.add(pluginStatusMapper.toVO(pluginStatus));
    }
    return pluginStatusVos;
  }
}
