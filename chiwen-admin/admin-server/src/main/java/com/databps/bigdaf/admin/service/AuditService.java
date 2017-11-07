package com.databps.bigdaf.admin.service;

import com.databps.bigdaf.admin.domain.AuditDailyStatistics;
import com.databps.bigdaf.admin.domain.AuditHomePage;
import com.databps.bigdaf.admin.domain.AuditStatistics;
import com.databps.bigdaf.admin.domain.FileAccessType;
import com.databps.bigdaf.admin.util.AccessCount;
import com.databps.bigdaf.admin.util.AuditDateType;
import com.databps.bigdaf.admin.vo.*;
import com.databps.bigdaf.core.common.AuditType;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuditService {

  long count(String cmpyId ,AccessCount accessType);

  long count(String cmpyId ,AuditType auditType);

  void AuditLog(AuditVo auditVo);

  List<GroupSuccessOrFailure> getAuditStatistics(String cmpyId ,String startTime,String endTime,String status);

  List<GroupSuccessOrFailure> getAuditStatistics(String cmpyId ,AuditDateType dateType,String status);

  List<AuditVo> findAuditsPage(MongoPage page,LogQueryVo query);

  List<PersistentAuditEventVo> findPersistentAuditEvent(MongoPage pageable);

  List<FileAccessType> getFileAccessType(String cmpyId);

  void deleteInBatch(String[] ids);

  void clean(int date);

  List<AuditStatistics> getDailyAuditStatistics(String cmpId);

  void insertDailyAuditStatistics(Map<String, AuditDailyStatisticsVo> map);

  List<AuditDailyStatistics> queryAuditDailyStatistics(String cmpId, HomePageQueryVo homePageQueryVo);

  void inserDailyNull();

  void insertAuditHomePage(HomePageVo vo);

    AuditHomePage findAuditHomePage(String cmpyId);

  List<PluginStatusVo> listPluginStatus(String cmpyId);
}
