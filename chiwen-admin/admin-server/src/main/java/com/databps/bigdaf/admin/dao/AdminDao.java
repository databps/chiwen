package com.databps.bigdaf.admin.dao;

import com.databps.bigdaf.admin.security.domain.Admin;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import com.databps.bigdaf.core.util.DateUtils;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

/**
 * @author merlin
 * @create 2017-08-31 下午2:18
 */
@Repository
public class AdminDao {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private MongoOperations mongoOperations;

  private final static String COLLECTIO_1NNAME = "admin";

  public List<Admin> findAuditsPage(MongoPage page, String name, String id) {
    Query query = getQuery(name, id);
    page.setTotalResults(count(name,id));
    Sort sort = new Sort(Direction.DESC, "last_modified_date");
    query.with(sort).skip(page.getFirstResult()).limit(page.getMaxResults());
    return mongoOperations.find(query, Admin.class, COLLECTIO_1NNAME);

  }

  public long count(String name, String id) {
    Query query = getQuery(name, id);
    long count = mongoOperations.count(query, Admin.class, COLLECTIO_1NNAME);
    return count;
  }


  private Query getQuery( String name, String id) {
    Query query = new Query();
    if (StringUtils.isNotBlank(name)) {
      query.addCriteria(Criteria.where("login").is(name));
    }
    query.addCriteria(Criteria.where("parent_id").is(id));

    return query;
  }

  public Admin findOne(String id) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(id));
    return mongoOperations.findOne(query, Admin.class, COLLECTIO_1NNAME);
  }

  public void update(Admin admin){
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(admin.getId()));
    Update update=new Update();
    update.set("login",admin.getLogin());
    update.set("email",admin.getEmail());
    update.set("last_modified_date", DateUtils.getNowDateTime());
    mongoOperations.updateFirst(query,update,COLLECTIO_1NNAME);
  }


  public void delete(String id){
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(id));
    mongoOperations.remove(query,COLLECTIO_1NNAME);
  }
}
