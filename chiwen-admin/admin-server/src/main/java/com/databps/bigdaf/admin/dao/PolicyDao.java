package com.databps.bigdaf.admin.dao;


import com.databps.bigdaf.admin.BigDafApplication;
import com.databps.bigdaf.admin.domain.Policy;
import com.databps.bigdaf.admin.domain.Privilege;
import com.databps.bigdaf.admin.domain.model.ChiWenPolicy;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

/**
 * PolicyDao
 *
 * @author lgc
 * @create 2017-08-08 上午11:39
 */

@Repository
public class PolicyDao {
  @Autowired
  private MongoTemplate mongoTemplate;
  private final static String COLLECTIO_1NNAME = "policy";
  public ChiWenPolicy getPolicy(){
    Query query = new Query();
    query.addCriteria(Criteria.where("type").is("hdfs"));
    ChiWenPolicy policy = mongoTemplate.findOne(query,ChiWenPolicy.class, COLLECTIO_1NNAME);
    return policy;
  }

  public long countPolicy(String cmpyId,String type) {
    Query query =new Query();
    query.addCriteria(Criteria.where("type").is(type));
    return mongoTemplate.count(query,COLLECTIO_1NNAME);
  }
  public long countPrivilege(String cmpyId,String type) {
    Query query = new Query();
    query.addCriteria(Criteria.where("type").is(type));
    List<ChiWenPolicy> policys = mongoTemplate.find(query, ChiWenPolicy.class, COLLECTIO_1NNAME);
    long count =0;
    for (ChiWenPolicy policy : policys) {
      if (CollectionUtils.isNotEmpty(policy.getPrivileges())) {
        count += policy.getPrivileges().size();
      }
    }
    return count;
  }

  public void insert(Policy policy,Privilege p){

  }
  public void updatePrivileges(String type,Policy policy){
    Query query = Query.query(Criteria.where("type").is(type));
    Update update = new Update();
    update.set("privileges", policy.getPrivileges());
    mongoTemplate.updateFirst(query,update,COLLECTIO_1NNAME);
  }
//  public MongoT
// emplate getMongoTemplate() {
//    return mongoTemplate;
//  }

//  public void setMongoTemplate(MongoTemplate mongoTemplate) {
//    this.mongoTemplate = mongoTemplate;
//  }

//  public static void main(String[] args) {
//        ApplicationContext app = SpringApplication.run(BigDafApplication.class, args);
//    MongoTemplate mongoTemplate=app.getBean(MongoTemplate.class);
//    Query query = new Query();
//    query.addCriteria(Criteria.where("a").is("aaa").and("b.b1").is("b11"));
//    System.out.println(mongoTemplate.find(query,String.class,"testtable"));
//  }

}