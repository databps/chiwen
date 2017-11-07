package com.databps.bigdaf.admin.dao;

import com.databps.bigdaf.admin.domain.*;
import com.databps.bigdaf.core.common.AuditType;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import com.databps.bigdaf.core.util.DateUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shibingxin
 * @create 2017-08-31 下午1:54
 */
@Repository
public class HbaseRoleDao {

  private final static String COLLECTIO_HBASE_ROLE = "hbase_role";
  private final static String COLLECTIO_HBASE_USER = "hbase_user";
  private final static String COLLECTIO_HBASE_PRIVILEGE = "hbase_privilege";
  private  String CMPY_ID= "5968802a01cbaa46738eee3d";

  @Autowired
  private MongoOperations mongoOperations;



  public List<HbaseRole> findAllByName(MongoPage page,String name) {
    page.setTotalResults(count(name));
    Query query = new Query();
    if(StringUtils.isNotBlank(name)){
      query.addCriteria(Criteria.where("name").is(name));
    }
    Sort sort = new Sort(Sort.Direction.DESC, "create_time");

    query.with(sort).skip(page.getFirstResult()).limit(page.getMaxResults());
    List<HbaseRole> GroupList = mongoOperations.find(query, HbaseRole.class, COLLECTIO_HBASE_ROLE);

    return GroupList;
  }

  public long count(String name) {
    Query query = new Query();
    if (StringUtils.isNotBlank(name)) {
      query.addCriteria(Criteria.where("name").is(name));
    }
    long count = mongoOperations.count(query, GatewayUser.class, COLLECTIO_HBASE_ROLE);
    return count;
  }


  public void insert(HbaseRole group) {

    Query query = new Query();
    query.addCriteria(Criteria.where("name").is(group.getName()));
    HbaseRole oldGroup = mongoOperations.findOne(query, HbaseRole.class, COLLECTIO_HBASE_ROLE);
    if (oldGroup == null) {
      //group.setUpdateTime(DateUtils.getNowDateTime());
      group.setCmpyId(CMPY_ID);
      mongoOperations.insert(group, COLLECTIO_HBASE_ROLE);
    }

  }

  public HbaseRole findOneByName(String name) {

    Query query = new Query();
    query.addCriteria(Criteria.where("name").is(name));
    return mongoOperations.findOne(query, HbaseRole.class, COLLECTIO_HBASE_ROLE);
  }

  public void update(HbaseRole group, String id) {


    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(id));

    HbaseRole oldRole = mongoOperations.findOne(query, HbaseRole.class, COLLECTIO_HBASE_ROLE);
    Update update = new Update();
    update.set("name", group.getName());
    update.set("description", group.getDescription());
    update.set("update_time", DateUtils.getNowDateTime());
    update.set("users_name",group.getUsersName());
    update.set("cmpy_id", CMPY_ID);

    if(group.getPrivileges()!=null){
      update.set("privileges",group.getPrivileges());

    }else{
      update.set("privileges",oldRole.getPrivileges());

    }
    //update.set("privileges",group.getPrivileges());
    mongoOperations.updateFirst(query, update, COLLECTIO_HBASE_ROLE);



  }

  public void deleteUserbyName(String name) {
    Query query = new Query();
    query.addCriteria(Criteria.where("name").is(name));
    mongoOperations.remove(query, COLLECTIO_HBASE_ROLE);
  }

  public void deleteById(String id) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(id));
    mongoOperations.remove(query, COLLECTIO_HBASE_ROLE);
  }

  public HbaseRole findOneById(String id) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(id));
    return mongoOperations.findOne(query, HbaseRole.class, COLLECTIO_HBASE_ROLE);
  }

  public List<HbaseRole> findGatewayRolesPage(Pageable pageable, String name, String id) {
    Query query = getQuery(pageable, name,id);

    Sort sort = new Sort(Sort.Direction.DESC, "last_modified_date");
    query.with(pageable).with(sort);
    return mongoOperations.find(query, HbaseRole.class, COLLECTIO_HBASE_ROLE);

  }
  private Query getQuery(Pageable pageable, String name,String id) {
    Query query = new Query();
    if (StringUtils.isNotBlank(name)) {
      query.addCriteria(Criteria.where("name").is(name));
    }
    if (StringUtils.isNotBlank(id)) {
      query.addCriteria(Criteria.where("_id").is(id));
    }
    if (pageable!=null){
      // query.with(pageable);
    }

    return query;
  }
}