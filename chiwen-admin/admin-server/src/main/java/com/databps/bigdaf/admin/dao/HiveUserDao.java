package com.databps.bigdaf.admin.dao;

import com.databps.bigdaf.admin.domain.HiveUser;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import com.databps.bigdaf.core.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class HiveUserDao {

  private final static String COLLECTIO_HIVE_ROLE = "hive_role";
  private final static String COLLECTIO_HIVE_USER = "hive_user";
  private final static String COLLECTIO_HIVE_PRIVILEGE = "hive_privilege";
  private  String CMPY_ID= "5968802a01cbaa46738eee3d";

  @Autowired
  private MongoOperations mongoOperations;
  

  public List<HiveUser> findAllByName(MongoPage page, String name) {


    Query query = new Query();
    if (StringUtils.isNotBlank(name)) {
      query.addCriteria(Criteria.where("u_name").is(name));
    }
    Sort sort = new Sort(Sort.Direction.DESC, "create_time");
    if (page!=null) {
      page.setTotalResults(count(name));
      query.with(sort).skip(page.getFirstResult()).limit(page.getMaxResults());
    }
    List<HiveUser> HiveUsers = mongoOperations.find(query, HiveUser.class, COLLECTIO_HIVE_USER);

    return HiveUsers;
  }

  public HiveUser findOneById(String id) {

    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(id));
    return mongoOperations.findOne(query, HiveUser.class, COLLECTIO_HIVE_USER);
  }

  public HiveUser findOneByName(String name) {

    Query query = new Query();
    query.addCriteria(Criteria.where("u_name").is(name));
    return mongoOperations.findOne(query, HiveUser.class, COLLECTIO_HIVE_USER);
  }

  public long count(String name) {
    Query query = new Query();
    if (StringUtils.isNotBlank(name)) {
      query.addCriteria(Criteria.where("u_name").is(name));
    }

    long count = mongoOperations.count(query, HiveUser.class, COLLECTIO_HIVE_USER);
    return count;
  }

  public void insert(HiveUser user) {

    Query query = new Query();
    query.addCriteria(Criteria.where("name").is(user.getName()));
    HiveUser oldUser = mongoOperations.findOne(query, HiveUser.class, COLLECTIO_HIVE_USER);
    if (oldUser == null) {
      //user.setUpdateTime(DateUtils.getNowDateTime());
      user.setCmpyId(CMPY_ID);
      mongoOperations.insert(user, COLLECTIO_HIVE_USER);
    }


  }


  public void update(HiveUser user, String id) {

    boolean isUpate = false;
    Query queryOr = new Query();
    queryOr.addCriteria(Criteria.where("_id").is(id));
    HiveUser oldUser = mongoOperations.findOne(queryOr, HiveUser.class, COLLECTIO_HIVE_USER);
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
      mongoOperations.updateFirst(query, update, COLLECTIO_HIVE_USER);
    }


  }

  public void deleteUser(String userNm) {
    Query query = new Query();
    query.addCriteria(Criteria.where("u_name").is(userNm));
    mongoOperations.remove(query, COLLECTIO_HIVE_USER);
  }

  public void deleteById(String id) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(id));
    mongoOperations.remove(query, COLLECTIO_HIVE_USER);
  }
}