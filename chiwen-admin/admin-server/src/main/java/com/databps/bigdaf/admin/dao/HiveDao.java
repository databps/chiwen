package com.databps.bigdaf.admin.dao;

import com.databps.bigdaf.admin.domain.HbasePrivliege;
import com.databps.bigdaf.admin.domain.HivePrivliege;
import com.databps.bigdaf.admin.domain.HiveRole;
import com.databps.bigdaf.admin.domain.HiveUser;
import com.databps.bigdaf.core.common.AuditType;
import com.databps.bigdaf.core.util.DateUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wanghaipeng
 * @create 2017-08-31 下午1:54
 */
@Repository
public class HiveDao {

  private final static String COLLECTIO_HIVE_ROLE = "hive_role";
  private final static String COLLECTIO_HIVE_USER = "hive_user";
  private final static String COLLECTIO_HIVE_PRIVILEGE = "hive_privilege";

  @Autowired
  private MongoOperations mongoOperations;

  public void saveHiveRole(HiveRole hiveRole) {
    hiveRole.setCreateTime(DateUtils.formatDateNow());
    mongoOperations.save(hiveRole,COLLECTIO_HIVE_ROLE);
  }

  public void saveHiveUser(HiveUser hiveUser) {
    hiveUser.setCreateTime(DateUtils.formatDateNow());
    mongoOperations.save(hiveUser,COLLECTIO_HIVE_USER);
  }

  public void saveHivePrivliege(HivePrivliege hivePrivliege) {
    hivePrivliege.setCreateTime(DateUtils.formatDateNow());
    mongoOperations.save(hivePrivliege,COLLECTIO_HIVE_PRIVILEGE);
  }

  public void upsertHiveRole(HiveRole hiveRole) {
    Query query = new Query();
    query.addCriteria(Criteria.where("cmpy_id").is(hiveRole.getCmpyId()));
    Update update = new Update();
    update.set("name",hiveRole.getName());
    update.set("description",hiveRole.getDescription());
    update.set("users_name",hiveRole.getUsersName());
    mongoOperations.upsert(query,update,COLLECTIO_HIVE_ROLE);
  }

  public void upsertHiveUser(HiveUser hiveUser) {
    Query query = new Query();
    query.addCriteria(Criteria.where("cmpy_id").is(hiveUser.getCmpyId()));
    Update update = new Update();
    update.set("name",hiveUser.getName());
    update.set("description",hiveUser.getDescription());
    mongoOperations.upsert(query,update,COLLECTIO_HIVE_USER);
  }

  public void upsertHivePrivliege(HivePrivliege hivePrivliege) {
    Query query = new Query();
    query.addCriteria(Criteria.where("cmpy_id").is(hivePrivliege.getCmpyId()));
    Update update = new Update();
    update.set("name",hivePrivliege.getName());
    update.set("description",hivePrivliege.getDescription());
    update.set("resource",hivePrivliege.getResource());
    update.set("roles_name",hivePrivliege.getRolesName());
    update.set("permissions",hivePrivliege.getPermissions());
    mongoOperations.upsert(query,update,COLLECTIO_HIVE_PRIVILEGE);
  }

  public HiveRole getHiveRole(String cmpyId,String name) {
    Query query = new Query();
    query.addCriteria(Criteria.where("cmpy_id").is(cmpyId).and("name").is(name));
    return mongoOperations.findOne(query,HiveRole.class);
  }

  public List<HiveRole> getHiveRoles(String cmpyId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("cmpy_id").is(cmpyId));
    List<HiveRole> hiveRoles = mongoOperations.find(query,HiveRole.class,COLLECTIO_HIVE_ROLE);
    return hiveRoles;
  }
  public HiveUser getHiveUser(String cmpyId,String name) {
    Query query = new Query();
    query.addCriteria(Criteria.where("cmpy_id").is(cmpyId).and("name").is(name));
    return mongoOperations.findOne(query,HiveUser.class,COLLECTIO_HIVE_USER);
  }

  public HivePrivliege getHivePrivliege(String cmpyId,String name) {
    Query query = new Query();
    query.addCriteria(Criteria.where("cmpy_id").is(cmpyId).and("name").is(name));
    return mongoOperations.findOne(query,HivePrivliege.class,COLLECTIO_HIVE_PRIVILEGE);
  }


  public JsonObject getJson(String cmpyId,String pluginUid) {
    JsonObject json = new JsonObject();
    json.addProperty("pluginUid",pluginUid);
    json.addProperty("lastVersion","123456789");
    json.addProperty("updateTime",DateUtils.formatDateNow());
    json.addProperty("type", AuditType.HBASE.getName());
//    json.addProperty("isEnabled","true");
    JsonArray rolesJsonArray = new JsonArray();
    List<HiveRole> hiveRoles = getHiveRoles(cmpyId);
    if(hiveRoles !=null) {
      for(HiveRole hiveRole : hiveRoles) {
        JsonObject jsonRole = new JsonObject();
        String roleName = hiveRole.getName();
        jsonRole.addProperty("roleName",roleName);
        List<String> usersName = hiveRole.getUsersName();
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
    List<HivePrivliege> privlieges = mongoOperations.find(query , HivePrivliege.class,COLLECTIO_HIVE_PRIVILEGE);
    JsonArray privliegeJsonArray = new Gson().toJsonTree(privlieges, new TypeToken<List<HivePrivliege>>() {}.getType()).getAsJsonArray();
    return privliegeJsonArray;
  }


  public List<HivePrivliege> getPrivilege2(String cmpyId ,String roleName) {
    Query query = new Query();
    query.addCriteria(Criteria.where("cmpy_id").is(cmpyId).and("roles_name").is(roleName));
    List<HivePrivliege> privlieges = mongoOperations.find(query , HivePrivliege.class,COLLECTIO_HIVE_PRIVILEGE);
    return privlieges;
  }



  public long countUser(String cmpyId) {
    Query query =new Query();
    query.addCriteria(Criteria.where("cmpy_id").is(cmpyId));
    return mongoOperations.count(query,COLLECTIO_HIVE_USER);
  }
  public long countPrivilege(String cmpyId) {
    Query query =new Query();
    query.addCriteria(Criteria.where("cmpy_id").is(cmpyId));
    return mongoOperations.count(query,COLLECTIO_HIVE_PRIVILEGE);
  }

}