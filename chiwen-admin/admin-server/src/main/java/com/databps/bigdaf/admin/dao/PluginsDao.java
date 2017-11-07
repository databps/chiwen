package com.databps.bigdaf.admin.dao;

import com.databps.bigdaf.admin.domain.Plugins;
import com.databps.bigdaf.core.util.DateUtils;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * @author shibingxin
 * @create 2017-08-12 下午11:28
 */
@Repository
public class PluginsDao {

    @Autowired
    private MongoTemplate mongoTemplate;
    private final static String collectionName = "plugins";

  public void save(String cmpyId, String uuid,String agenttype,String hostname) {
    Plugins plugin =new Plugins();
    plugin.setCmpyId(cmpyId);
    plugin.setUpdateTime(DateUtils.formatDateNow());
    plugin.setId(uuid);
    plugin.setHostname(hostname);
    plugin.setName(agenttype);
    mongoTemplate.save(plugin, collectionName);
  }

  public List<Plugins> getPlugins(String cmpyId ,String name) {
    Query query = new Query();
    query.addCriteria(Criteria.where("cmpy_id").is(cmpyId).and("name").is(name));
    return mongoTemplate.find(query,Plugins.class);
  }
  }