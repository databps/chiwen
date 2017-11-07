package com.databps.bigdaf.admin.dao;

import com.databps.bigdaf.admin.domain.GatewayUser;
import com.databps.bigdaf.admin.domain.HbaseRole;
import com.databps.bigdaf.admin.domain.UGroup;
import com.databps.bigdaf.admin.domain.GatewayCharacter;

import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import com.databps.bigdaf.core.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * GroupDao
 *
 * @author lgc
 * @create 2017-08-14 下午10:42
 */
@Repository
public class GatewayCharactorDao {

  @Autowired
  private MongoOperations mongoOperations;
  private  String CMPY_ID= "5968802a01cbaa46738eee3d";

  private final static String COLLECTIO_NNAME = "gateway_role";

  public List<GatewayCharacter> findAllByName(MongoPage page,String name) {
    page.setTotalResults(count(name));
    Query query = new Query();
    if(StringUtils.isNotBlank(name)){
      query.addCriteria(Criteria.where("name").is(name));
    }
    Sort sort = new Sort(Direction.DESC, "create_time");

    query.with(sort).skip(page.getFirstResult()).limit(page.getMaxResults());
    List<GatewayCharacter> GroupList = mongoOperations.find(query, GatewayCharacter.class, COLLECTIO_NNAME);

    return GroupList;
  }

  public long count(String name) {
    Query query = new Query();
    if (StringUtils.isNotBlank(name)) {
      query.addCriteria(Criteria.where("name").is(name));
    }
    long count = mongoOperations.count(query, GatewayUser.class, COLLECTIO_NNAME);
    return count;
  }


  public void insert(GatewayCharacter group) {

    Query query = new Query();
    query.addCriteria(Criteria.where("name").is(group.getName()));
    GatewayCharacter oldGroup = mongoOperations.findOne(query, GatewayCharacter.class, COLLECTIO_NNAME);
    if (oldGroup == null) {
      //group.setUpdateTime(DateUtils.getNowDateTime());
      group.setCmpyId(CMPY_ID);
      mongoOperations.insert(group, COLLECTIO_NNAME);
    }

  }

  public GatewayCharacter findOneByName(String name) {

    Query query = new Query();
    query.addCriteria(Criteria.where("name").is(name));
    return mongoOperations.findOne(query, GatewayCharacter.class, COLLECTIO_NNAME);
  }

  public void update(GatewayCharacter group, String id) {


      Query query = new Query();
      query.addCriteria(Criteria.where("_id").is(id));

      GatewayCharacter oldRole = mongoOperations.findOne(query, GatewayCharacter.class, COLLECTIO_NNAME);
      Update update = new Update();
      update.set("name", group.getName());
      update.set("description", group.getDescription());
      update.set("update_time", DateUtils.getNowDateTime());
      update.set("cmpy_id", CMPY_ID);

      if(group.getPrivileges()!=null){
        update.set("privileges",group.getPrivileges());

      }else{
        update.set("privileges",oldRole.getPrivileges());

      }
      mongoOperations.updateFirst(query, update, COLLECTIO_NNAME);



  }

  public void deleteUserbyName(String name) {
    Query query = new Query();
    query.addCriteria(Criteria.where("name").is(name));
    mongoOperations.remove(query, COLLECTIO_NNAME);
  }

  public void deleteById(String id) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(id));
    mongoOperations.remove(query, COLLECTIO_NNAME);
  }

  public GatewayCharacter findOneById(String id) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(id));
    return mongoOperations.findOne(query, GatewayCharacter.class, COLLECTIO_NNAME);
  }

}