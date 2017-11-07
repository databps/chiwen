package com.databps.bigdaf.admin.dao;

import com.databps.bigdaf.admin.domain.GatewayUser;
import com.databps.bigdaf.admin.domain.HbasePrivliege;
import com.databps.bigdaf.admin.domain.HbaseRole;
import com.databps.bigdaf.admin.domain.HbaseUser;
import com.databps.bigdaf.core.common.AuditType;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import com.databps.bigdaf.core.util.DateUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
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
public class HbaseDao {

  private final static String COLLECTIO_HBASE_ROLE = "hbase_role";
  private final static String COLLECTIO_HBASE_USER = "hbase_user";
  private final static String COLLECTIO_HBASE_PRIVILEGE = "hbase_privilege";

  @Autowired
  private MongoOperations mongoOperations;

  public void saveHbaseRole(HbaseRole hbaseRole) {
    hbaseRole.setCreateTime(DateUtils.formatDateNow());
    mongoOperations.save(hbaseRole,COLLECTIO_HBASE_ROLE);
  }

  public void saveHbaseUser(HbaseUser hbaseUser) {
    hbaseUser.setCreateTime(DateUtils.formatDateNow());
    mongoOperations.save(hbaseUser,COLLECTIO_HBASE_USER);
  }

  public void saveHbasePrivliege(HbasePrivliege hbasePrivliege) {
    hbasePrivliege.setCreateTime(DateUtils.formatDateNow());
    mongoOperations.save(hbasePrivliege,COLLECTIO_HBASE_PRIVILEGE);
  }

  public void upsertHbaseRole(HbaseRole hbaseRole) {
    Query query = new Query();
    query.addCriteria(Criteria.where("cmpy_id").is(hbaseRole.getCmpyId()));
    Update update = new Update();
    update.set("name",hbaseRole.getName());
    update.set("description",hbaseRole.getDescription());
    update.set("users_name",hbaseRole.getUsersName());
    mongoOperations.upsert(query,update,COLLECTIO_HBASE_ROLE);
  }

  public void upsertHbaseUser(HbaseUser hbaseUser) {
    Query query = new Query();
    query.addCriteria(Criteria.where("cmpy_id").is(hbaseUser.getCmpyId()));
    Update update = new Update();
    update.set("name",hbaseUser.getName());
    update.set("description",hbaseUser.getDescription());
    mongoOperations.upsert(query,update,COLLECTIO_HBASE_USER);
  }

  public void upsertHbasePrivliege(HbasePrivliege hbasePrivliege) {
    Query query = new Query();
    query.addCriteria(Criteria.where("cmpy_id").is(hbasePrivliege.getCmpyId()));
    Update update = new Update();
    update.set("name",hbasePrivliege.getName());
    update.set("description",hbasePrivliege.getDescription());
    update.set("resource",hbasePrivliege.getResource());
    update.set("roles_name",hbasePrivliege.getRolesName());
    update.set("permissions",hbasePrivliege.getPermissions());
    mongoOperations.upsert(query,update,COLLECTIO_HBASE_PRIVILEGE);
  }

  public HbaseRole getHbaseRole(String cmpyId,String name) {
    Query query = new Query();
    query.addCriteria(Criteria.where("cmpy_id").is(cmpyId).and("name").is(name));
    return mongoOperations.findOne(query,HbaseRole.class);
  }

  public List<HbaseRole> getHbaseRoles(String cmpyId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("cmpy_id").is(cmpyId));
    List<HbaseRole> hbaseRoles = mongoOperations.find(query,HbaseRole.class,COLLECTIO_HBASE_ROLE);
    return hbaseRoles;
  }
  public HbaseUser getHbaseUser(String cmpyId,String name) {
    Query query = new Query();
    query.addCriteria(Criteria.where("cmpy_id").is(cmpyId).and("name").is(name));
    return mongoOperations.findOne(query,HbaseUser.class,COLLECTIO_HBASE_USER);
  }

  public HbasePrivliege getHbasePrivliege(String cmpyId,String name) {
    Query query = new Query();
    query.addCriteria(Criteria.where("cmpy_id").is(cmpyId).and("name").is(name));
    return mongoOperations.findOne(query,HbasePrivliege.class,COLLECTIO_HBASE_PRIVILEGE);
  }


  public JsonObject getJson(String cmpyId,String pluginUid) {
    JsonObject json = new JsonObject();
    json.addProperty("pluginUid",pluginUid);
    json.addProperty("lastVersion","123456789");
    json.addProperty("updateTime",DateUtils.formatDateNow());
    json.addProperty("type", AuditType.HBASE.getName());
//    json.addProperty("isEnabled","true");
    JsonArray rolesJsonArray = new JsonArray();
    List<HbaseRole> hbaseRoles = getHbaseRoles(cmpyId);
    if(hbaseRoles !=null) {
      for(HbaseRole hbaseRole : hbaseRoles) {
        JsonObject jsonRole = new JsonObject();
        String roleName = hbaseRole.getName();
        jsonRole.addProperty("roleName",roleName);
        List<String> usersName = hbaseRole.getUsersName();
        JsonArray usersArrayJson =null;
        if(usersName !=null){
           usersArrayJson = new Gson().toJsonTree(usersName, new TypeToken<List<String>>() {}.getType()).getAsJsonArray();
        }
        jsonRole.add("users",usersArrayJson);
        JsonArray privliegeJsonArray = getPrivilege(cmpyId,roleName);
        jsonRole.add("privileges",privliegeJsonArray);
        rolesJsonArray.add(jsonRole);
      }
    }
    json.add("roles", rolesJsonArray);
    return json ;
  }

  public JsonArray getPrivilege(String cmpyId ,String roleName) {
    Query query = new Query();
    query.addCriteria(Criteria.where("cmpy_id").is(cmpyId).and("roles_name").is(roleName));
    List<HbasePrivliege> privlieges = mongoOperations.find(query , HbasePrivliege.class,COLLECTIO_HBASE_PRIVILEGE);
    JsonArray privliegeJsonArray = new Gson().toJsonTree(privlieges, new TypeToken<List<HbasePrivliege>>() {}.getType()).getAsJsonArray();
    return privliegeJsonArray;
  }

  public List<HbasePrivliege> getPrivilege2(String cmpyId ,String roleName) {
    Query query = new Query();
    query.addCriteria(Criteria.where("cmpy_id").is(cmpyId).and("roles_name").is(roleName));
    List<HbasePrivliege> privlieges = mongoOperations.find(query , HbasePrivliege.class,COLLECTIO_HBASE_PRIVILEGE);
    return privlieges;
  }

  public long countUser(String cmpyId) {
    Query query =new Query();
    query.addCriteria(Criteria.where("cmpy_id").is(cmpyId));
    return mongoOperations.count(query,COLLECTIO_HBASE_USER);
  }
  public long countPrivilege(String cmpyId) {
    Query query =new Query();
    query.addCriteria(Criteria.where("cmpy_id").is(cmpyId));
    return mongoOperations.count(query,COLLECTIO_HBASE_PRIVILEGE);
  }

}