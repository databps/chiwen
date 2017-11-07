package com.databps.bigdaf.admin.dao;

import com.databps.bigdaf.admin.domain.*;
import com.databps.bigdaf.admin.util.AccessCount;
import com.databps.bigdaf.admin.util.AuditDateType;
import com.databps.bigdaf.admin.vo.AuditDailyStatisticsVo;
import com.databps.bigdaf.admin.vo.LogQueryVo;
import com.databps.bigdaf.core.common.AuditType;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import com.databps.bigdaf.core.util.DateUtils;
import com.databps.bigdaf.core.util.FsAction;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class AuditDao {

  @Autowired
  private MongoOperations mongoOperations;
  private final static String COLLECTIO_1NNAME = "audit_event";
  private final static String COLLECTIO_AUDITHOMEPAGE="auditHomePage";

  //用户名、访问ip、操作、资源、访问类型、操作时间，授权结果 hdfs日志
  private Criteria createCriteria(Map<String, String> params) {
    String cmpyId = params.get("cmpyId");
    String ip = params.get("clientIPAddress");
    String accessType = params.get("accessType");
    String path = params.get("path");
    String clientType = params.get("clientType");
    String isAllowed = params.get("isAllowed");
    String startTime = params.get("startTime");
    String endTime = params.get("endTime");
    String action = params.get("action");
    Criteria criteria = Criteria.where("cmpy_id").is(cmpyId);
    if (StringUtils.isNotBlank(ip)) {
      criteria.and("client_ip_address").regex(".*?" + ip + ".*");
    }
    if (StringUtils.isNotBlank(path)) {
      criteria.and("path").regex(".*?" + path + ".*");
    }
    if (StringUtils.isNotBlank(clientType)) {
      criteria.and("client_type").is(clientType);
    }
    if (StringUtils.isNotBlank(startTime)) {
      criteria.and("access_time").gt(startTime).lt(endTime);
    }
    if (StringUtils.isNotBlank(accessType)) {
      criteria.and("auditor_type").is(accessType);
    }
    if (StringUtils.isNotBlank(accessType)) {
      criteria.and("access_type").is(accessType);
    }
    if (StringUtils.isNotBlank(isAllowed)) {
      criteria.and("is_allowed").is(isAllowed);
    }
    if (StringUtils.isNotBlank(action)) {
      criteria.and("action").is(action);
    }
    return criteria;
  }

  public long count(String cmpyId, AccessCount accessType) {
    long count = 0L;
    Map<String, String> parms = new HashMap<>();
    parms.put("cmpyId", cmpyId);
    switch (accessType) {
      case TODAY_ACCESS_COUNT:
        parms.put("startTime", DateUtils.formatZeroDate());
        parms.put("endTime", DateUtils.formatDateNow());
        count = count(parms);
        break;
      case IP_TOTAL_COUNT:
        count = count(parms);
        break;
      case TODAY_FAILURE_COUNT:
        parms.put("isAllowed", accessType.getAllowed());
        parms.put("startTime", DateUtils.formatZeroDate());
        parms.put("endTime", DateUtils.formatDateNow());
        count = count(parms);
        break;
      case IP_FAILURE_COUNT:
        parms.put("isAllowed", accessType.getAllowed());
        count = count(parms);
        break;
      case IP_SUCCESS_COUNT:
        parms.put("isAllowed", accessType.getAllowed());
        count = count(parms);
        break;
    }
    return count;
  }

  public long count(String cmpyId, AuditType auditType) {
    long count = 0L;
    Map<String, String> parms = new HashMap<>();
    parms.put("cmpyId", cmpyId);
    parms.put("clientType", auditType.getName());
    count = count(parms);
    switch (auditType) {
      case HDFS:

        break;
      case HBASE:
        break;
      case HIVE:
        break;
      case GATEWAY:
        break;
    }
    return count;
  }

  public long count(Map<String, String> params) {
    Criteria criteria = createCriteria(params);
    Query query = Query.query(criteria);
    long count = mongoOperations.count(query, Audit.class);
//    List list=mongoOperations.find(query,Audit.class);
//    long count=list.size();
    return count;
  }


  private List<AuditStatistics> neatenDate(List<AuditStatistics> groups, String startTime,
      String endTime, int bound) {
    List<AuditStatistics> statistics = null;
    String start = startTime.substring(0, bound);
    String end = endTime.substring(0, bound);
    Map<String, AuditStatistics> statisticsMap = new HashMap<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    try {
      Date beginDate = sdf.parse(start);
      Date endDate = sdf.parse(end);
      double between = (endDate.getTime() - beginDate.getTime()) / 1000;//除以1000是为了转换成秒
      double day = between / (24 * 3600);
      for (int i = 1; i <= day; i++) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(sdf.parse(start));
        cd.add(Calendar.DATE, i);//增加一天
        statisticsMap
            .put(sdf.format(cd.getTime()), new AuditStatistics(0, null, sdf.format(cd.getTime())));
      }
    } catch (ParseException e) {
      e.printStackTrace();
    }
    if (groups != null) {
      for (AuditStatistics group : groups) {
        String dateTime = group.getDateTime();
        statisticsMap.put(dateTime, group);
      }
    }
    statistics = new ArrayList(statisticsMap.values());
    Collections.sort(statistics);
    return statistics;
  }

  public List<FileAccessType> getFileAccessType(String cmpyId) {
    List<FileAccessType> fileAccessTypes = new ArrayList<>();
    Map<String, String> parms = new HashMap<>();
    parms.put("cmpyId", cmpyId);
    for (FsAction action : FsAction.values()) {
      long count = 0;
      parms.put("action", action.toString());
      count = count(parms);
      FileAccessType type = new FileAccessType();
      type.setValue(count);
      type.setName(action.NAME);
      fileAccessTypes.add(type);
    }
    Collections.sort(fileAccessTypes);
    List<FileAccessType> result = new ArrayList<>();
    long otherCount = 0;
    for (int i = 0; i < fileAccessTypes.size(); i++) {
      FileAccessType type = fileAccessTypes.get(i);
      if (i < 2) {
        result.add(type);
      } else {
        otherCount += type.getValue();
      }
    }
    FileAccessType accessType = new FileAccessType();
    accessType.setValue(otherCount);
    accessType.setName("其他");
    result.add(accessType);
    return result;
  }


  public List<AuditStatisticsGroup> getAuditStatistics(String cmpyId, String startTime,
                                                       String endTime, int bound, int limit, String status, AuditDateType dateType) {
    List<AuditStatisticsGroup> statisticsGroup = new ArrayList<>();
    Map<String, String> parms = new HashMap<>();
    parms.put("cmpyId", cmpyId);
    parms.put("startTime", startTime);//20171002145401362
    parms.put("endTime", endTime);//20171009145401362
    parms.put("isAllowed", status);//
    for (AuditType auditType : AuditType.values()) {
      parms.put("clientType", auditType.getName());
      List<AuditStatistics> auditStatistics = countByDate(parms, bound, limit);
      auditStatistics = neatenDate(auditStatistics, startTime, endTime, bound);
      if (auditStatistics != null) {
        AuditStatisticsGroup group = new AuditStatisticsGroup();
        group.setAuditType(auditType.getName());
        group.setStatistics(auditStatistics);
        statisticsGroup.add(group);
      }
    }
    return statisticsGroup;
  }

  public List<AuditStatistics> countByDate(Map<String, String> params, int end, int limit) {
    String groupColum = "dateTime";
    ProjectionOperation po = Aggregation.project("access_time").and("access_time").substring(0, end)
        .as(groupColum);
    Criteria criteria = createCriteria(params);
    MatchOperation mo = Aggregation.match(criteria);
    GroupOperation go = Aggregation.group(groupColum).count().as("count").first(groupColum)
        .as(groupColum);
    LimitOperation lo = Aggregation.limit(limit);
    SortOperation so = Aggregation.sort(Direction.ASC, groupColum);
    Aggregation aggregation = Aggregation.newAggregation(mo, po, go, lo, so);
    long startTime1 = System.currentTimeMillis();
    AggregationResults aggregationResults = mongoOperations
        .aggregate(aggregation, COLLECTIO_1NNAME, AuditStatistics.class);
    long endTime1 = System.currentTimeMillis();
    System.out.println("Dao程序运行时间："+(endTime1-startTime1)+"ms");
    List<AuditStatistics> list = aggregationResults.getMappedResults();
    return list;
  }

  public List<Audit> findAuditsPage(MongoPage page, LogQueryVo criteria) {

    Query query = getQuery(criteria);
    page.setTotalResults(count(query));

    Sort sort = new Sort(Direction.DESC, "access_time");

    query.with(sort).skip(page.getFirstResult()).limit(page.getMaxResults());
    return mongoOperations.find(query, Audit.class, COLLECTIO_1NNAME);
  }

  public long count(Query query) {
    long count = mongoOperations.count(query, Audit.class, COLLECTIO_1NNAME);

    return count;
  }

  private Query getQuery(LogQueryVo criteria) {
    Query query = new Query();
    String startDate = "";
    String endDate = "";
    if (StringUtils.isNotBlank(criteria.getStartDate()) && StringUtils
        .isNotBlank(criteria.getEndDate())) {
      startDate = DateUtils
          .format(DateUtils.parse(criteria.getStartDate()), DateUtils.YYYYMMDDHHMMSSSSS);
      endDate = DateUtils
          .format(DateUtils.parse(criteria.getEndDate()), DateUtils.YYYYMMDDHHMMSSSSS);
    } else if (StringUtils.isBlank(criteria.getStartDate()) && StringUtils
        .isNotBlank(criteria.getEndDate())) {
      endDate = DateUtils
          .format(DateUtils.parse(criteria.getEndDate()), DateUtils.YYYYMMDDHHMMSSSSS);
    } else if (StringUtils.isBlank(criteria.getEndDate()) && StringUtils
        .isNotBlank(criteria.getStartDate())) {
      startDate = DateUtils
          .format(DateUtils.parse(criteria.getStartDate()), DateUtils.YYYYMMDDHHMMSSSSS);
    } else if(StringUtils.isBlank(criteria.getStartDate()) && StringUtils
        .isBlank(criteria.getEndDate())){
      startDate = DateUtils.formatLastDayDate(-2, DateUtils.YYYYMMDDHHMMSSSSS);
      endDate = DateUtils.format(new Date(), DateUtils.YYYYMMDDHHMMSSSSS);
    }

    Criteria cr = Criteria.where("access_time").lt(endDate).gt(startDate);

    if (StringUtils.isNotBlank(criteria.getAccessType())) {
      cr.and("access_type").is(criteria.getAccessType());
    }

    if (StringUtils.isNotBlank(criteria.getResult())) {
      cr.and("is_allowed").is(criteria.getResult());
    }
    if (StringUtils.isNotBlank(criteria.getUsername())) {
      cr.and("user").is(criteria.getUsername());
    }
    if (StringUtils.isNotBlank(criteria.getClientType())) {
      cr.and("client_type").is(criteria.getClientType());
    }
//    if (StringUtils.isNotBlank(criteria.getStartDate()) && StringUtils
//        .isNotBlank(criteria.getEndDate())) {
//      String startDate = DateUtils
//          .format(DateUtils.parse(criteria.getStartDate()), DateUtils.YYYYMMDDHHMMSSSSS);
//      String endDate = DateUtils
//          .format(DateUtils.parse(criteria.getEndDate()), DateUtils.YYYYMMDDHHMMSSSSS);
//      query.addCriteria(Criteria.where("access_time").lt(endDate).gt(startDate));
//    } else if (StringUtils.isBlank(criteria.getStartDate()) && StringUtils
//        .isNotBlank(criteria.getEndDate())) {
//      String endDate = DateUtils
//          .format(DateUtils.parse(criteria.getEndDate()), DateUtils.YYYYMMDDHHMMSSSSS);
//      query.addCriteria(Criteria.where("access_time").lt(endDate));
//    } else if (StringUtils.isBlank(criteria.getEndDate()) && StringUtils
//        .isNotBlank(criteria.getStartDate())) {
//      String startDate = DateUtils
//          .format(DateUtils.parse(criteria.getStartDate()), DateUtils.YYYYMMDDHHMMSSSSS);
//      query.addCriteria(Criteria.where("access_time").gt(startDate));
//    }
    query.addCriteria(cr);
    return query;
  }

  public void insert(Audit audit) {
    mongoOperations.insert(audit, COLLECTIO_1NNAME);
    //mongoOperations.indexOps(Audit.class).ensureIndex(new Index().on("access_time", Direction.DESC));
  }

  public void deleteInBatch(String[] ids) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").in(ids));
    mongoOperations.remove(query, COLLECTIO_1NNAME);
  }

  public long clean(String startDate, String endDate) {
    Query query = new Query();
    query.addCriteria(Criteria.where("access_time").gt(startDate).lt(endDate));
    long count = mongoOperations.count(query, Audit.class, COLLECTIO_1NNAME);
    if (count > 0) {
      mongoOperations.remove(query, COLLECTIO_1NNAME);
    }
    return count;
  }

  public List<AuditStatistics> getDailyAuditStatistics(String companyId){
    String startDate=DateUtils.getStartDate();
    String finalDate=DateUtils.format(DateUtils.getFinallyDate(new Date()),DateUtils.YYYYMMDDHHMMSSSSS);
    Criteria ct=Criteria.where("access_time").lt(finalDate).gt(startDate);
    ProjectionOperation po = Aggregation.project("access_time").and("client_type").as("accessType").and("is_allowed").as("isAllowed");

    MatchOperation mo=new MatchOperation(ct);
    GroupOperation groupOperation=Aggregation.group("accessType","isAllowed").count().as("count");
    Aggregation aggregation=Aggregation.newAggregation(po,mo,groupOperation);

    AggregationResults aggregationResults = mongoOperations
            .aggregate(aggregation, COLLECTIO_1NNAME, AuditStatistics.class);
    List<AuditStatistics> list = aggregationResults.getMappedResults();
    return list;
  }

  public void insertDailyAuditStatistics(AuditDailyStatistics auditDailyStatistics) {
    mongoOperations.insert(auditDailyStatistics, "auditDailyStatistics");
  }

  public AuditDailyStatistics getDailyAuditStatisticsByDate(String statisticDate,String getAccessType) {
    Criteria ct=Criteria.where("statistic_date").regex(statisticDate.substring(0,8)).and("access_type").is(getAccessType);
    Query query=new Query();
    query.addCriteria(ct);
    return mongoOperations.findOne(query,AuditDailyStatistics.class);
  }

  public void updateDailyAuditStatisticsByEntity(AuditDailyStatisticsVo vo) {
    Query query=new Query();
    Criteria ct=Criteria.where("statistic_date").regex(vo.getStatisticDate().substring(0,8)).and("access_type").is(vo.getAccessType());
    query.addCriteria(ct);
    Update update=new Update();
    update.set("success_count",vo.getSuccessCount());
    update.set("filure_count",vo.getFilureCount());
    update.set("statistic_date",vo.getStatisticDate());
    mongoOperations.updateFirst(query,update,"auditDailyStatistics");
  }

  public List<AuditDailyStatistics> queryAuditDailyStatistics(String cmpId, AuditDateType auditDateType) {
    Query query=new Query();
    Calendar calendar=Calendar.getInstance();
    calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR) - 6);
    Date today = calendar.getTime();
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
    String startDate = format.format(today)+"000";
    String finalDate=DateUtils.format(DateUtils.getFinallyDate(new Date()),DateUtils.YYYYMMDDHHMMSSSSS);
    Criteria ct=Criteria.where("statistic_date").gt(startDate).lt(finalDate);
    query.addCriteria(ct);
    Sort sort = new Sort(Direction.ASC, "statistic_date");
    query.with(sort);
    return mongoOperations.find(query,AuditDailyStatistics.class);
  }

  public void insertAuditHomePage(AuditHomePage auditHomePage) {
    Query query=new Query();
    Criteria ct=Criteria.where("cmpy_id").is(auditHomePage.getCmpyId());
    query.addCriteria(ct);
    AuditHomePage auditHome=mongoOperations.findOne(query,AuditHomePage.class);
    if(auditHome==null){
      mongoOperations.insert(auditHomePage, COLLECTIO_AUDITHOMEPAGE);
    }else{
      Update update=new Update();
      update.set("day",auditHomePage.getDay());
      update.set("high_day",auditHomePage.getHighDay());
      update.set("high_hour",auditHomePage.getHighhour());
      update.set("hour",auditHomePage.getHour());
      update.set("ip_total_access",auditHomePage.getIpTotalAccess());
      update.set("loophole_score",auditHomePage.getLoopholeScore());
      update.set("loophole_url",auditHomePage.getLoopholeUrl());
      update.set("runtime",auditHomePage.getRuntime());
      update.set("today_access",auditHomePage.getTodayAccess());
      update.set("today_violation",auditHomePage.getTodayViolation());
      update.set("total_violation",auditHomePage.getTotalViolation());
      mongoOperations.updateFirst(query,update,COLLECTIO_AUDITHOMEPAGE);
    }
  }

  public AuditHomePage findAuditHomePage(String cmpyId) {
    Query query=new Query();
    Criteria ct=Criteria.where("cmpy_id").is(cmpyId);
    query.addCriteria(ct);
    return mongoOperations.findOne(query,AuditHomePage.class);
  }
}
