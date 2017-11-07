package com.databps.bigdaf.admin.dao;

import com.databps.bigdaf.admin.domain.BackUp;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import com.databps.bigdaf.core.util.DateUtils;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * @author merlin
 * @create 2017-09-07 上午11:16
 */
@Repository
public class BackUpDao {

  @Autowired
  private MongoOperations mongoOperations;

  private final static String COLLECTIO_1NNAME = "backup";

  public void insert(BackUp backUp) {
    mongoOperations.insert(backUp, COLLECTIO_1NNAME);
  }

  public List<BackUp> findAuditsPage(MongoPage page,String startDate,String endDate) {

    Query query = getQuery(startDate,endDate);
    page.setTotalResults(count(startDate,endDate));

    Sort sort = new Sort(Direction.DESC, "create_time");
    query.with(sort).skip(page.getFirstResult()).limit(page.getMaxResults());
    return mongoOperations.find(query, BackUp.class, COLLECTIO_1NNAME);
  }

  public long count(String startDate,String endDate) {
    Query query = getQuery(startDate,endDate);
    long count = mongoOperations.count(query, BackUp.class, COLLECTIO_1NNAME);
    return count;
  }

  private Query getQuery(String ostartDate,String oendDate) {
    Query query = new Query();
    if (StringUtils.isNotBlank(ostartDate) && StringUtils
        .isNotBlank(oendDate)) {
      String startDate = DateUtils
          .format(DateUtils.parse(ostartDate), DateUtils.YYYYMMDDHHMMSSSSS);
      String endDate = DateUtils
          .format(DateUtils.parse(oendDate), DateUtils.YYYYMMDDHHMMSSSSS);
      query.addCriteria(Criteria.where("create_time").lt(endDate).gt(startDate));
    } else if (StringUtils.isBlank(ostartDate) && StringUtils
        .isNotBlank(oendDate)) {
      String endDate = DateUtils
          .format(DateUtils.parse(oendDate), DateUtils.YYYYMMDDHHMMSSSSS);
      query.addCriteria(Criteria.where("create_time").lt(endDate));
    } else if (StringUtils.isBlank(oendDate) && StringUtils
        .isNotBlank(ostartDate)) {
      String startDate = DateUtils
          .format(DateUtils.parse(ostartDate), DateUtils.YYYYMMDDHHMMSSSSS);
      query.addCriteria(Criteria.where("create_time").gt(startDate));
    }
    return query;
  }



}
