package com.databps.bigdaf.admin.dao;

import com.databps.bigdaf.admin.domain.PluginStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author haipeng
 * @create 17-10-18 下午1:33
 */
@Repository
public class PluginStatusDao {
    @Autowired
    private MongoOperations mongoOperations;
    private final static String COLLECTIO_1NNAME = "pluginStatus";


    public void insertOrUpdate(PluginStatus pluginStatus) {
        Query query=new Query();
        Criteria criteria= Criteria.where("cmpy_id").is(pluginStatus.getCmpyId());
        criteria.and("type").is(pluginStatus.getType());
        query.addCriteria(criteria);
        PluginStatus pluginStatus1=mongoOperations.findOne(query,PluginStatus.class);
        if(pluginStatus1==null){
            mongoOperations.insert(pluginStatus,COLLECTIO_1NNAME);
        }else{
            Update update=new Update();
            update.set("audit_count",pluginStatus.getAuditCount());
            update.set("auth_count",pluginStatus.getAuthCount());
            update.set("user_count",pluginStatus.getUserCount());
            mongoOperations.updateFirst(query,update,COLLECTIO_1NNAME);
        }
    }

    public List<PluginStatus> listPluginStatus(String cmpyId) {
        Query query=new Query();
        Criteria criteria= Criteria.where("cmpy_id").is(cmpyId);
        query.addCriteria(criteria);
        List<PluginStatus> pluginStatuses=mongoOperations.find(query,PluginStatus.class);
        return pluginStatuses;
    }
}
