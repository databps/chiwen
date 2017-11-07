package com.databps.bigdaf.admin.dao;

import com.databps.bigdaf.admin.domain.HbaseUser;
import com.databps.bigdaf.admin.domain.HbasePrivliege;
import com.databps.bigdaf.admin.domain.HbaseRole;
import com.databps.bigdaf.admin.domain.HbaseUser;
import com.databps.bigdaf.admin.domain.model.ChiWenPolicy;
import com.databps.bigdaf.core.common.AuditType;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import com.databps.bigdaf.core.util.DateUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

/**
 * @author shibingxin
 * @create 2017-08-31 下午1:54
 */
@Repository
public class HbaseUserDao {

  private final static String COLLECTIO_HBASE_ROLE = "hbase_role";
  private final static String COLLECTIO_HBASE_USER = "hbase_user";
  private final static String COLLECTIO_HBASE_PRIVILEGE = "hbase_privilege";
  private  String CMPY_ID= "5968802a01cbaa46738eee3d";

  @Autowired
  private MongoOperations mongoOperations;
  

  public List<HbaseUser> findAllByName(MongoPage page, String name) {


    Query query = new Query();
    if (StringUtils.isNotBlank(name)) {
      query.addCriteria(Criteria.where("u_name").is(name));
    }
    Sort sort = new Sort(Sort.Direction.DESC, "create_time");
    if (page!=null) {
      page.setTotalResults(count(name));
      query.with(sort).skip(page.getFirstResult()).limit(page.getMaxResults());
    }
    List<HbaseUser> HbaseUsers = mongoOperations.find(query, HbaseUser.class, COLLECTIO_HBASE_USER);

    return HbaseUsers;
  }

  public HbaseUser findOneById(String id) {

    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(id));
    return mongoOperations.findOne(query, HbaseUser.class, COLLECTIO_HBASE_USER);
  }

  public HbaseUser findOneByName(String name) {

    Query query = new Query();
    query.addCriteria(Criteria.where("u_name").is(name));
    return mongoOperations.findOne(query, HbaseUser.class, COLLECTIO_HBASE_USER);
  }

  public long count(String name) {
    Query query = new Query();
    if (StringUtils.isNotBlank(name)) {
      query.addCriteria(Criteria.where("u_name").is(name));
    }

    long count = mongoOperations.count(query, HbaseUser.class, COLLECTIO_HBASE_USER);
    return count;
  }

  public void insert(HbaseUser user) {

    Query query = new Query();
    query.addCriteria(Criteria.where("name").is(user.getName()));
    HbaseUser oldUser = mongoOperations.findOne(query, HbaseUser.class, COLLECTIO_HBASE_USER);
    if (oldUser == null) {
      //user.setUpdateTime(DateUtils.getNowDateTime());
      user.setCmpyId(CMPY_ID);
      mongoOperations.insert(user, COLLECTIO_HBASE_USER);
    }


  }


  public void update(HbaseUser user, String id) {

    boolean isUpate = false;
    Query queryOr = new Query();
    queryOr.addCriteria(Criteria.where("_id").is(id));
    HbaseUser oldUser = mongoOperations.findOne(queryOr, HbaseUser.class, COLLECTIO_HBASE_USER);
    if (oldUser != null) {
      if (oldUser.get_id().equals(id)) {
        isUpate = true;
      } else {
        isUpate = false;
      }
    } else {
      isUpate = true;
    }
    if (isUpate) {
      Query query = new Query();
      query.addCriteria(Criteria.where("_id").is(id));
      Update update = new Update();
      update.set("name", user.getName());
      update.set("description", user.getDescription());
      update.set("update_time", DateUtils.getNowDateTime());
      update.set("cmpy_id", CMPY_ID);

      if(user.getRoles()!=null){
        update.set("roles",user.getRoles());

      }else{
        update.set("roles",oldUser.getRoles());

      }
      mongoOperations.updateFirst(query, update, COLLECTIO_HBASE_USER);
    }


  }

  public void deleteUser(String userNm) {
    Query query = new Query();
    query.addCriteria(Criteria.where("u_name").is(userNm));
    mongoOperations.remove(query, COLLECTIO_HBASE_USER);
  }

  public void deleteById(String id) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(id));
    mongoOperations.remove(query, COLLECTIO_HBASE_USER);
  }
}