package com.databps.bigdaf.admin.dao;

import com.databps.bigdaf.admin.domain.Plugins;
import com.databps.bigdaf.admin.domain.Services;
import com.databps.bigdaf.core.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author shibingxin
 * @create 2017-08-07 下午1:31
 */
@Repository
public class ServicesDao {

  @Autowired
  private MongoTemplate mongoTemplate;

  private static String collectionName = "service";

  public void saveService(Services service) {

    mongoTemplate.save(service,collectionName);
  }

  public List<Services> getServices(String cmpyId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("cmpy_id").is(cmpyId));
    return mongoTemplate.find(query, Services.class, collectionName);
  }

  public Services getService(String cmpyId ,String name) {
    Query query = new Query();
    query.addCriteria(Criteria.where("cmpy_id").is(cmpyId));
    query.addCriteria(Criteria.where("name").is(name));
    return mongoTemplate.findOne(query, Services.class, collectionName);
  }
  public void saveServiceConfig(String cmpyId,String name,Object serviceConfig) {
    Query query =new Query();
    query.addCriteria(Criteria.where("cmpy_id").is(cmpyId).and("name").is(name));
    Update update = new Update();
    update.set("update_time",DateUtils.formatDateNow());
    update.set("service_config",serviceConfig);
    mongoTemplate.upsert(query,update,collectionName);
  }

  public Map<String,String> getServiceConfigMap(String cmpyId ,String name ) {
    Map<String,String> serviceConfigMap =null;
    Services servcie = getServiceConfig(cmpyId,name);

    if (servcie != null) {
      serviceConfigMap = servcie.getServiceConfig();
    }
    return serviceConfigMap;
  }

  public Services getServiceConfig(String cmpyId ,String name) {
    Query query = new Query();
    query.addCriteria(Criteria.where("cmpy_id").is(cmpyId).and("name").is(name));
    return  mongoTemplate.findOne(query, Services.class, collectionName);
  }

  public List<Plugins> getPlugins(String cmpyId ,String name) {
    Query query = new Query();
    query.addCriteria(Criteria.where("cmpy_id").is(cmpyId).and("name").is(name));
    return mongoTemplate.find(query,Plugins.class);
  }

}