package com.databps.bigdaf.admin.dao;

import com.databps.bigdaf.admin.domain.UGroup;
import com.databps.bigdaf.admin.domain.HdpAppUser;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import com.databps.bigdaf.core.util.DateUtils;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

/**
 * GroupDao
 *
 * @author lgc
 * @create 2017-08-14 下午10:42
 */
@Repository
public class GroupDao {

  @Autowired
  private MongoOperations mongoOperations;

  private final static String COLLECTIO_NNAME = "ugroup";

  public List<UGroup> findAllByName(MongoPage page,String name) {
    page.setTotalResults(count(name));
    Query query = new Query();
    if(StringUtils.isNotBlank(name)){
      query.addCriteria(Criteria.where("g_name").is(name));
    }
    Sort sort = new Sort(Direction.DESC, "create_time");

    query.with(sort).skip(page.getFirstResult()).limit(page.getMaxResults());
    List<UGroup> GroupList = mongoOperations.find(query, UGroup.class, COLLECTIO_NNAME);

    return GroupList;
  }

  public long count(String name) {
    Query query = new Query();
    if (StringUtils.isNotBlank(name)) {
      query.addCriteria(Criteria.where("g_name").is(name));
    }
    long count = mongoOperations.count(query, HdpAppUser.class, COLLECTIO_NNAME);
    return count;
  }


  public void insert(UGroup group) {

    Query query = new Query();
    query.addCriteria(Criteria.where("g_name").is(group.getGroupName()));
    UGroup oldGroup = mongoOperations.findOne(query, UGroup.class, COLLECTIO_NNAME);
    if (oldGroup == null) {
      group.setUpdateTime(DateUtils.getNowDateTime());
      mongoOperations.insert(group, COLLECTIO_NNAME);
    }

  }

  public UGroup findOneByName(String name) {

    Query query = new Query();
    query.addCriteria(Criteria.where("g_name").is(name));
    return mongoOperations.findOne(query, UGroup.class, COLLECTIO_NNAME);
  }

  public void update(UGroup group, String id) {

    boolean isUpate = false;
    Query queryOr = new Query();
    queryOr.addCriteria(Criteria.where("g_name").is(group.getGroupName()));
    UGroup oldGroup = mongoOperations.findOne(queryOr, UGroup.class, COLLECTIO_NNAME);
    if (oldGroup != null) {
      if (oldGroup.getId().equals(id)) {
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
      update.set("g_name", group.getGroupName());
      update.set("description", group.getDescription());
      update.set("update_time", DateUtils.getNowDateTime());
      mongoOperations.updateFirst(query, update, COLLECTIO_NNAME);
    }


  }

  public void deleteUserbyName(String name) {
    Query query = new Query();
    query.addCriteria(Criteria.where("g_name").is(name));
    mongoOperations.remove(query, COLLECTIO_NNAME);
  }

  public void deleteById(String id) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(id));
    mongoOperations.remove(query, COLLECTIO_NNAME);
  }

  public UGroup findOneById(String id) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(id));
    return mongoOperations.findOne(query, UGroup.class, COLLECTIO_NNAME);
  }

}