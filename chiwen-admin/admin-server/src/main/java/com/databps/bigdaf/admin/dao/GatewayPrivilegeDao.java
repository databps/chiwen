package com.databps.bigdaf.admin.dao;

import com.databps.bigdaf.admin.domain.*;
import com.databps.bigdaf.admin.domain.model.ChiWenPolicy;
import com.databps.bigdaf.admin.domain.model.ChiWenPrivilege;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import com.databps.bigdaf.core.util.DateUtils;
import org.bson.Document;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.BasicUpdate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author merlin
 * @create 2017-08-31 下午2:18
 */
@Repository
public class GatewayPrivilegeDao {
    @Autowired
    private MongoOperations mongoOperations;
    private final static String COLLECTIO_1NNAME = "gateway_role";
    private final static String COLLECTIO_1NNAME2 = "gateway_privilege";

    private  String CMPY_ID= "5968802a01cbaa46738eee3d";

    public List<GatewayCharacter> findGatewayRolesPage(Pageable pageable, String name, String id) {
        Query query = getQuery(pageable, name,id);

        Sort sort = new Sort(Sort.Direction.DESC, "last_modified_date");
        query.with(pageable).with(sort);
        return mongoOperations.find(query, GatewayCharacter.class, COLLECTIO_1NNAME);

    }


    public  GatewayPrivilege findOneGp(Pageable pageable, String name,String id) {
       Query query = getQuery(pageable, name,id);

       return mongoOperations.findOne(query, GatewayPrivilege.class, COLLECTIO_1NNAME2);

    }
    public GatewayCharacter findOneGr(Pageable pageable, String name, String id) {
        Query query = getQuery(pageable, name,id);

        return mongoOperations.findOne(query, GatewayCharacter.class, COLLECTIO_1NNAME);

    }
    public List<GatewayPrivilege> findGateWayPrivilegesPage(MongoPage page, String name, String id) {

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

        return mongoOperations.find(query, GatewayPrivilege.class, COLLECTIO_1NNAME2);

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
        long count = mongoOperations.count(query, GatewayPrivilege.class, COLLECTIO_1NNAME2);
        return count;
    }

    private final static String COLLECTIO_NNAME = "policy";

    public List<Privilege> findAllByName(Pageable pageable) {

        Query query = new Query();
        Sort sort = new Sort(Sort.Direction.DESC, "create_time");
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
            List<Privilege.PrivilegeItems> privilegeItemsList=privilege.getPrivilege_items();
            for(Privilege.PrivilegeItems privilegeItems:privilegeItemsList){
                Set<String> set= privilegeItems.getGroups();
                for(Iterator it = set.iterator(); it.hasNext();){
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
            List<Privilege.PrivilegeItems> privilegeItemsList=privilege.getPrivilege_items();
            for(Privilege.PrivilegeItems privilegeItems:privilegeItemsList){
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
        query.addCriteria(Criteria.where("_id").is(name));
        mongoOperations.remove(query, GatewayPrivilege.class, COLLECTIO_1NNAME2);
    }

    public GatewayPrivilege findOneByName(String name) {
        Query query = new Query();
        //query.addCriteria(Criteria.where("type").is("hdfs").and("privileges").elemMatch(Criteria.where("name").is(name)));
       // query.fields().position("privileges.name",1);
        query.addCriteria(Criteria.where("_id").is(name));
        return mongoOperations.findOne(query, GatewayPrivilege.class, COLLECTIO_1NNAME2);
    }
    public GatewayPrivilege findOneByName2(String name) {
        Query query = new Query();
        //query.addCriteria(Criteria.where("type").is("hdfs").and("privileges").elemMatch(Criteria.where("name").is(name)));
        // query.fields().position("privileges.name",1);
        query.addCriteria(Criteria.where("name").is(name));
        return mongoOperations.findOne(query, GatewayPrivilege.class, COLLECTIO_1NNAME2);
    }

    public GatewayPrivilege findByCriteria(String where, String criteria,String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where(where).is(criteria));
        query.addCriteria(Criteria.where("_id").is(id));

        GatewayPrivilege oldpolicy = mongoOperations.findOne(query, GatewayPrivilege.class, COLLECTIO_1NNAME2);
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

    public void insert(GatewayPrivilege policy)
    {
        policy.setCmpyId(CMPY_ID);
        mongoOperations.insert(policy, COLLECTIO_1NNAME2);
    }

    public void upset(String type, GatewayPrivilege privilege) {
        Query query = Query.query(Criteria.where("type").is(type));
        Update update = new Update();
        update.set("name", privilege.getName());
        update.set("description", privilege.getDescription());
        update.set("update_time", DateUtils.getNowDateTime());
        update.set("sources",privilege.getSources());
        update.set("cmpy_id", CMPY_ID);

        mongoOperations.upsert(query, update, GatewayPrivilege.class, COLLECTIO_NNAME);
    }

    /**
     * 先删除在更新
     * @param type
     * @param privilege
     * @param id
     */
    public void updateByName(String type, GatewayPrivilege privilege,String id) {

        Query query =new  Query();
        query.addCriteria(Criteria.where("type").is(type));
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set("name", privilege.getName());
        update.set("description", privilege.getDescription());
        update.set("update_time", DateUtils.getNowDateTime());
        update.set("sources",privilege.getSources());
        update.set("cmpy_id", CMPY_ID);

        mongoOperations.updateFirst(query, update, COLLECTIO_1NNAME2);
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

    public GatewayPrivilege findOneById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return mongoOperations.findOne(query, GatewayPrivilege.class, COLLECTIO_1NNAME2);
    }
    public long countPrivilege(String cmpyId) {
        Query query =new Query();
        query.addCriteria(Criteria.where("cmpy_id").is(cmpyId));
        return mongoOperations.count(query,COLLECTIO_1NNAME2);
    }
}
