package com.databps.bigdaf.admin.dao;

import com.databps.bigdaf.admin.domain.UGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * HdpAppGroupDao
 *
 * @author lgc
 * @create 2017-08-08 上午10:37
 */

@Repository
public class HdpAppGroupDao {
  @Autowired
  private MongoTemplate mongoTemplate;
  private final static String COLLECTIO_NNAME = "hdpappgroup";
  public void addHdpAppGroup(UGroup hdpAppGroup){
    mongoTemplate.insert(hdpAppGroup, COLLECTIO_NNAME);
  }

  public void deleteGroup(String groupNn){
    Query query = new Query();
    query.addCriteria(Criteria.where("g_name").is(groupNn));
    mongoTemplate.remove(query,COLLECTIO_NNAME);
  }


//  public MongoTemplate getMongoTemplate() {
//    return mongoTemplate;
//  }
//
//  public void setMongoTemplate(MongoTemplate mongoTemplate) {
//    this.mongoTemplate = mongoTemplate;
//  }
//  public static void main(String[] args) {
//    ApplicationContext app = SpringApplication.run(BigDafApplication.class, args);
//    MongoTemplate mongoTemplate=app.getBean(MongoTemplate.class);
//    HdpAppGroupDao hdpAppGroupDao = new HdpAppGroupDao();
//    Group hdpAppGroup = new Group();
//    hdpAppGroupDao.setMongoTemplate(mongoTemplate);
//    hdpAppGroup.setGroupName("supergroup");
//    hdpAppGroup.setRemarks("this is super group");
////    hdpAppGroupDao.addHdpAppGroup(hdpAppGroup);
//    hdpAppGroupDao.deleteGroup("supergroup");
//  }
}