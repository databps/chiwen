package com.databps.bigdaf.admin.util;

import java.util.ArrayList;
import org.junit.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;

/**
 * Created by yyh on 17-7-18.
 */
public class MongodbCommandTest {

  @Test
  public void parseAggregation() throws Exception {
    Criteria criteria = Criteria.where("cmpy_id").is("78978979878979");
    String groupColum = "date_time";
    int limit = 10;
    int end = 10;
    ProjectionOperation po = Aggregation.project("create_time").and("create_time")
        .substring(0, end).as(groupColum);
    MatchOperation mo = Aggregation.match(criteria);
    GroupOperation go = Aggregation.group(groupColum).count().as("count").first(groupColum)
        .as(groupColum);
    SortOperation finalSo = Aggregation.sort(Sort.Direction.DESC, "_id");
    LimitOperation lo = Aggregation.limit(limit);
    Aggregation aggregation = Aggregation.newAggregation(mo, po, go, finalSo, lo);
  }

  @Test
  public void countByOp() {
    String groupColum = "op";
    Criteria criteria = Criteria.where("cmpy_id").is("78978979878979");
    MatchOperation mo = Aggregation.match(criteria);
    GroupOperation go = Aggregation.group(groupColum).count().as("count").first(groupColum)
        .as(groupColum);
    Aggregation aggregation = Aggregation.newAggregation(mo, go);
  }

  @Test
  public void updateTest() {
    Update update = new Update();
    update.set("name", "1212");
    update.set("config", "config999");
    update.addToSet("plugins", new ArrayList<String>());
    update.setOnInsert("oninsert", "firstaa");
    System.out.println(update);
  }
}