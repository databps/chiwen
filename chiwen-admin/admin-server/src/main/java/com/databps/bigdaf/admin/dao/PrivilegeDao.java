package com.databps.bigdaf.admin.dao;

import com.databps.bigdaf.admin.domain.Policy;
import com.databps.bigdaf.admin.domain.Privilege;
import com.databps.bigdaf.admin.domain.Privilege.PrivilegeItems;
import com.databps.bigdaf.admin.domain.model.ChiWenPolicy;
import com.databps.bigdaf.admin.domain.model.ChiWenPrivilege;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicUpdate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

/**
 * PrivilegeDao
 *
 * @author lgc
 * @create 2017-08-05 下午12:10
 */

@Repository
public class PrivilegeDao {

  @Autowired
  private MongoOperations mongoOperations;

  private final static String COLLECTIO_NNAME = "policy";

  public List<Privilege> findAllByName(Pageable pageable) {

    Query query = new Query();
    Sort sort = new Sort(Direction.DESC, "create_time");
    query.addCriteria(Criteria.where("type").is("hdfs"));
    query.with(pageable).with(sort);

    List<Privilege> hdpAppUsers = mongoOperations.find(query, Privilege.class, COLLECTIO_NNAME);

    return hdpAppUsers;
  }

  public void deleteByGroupName(String groupname){


    Query query = new Query();
    query.addCriteria(Criteria.where("type").is("hdfs"));
    Policy oldpolicy = mongoOperations.findOne(query, Policy.class, COLLECTIO_NNAME);
    List<Privilege> privilegeList=oldpolicy.getPrivileges();
    for(Privilege privilege:privilegeList){
      List<PrivilegeItems> privilegeItemsList=privilege.getPrivilege_items();
      for(PrivilegeItems privilegeItems:privilegeItemsList){
        Set<String> set= privilegeItems.getGroups();
        for(Iterator it = set.iterator();it.hasNext();){
          String obj = (String) it.next();
          if(obj.equals(groupname)){
            set.remove(obj);//试图删除迭代出来的元素
          }

        }
      }
    }

    Query queryUpdate = new Query();
    queryUpdate.addCriteria(Criteria.where("type").is("hdfs"));
    Update update = new Update();
    update.set("privileges", oldpolicy.getPrivileges());
    mongoOperations.updateFirst(queryUpdate, update, Policy.class, COLLECTIO_NNAME);
  }


  public void deleteByUserName(String username){


    Query query = new Query();
    query.addCriteria(Criteria.where("type").is("hdfs"));
    Policy oldpolicy = mongoOperations.findOne(query, Policy.class, COLLECTIO_NNAME);
    List<Privilege> privilegeList=oldpolicy.getPrivileges();
    for(Privilege privilege:privilegeList){
      List<PrivilegeItems> privilegeItemsList=privilege.getPrivilege_items();
      for(PrivilegeItems privilegeItems:privilegeItemsList){
        Set<String> set= privilegeItems.getUsers();
        for(Iterator it = set.iterator();it.hasNext();){
          String obj = (String) it.next();
          if(obj.equals(username)){
            set.remove(obj);//试图删除迭代出来的元素
          }

        }
      }
    }

    Query queryUpdate = new Query();
    queryUpdate.addCriteria(Criteria.where("type").is("hdfs"));
    Update update = new Update();
    update.set("privileges", oldpolicy.getPrivileges());
    mongoOperations.updateFirst(queryUpdate, update, Policy.class, COLLECTIO_NNAME);
  }
  public void deleteByName(String name) {
    Query query = new Query();
    query.addCriteria(Criteria.where("type").is("hdfs"));
    Update update = new Update();
    Document document = new Document("name", name);
    update.pull("privileges", document);
    mongoOperations.updateFirst(query, update, COLLECTIO_NNAME);
  }

  public Policy findOneByName(String name) {
    Query query = new Query();
    //query.addCriteria(Criteria.where("type").is("hdfs").and("privileges").elemMatch(Criteria.where("name").is(name)));
    query.fields().position("privileges.name",1);
    query.addCriteria(Criteria.where("privileges.name").is(name));
    return mongoOperations.findOne(query, Policy.class, COLLECTIO_NNAME);
  }

  public Policy findByCriteria(String where, String criteria) {
    Query query = new Query();
    query.addCriteria(Criteria.where(where).is(criteria));
    Policy oldpolicy = mongoOperations.findOne(query, Policy.class, COLLECTIO_NNAME);
    return oldpolicy;
  }

  public Policy findByCriteria( String criteria) {
    Query query = new Query();

    query.addCriteria(Criteria.where("type").is("hdfs"));
    if(StringUtils.isNotBlank(criteria)){
      query.fields().position("privileges.name",1);
      query.addCriteria(Criteria.where("privileges.name").is(criteria));
    }

    Policy oldpolicy = mongoOperations.findOne(query, Policy.class, COLLECTIO_NNAME);
    return oldpolicy;
  }

  public void insert(Policy policy) {
    mongoOperations.insert(policy, COLLECTIO_NNAME);
  }

  public void upset(String type, Privilege privilege) {
    Query query = Query.query(Criteria.where("type").is(type));
    Update update = new Update();
    update.addToSet("privileges", privilege);

    mongoOperations.upsert(query, update, Policy.class, COLLECTIO_NNAME);
  }

  /**
   * 先删除在更新
   * @param type
   * @param privilege
   * @param name
   */
  public void updateByName(String type, Privilege privilege,String name) {

    Query query = Query.query(Criteria.where("type").is(type));
    Update delete = new Update();
    Document document = new Document("name", name);
    delete.pull("privileges", document);

    mongoOperations.updateFirst(query, delete, COLLECTIO_NNAME);

    Update update = new Update();
    update.addToSet("privileges", privilege);
    mongoOperations.upsert(query, update, Policy.class, COLLECTIO_NNAME);
  }


  public Policy findOneByType(String type) {

    Query query = new Query();
    query.addCriteria(Criteria.where("type").is(type));

    return mongoOperations.findOne(query, Policy.class);

  }

  public void addPrivilege(ChiWenPrivilege chiWenPrivilege) {
    Query query = new Query();
    query.addCriteria(Criteria.where("plugin_uid").is("0d047247-bafe-4cf8-8e9b-d5d377284b2d"));
    Update update = new Update().push("privileges", chiWenPrivilege);
    mongoOperations.updateFirst(query, update, COLLECTIO_NNAME);
  }

  public void deletePrivelege(String privilegeName) {

    Query query = new Query();
    query.addCriteria(Criteria.where("plugin_uid").is("0d047247-bafe-4cf8-8e9b-d5d377284b2d"));
    Update update = new BasicUpdate("{'$pull':{'privileges':{'name':'" + privilegeName + "'}}}");
    mongoOperations.updateFirst(query, update, COLLECTIO_NNAME);
  }

  public void removePrivilegeByPNameAndUName(String privilegeName, String userName) {
    Query query = new Query();
    query.addCriteria(Criteria.where("plugin_uid").is("0d047247-bafe-4cf8-8e9b-d5d377284b2d"));
    List<ChiWenPolicy> cwpList = mongoOperations.find(query, ChiWenPolicy.class, COLLECTIO_NNAME);
    for (int i = 0; i < cwpList.get(0).getPrivileges().size(); i++) {
      if (cwpList.get(0).getPrivileges().get(i).getName().equalsIgnoreCase(privilegeName)) {
        for (int j = 0; j < cwpList.get(0).getPrivileges().get(i).getPrivilegeItems().size(); j++) {
          Query query1 = new Query();
          query1
              .addCriteria(Criteria.where("plugin_uid").is("0d047247-bafe-4cf8-8e9b-d5d377284b2d"));
          Update update = new BasicUpdate(
              "{'$pull':{'privileges." + i + ".privilege_items." + j + ".users':'" + userName
                  + "'}}");
          mongoOperations.updateFirst(query1, update, COLLECTIO_NNAME);
        }
      }
    }
  }

  public void grantPrivilegeByPNameAndUName(String privilegeName, String userName) {
    Query query = new Query();
    query.addCriteria(Criteria.where("plugin_uid").is("0d047247-bafe-4cf8-8e9b-d5d377284b2d"));
    List<ChiWenPolicy> cwpList = mongoOperations.find(query, ChiWenPolicy.class, COLLECTIO_NNAME);
    for (int i = 0; i < cwpList.get(0).getPrivileges().size(); i++) {
      if (cwpList.get(0).getPrivileges().get(i).getName().equalsIgnoreCase(privilegeName)) {
        for (int j = 0; j < cwpList.get(0).getPrivileges().get(i).getPrivilegeItems().size(); j++) {
          Query query1 = new Query();
          query1
              .addCriteria(Criteria.where("plugin_uid").is("0d047247-bafe-4cf8-8e9b-d5d377284b2d"));
          Update update = new BasicUpdate(
              "{'$push':{'privileges." + i + ".privilege_items." + j + ".users':'" + userName
                  + "'}}");
          mongoOperations.updateFirst(query1, update, COLLECTIO_NNAME);
        }
      }
    }
  }

  public void removePrivilegeByPNameAndGName(String privilegeName, String groupName) {
    Query query = new Query();
    query.addCriteria(Criteria.where("plugin_uid").is("0d047247-bafe-4cf8-8e9b-d5d377284b2d"));
    List<ChiWenPolicy> cwpList = mongoOperations.find(query, ChiWenPolicy.class, COLLECTIO_NNAME);
    for (int i = 0; i < cwpList.get(0).getPrivileges().size(); i++) {
      if (cwpList.get(0).getPrivileges().get(i).getName().equalsIgnoreCase(privilegeName)) {
        for (int j = 0; j < cwpList.get(0).getPrivileges().get(i).getPrivilegeItems().size(); j++) {
          Query query1 = new Query();
          query1
              .addCriteria(Criteria.where("plugin_uid").is("0d047247-bafe-4cf8-8e9b-d5d377284b2d"));
          Update update = new BasicUpdate(
              "{'$pull':{'privileges." + i + ".privilege_items." + j + ".groups':'" + groupName
                  + "'}}");
          mongoOperations.updateFirst(query1, update, COLLECTIO_NNAME);
        }
      }
    }
  }

  public void grantPrivilegeByPNameAndGName(String privilegeName, String groupName) {
    Query query = new Query();
    query.addCriteria(Criteria.where("plugin_uid").is("0d047247-bafe-4cf8-8e9b-d5d377284b2d"));
    List<ChiWenPolicy> cwpList = mongoOperations.find(query, ChiWenPolicy.class, COLLECTIO_NNAME);
    for (int i = 0; i < cwpList.get(0).getPrivileges().size(); i++) {
      if (cwpList.get(0).getPrivileges().get(i).getName().equalsIgnoreCase(privilegeName)) {
        for (int j = 0; j < cwpList.get(0).getPrivileges().get(i).getPrivilegeItems().size(); j++) {
          Query query1 = new Query();
          query1
              .addCriteria(Criteria.where("plugin_uid").is("0d047247-bafe-4cf8-8e9b-d5d377284b2d"));
          Update update = new BasicUpdate(
              "{'$push':{'privileges." + i + ".privilege_items." + j + ".groups':'" + groupName
                  + "'}}");
          mongoOperations.updateFirst(query1, update, COLLECTIO_NNAME);
        }
      }
    }
  }

//  public MongoTemplate getMongoTemplate() {
//    return mongoTemplate;
//  }
//
//  public void setMongoTemplate(MongoTemplate mongoTemplate) {
//    this.mongoTemplate = mongoTemplate;
//  }

//  public static void main(String[] args) {
//    ApplicationContext app =SpringApplication.run(BigDafApplication.class, args);
//    MongoTemplate mongoTemplate=app.getBean(MongoTemplate.class);
//    Gson gson=new GsonBuilder().setDateFormat("yyyyMMddHHmmssSSS").setPrettyPrinting().create();
//    ChiWenPrivilege chiWenPrivilege=gson.fromJson(" {\n"
//        + "      \"name\": \"TmpdirWrite\",\n"
//        + "      \"resources\": {\n"
//        + "        \"path\": {\n"
//        + "          \"values\": [\n"
//        + "            \"tmptmpdir2\"\n"
//        + "          ]\n"
//        + "        }\n"
//        + "      },\n"
//        + "      \"privilegeItems\": [\n"
//        + "        {\n"
//        + "          \"users\": [\n"
//        + "            \"hive\"\n"
//        + "          ],\n"
//        + "          \"groups\": [\n"
//        + "            \"IT\"\n"
//        + "          ],\n"
//        + "          \"accesses\": [\n"
//        + "            {\n"
//        + "              \"type\": \"write\"\n"
//        + "            }\n"
//        + "          ]\n"
//        + "        },\n"
//        + "        {\n"
//        + "          \"users\": [\n"
//        + "            \"bob\"\n"
//        + "          ],\n"
//        + "          \"groups\": [],\n"
//        + "          \"accesses\": [\n"
//        + "            {\n"
//        + "              \"type\": \"write\"\n"
//        + "            }\n"
//        + "          ]\n"
//        + "        }\n"
//        + "      ]\n"
//        + "    }",ChiWenPrivilege.class);
//
//    PrivilegeDao privilegeDao = new PrivilegeDao();
//    privilegeDao.setMongoTemplate(mongoTemplate);
//    privilegeDao.addPrivilege(chiWenPrivilege);
////    privilegeDao.deletePrivelege("TmpdirWrite");
////    privilegeDao.removePrivilegeByPNameAndUName("TmpdirWrite","hive");
////    privilegeDao.grantPrivilegeByPNameAndUName("TmpdirWrite","hive");
////    privilegeDao.grantPrivilegeByPNameAndGName("TmpdirWrite","hivegroup");
////    privilegeDao.removePrivilegeByPNameAndGName("TmpdirWrite","hivegroup");
//  }
}