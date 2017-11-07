package com.databps.bigdaf.admin.dao;

import com.databps.bigdaf.admin.domain.HivePrivliege;
import com.databps.bigdaf.admin.domain.HivePrivliege;
import com.databps.bigdaf.admin.domain.Policy;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import com.databps.bigdaf.core.util.DateUtils;
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
public class HivePrivilegeDao {


  private final static String COLLECTIO_HIVE_PRIVILEGE = "hive_privilege";

  @Autowired
  private MongoOperations mongoOperations;
  private  String CMPY_ID= "5968802a01cbaa46738eee3d";



  public HivePrivliege findOneGp(Pageable pageable, String name, String id) {
    Query query = getQuery(pageable, name,id);

    return mongoOperations.findOne(query, HivePrivliege.class, COLLECTIO_HIVE_PRIVILEGE);

  }

  public List<HivePrivliege> findHivePrivliegesPage(MongoPage page, String name, String id) {

    if (page!=null) {
      page.setTotalResults(count(name));
    }
    Query query = new Query();
    if(StringUtils.isNotBlank(name)){
      query.addCriteria(Criteria.where("name").is(name));
    }
    if(StringUtils.isNotBlank(id)){
      query.addCriteria(Criteria.where("_id").is(id));
    }
    Sort sort = new Sort(Sort.Direction.DESC, "create_time");

    if (page!=null) {
      query.with(sort).skip(page.getFirstResult()).limit(page.getMaxResults());
    }

    return mongoOperations.find(query, HivePrivliege.class, COLLECTIO_HIVE_PRIVILEGE);

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
  public long count(String name) {
    Query query = new Query();
    if (StringUtils.isNotBlank(name)) {
      query.addCriteria(Criteria.where("g_name").is(name));
    }
    long count = mongoOperations.count(query, HivePrivliege.class, COLLECTIO_HIVE_PRIVILEGE);
    return count;
  }

    public void deleteByName(String name) {
    Query query = new Query();
   // query.addCriteria(Criteria.where("type").is("hdfs"));
    query.addCriteria(Criteria.where("_id").is(name));
    mongoOperations.remove(query, HivePrivliege.class, COLLECTIO_HIVE_PRIVILEGE);
  }

  public HivePrivliege findOneByName(String name) {
    Query query = new Query();
    //query.addCriteria(Criteria.where("type").is("hdfs").and("privileges").elemMatch(Criteria.where("name").is(name)));
    // query.fields().position("privileges.name",1);
    query.addCriteria(Criteria.where("_id").is(name));
    return mongoOperations.findOne(query, HivePrivliege.class, COLLECTIO_HIVE_PRIVILEGE);
  }
  public HivePrivliege findOneByName2(String name) {
    Query query = new Query();
    //query.addCriteria(Criteria.where("type").is("hdfs").and("privileges").elemMatch(Criteria.where("name").is(name)));
    // query.fields().position("privileges.name",1);
    query.addCriteria(Criteria.where("name").is(name));
    return mongoOperations.findOne(query, HivePrivliege.class, COLLECTIO_HIVE_PRIVILEGE);
  }

  public HivePrivliege findByCriteria(String where, String criteria,String id) {
    Query query = new Query();
    query.addCriteria(Criteria.where(where).is(criteria));
    query.addCriteria(Criteria.where("_id").is(id));

    HivePrivliege oldpolicy = mongoOperations.findOne(query, HivePrivliege.class, COLLECTIO_HIVE_PRIVILEGE);
    return oldpolicy;
  }



  public void insert(HivePrivliege policy) {

    policy.setCmpyId(CMPY_ID);
    mongoOperations.insert(policy, COLLECTIO_HIVE_PRIVILEGE);
  }


  /**
   * 先删除在更新
   * @param type
   * @param privilege
   * @param id
   */
  public void updateByName(String type, HivePrivliege privilege,String id) {

    Query query =new  Query();
    //query.addCriteria(Criteria.where("type").is(type));
    query.addCriteria(Criteria.where("_id").is(id));
    Update update = new Update();
    update.set("name", privilege.getName());
    update.set("description", privilege.getDescription());
    update.set("update_time", DateUtils.getNowDateTime());
    update.set("permissions",privilege.getPermissions());
    update.set("resource", privilege.getResource());
    update.set("cmpy_id", CMPY_ID);
    mongoOperations.updateFirst(query, update, COLLECTIO_HIVE_PRIVILEGE);
  }


  public Policy findOneByType(String type) {

    Query query = new Query();
    query.addCriteria(Criteria.where("type").is(type));

    return mongoOperations.findOne(query, Policy.class);

  }


 

  public HivePrivliege findOneById(String id) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(id));
    return mongoOperations.findOne(query, HivePrivliege.class, COLLECTIO_HIVE_PRIVILEGE);
  }



  public void update(HivePrivliege group, String id) {


    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(id));

    HivePrivliege oldRole = mongoOperations.findOne(query, HivePrivliege.class, COLLECTIO_HIVE_PRIVILEGE);
    Update update = new Update();
    update.set("name", group.getName());
    update.set("description", group.getDescription());
    update.set("update_time", DateUtils.getNowDateTime());
    update.set("roles_name",group.getRolesName());
    update.set("cmpy_id", CMPY_ID);

    //update.set("privileges",group.getPrivileges());
    mongoOperations.updateFirst(query, update, COLLECTIO_HIVE_PRIVILEGE);



  }
}