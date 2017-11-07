package com.databps.bigdaf.admin.dao;

import com.databps.bigdaf.admin.domain.Config;
import com.databps.bigdaf.admin.domain.Kerberos;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

/**
 * @author shibingxin
 * @create 2017-08-21 下午3:23
 */
@Repository
public class ConfigDao {
    private final static String COLLECTIO_NNAME = "config";
    @Autowired
    private MongoOperations mongoOperations;

    public Config findConfig(String cmpyId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("cmpy_id").is(cmpyId));
        Config config = mongoOperations.findOne(query,Config.class);
        return config;
    }

    public void saveConfig(Config conf) {
        Query query = new Query();
        query.addCriteria(Criteria.where("cmpy_id").is(conf.getCmpyId()));

        Update update = new Update();
        update.set("test_mode",conf.getTestMode());
        update.set("login_max_number",conf.getLoginMaxNumber());
        update.set("login_interval_time",conf.getLoginIntervalTime());
        update.set("kerberos_enable",conf.getKerberosEnable());
        mongoOperations.upsert(query,update,COLLECTIO_NNAME);
    }


    public void saveKerberos(String cmpyId,int kerberos_enable) {
        Query query = new Query();
        query.addCriteria(Criteria.where("cmpy_id").is(cmpyId));
        Update update = new Update();
        update.set("kerberos_enable",kerberos_enable);
        mongoOperations.upsert(query,update,COLLECTIO_NNAME);
    }
}