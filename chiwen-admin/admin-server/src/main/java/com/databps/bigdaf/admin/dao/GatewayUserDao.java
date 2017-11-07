package com.databps.bigdaf.admin.dao;

import com.databps.bigdaf.admin.domain.GatewayUser;
import com.databps.bigdaf.admin.domain.UGroup;
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
 * GatewayUserDao
 *
 * @author lgc
 * @create 2017-08-07 下午8:11
 */

@Repository
public class GatewayUserDao {

  @Autowired
  private MongoOperations mongoOperations;

  private final static String COLLECTIO_NNAME = "user";
  private  String CMPY_ID= "5968802a01cbaa46738eee3d";

  public List<GatewayUser> findAllByName(MongoPage page, String name) {


    Query query = new Query();
    if (StringUtils.isNotBlank(name)) {
      query.addCriteria(Criteria.where("u_name").is(name));
    }
    Sort sort = new Sort(Direction.DESC, "create_time");
    if (page!=null) {
      page.setTotalResults(count(name));
      query.with(sort).skip(page.getFirstResult()).limit(page.getMaxResults());
    }
     List<GatewayUser> GatewayUsers = mongoOperations.find(query, GatewayUser.class, COLLECTIO_NNAME);

    return GatewayUsers;
  }

  public List<GatewayUser> findAllByName(MongoPage page, String name,String gatewayOrHdfs) {

    Query query = new Query();
    if (StringUtils.isNotBlank(name)) {
      query.addCriteria(Criteria.where("u_name").is(name).and("gatewayOrHdfs").is(gatewayOrHdfs));
    }
    if(StringUtils.isNotBlank(gatewayOrHdfs)){
      query.addCriteria(Criteria.where("gatewayOrHdfs").is(gatewayOrHdfs));
    }

    Sort sort = new Sort(Direction.DESC, "create_time");
    if (page!=null) {
      page.setTotalResults(count(name));
      query.with(sort).skip(page.getFirstResult()).limit(page.getMaxResults());
    }

    List<GatewayUser> GatewayUsers = mongoOperations.find(query, GatewayUser.class, COLLECTIO_NNAME);

    return GatewayUsers;
  }

  public GatewayUser findOneById(String id) {

    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(id));
    return mongoOperations.findOne(query, GatewayUser.class, COLLECTIO_NNAME);
  }

  public GatewayUser findOneByName(String name) {

    Query query = new Query();
    query.addCriteria(Criteria.where("u_name").is(name));
    return mongoOperations.findOne(query, GatewayUser.class, COLLECTIO_NNAME);
  }

  public long count(String name) {
    Query query = new Query();
    if (StringUtils.isNotBlank(name)) {
      query.addCriteria(Criteria.where("u_name").is(name));
    }

    long count = mongoOperations.count(query, GatewayUser.class, COLLECTIO_NNAME);
    return count;
  }

  public void insert(GatewayUser user) {

    Query query = new Query();
    query.addCriteria(Criteria.where("u_name").is(user.getuName()));
    GatewayUser oldUser = mongoOperations.findOne(query, GatewayUser.class, COLLECTIO_NNAME);
    if (oldUser == null) {
      user.setUpdateTime(DateUtils.getNowDateTime());
      user.setCmpyId(CMPY_ID);
      mongoOperations.insert(user, COLLECTIO_NNAME);
    }


  }

  public long countUser(String cmpyId) {
    Query query =new Query();
    query.addCriteria(Criteria.where("cmpy_id").is(cmpyId));
    return mongoOperations.count(query,COLLECTIO_NNAME);
  }

  public void update(GatewayUser user, String id) {

    boolean isUpate = false;
    Query queryOr = new Query();
    queryOr.addCriteria(Criteria.where("_id").is(id));
    GatewayUser oldUser = mongoOperations.findOne(queryOr, GatewayUser.class, COLLECTIO_NNAME);
    if (oldUser != null) {
      if (oldUser.getId().equals(id)) {
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
      update.set("u_name", user.getuName());
      update.set("description", user.getDescription());
      update.set("update_time", DateUtils.getNowDateTime());
      update.set("cmpy_id", CMPY_ID);

      if(user.getRoles()!=null){
        update.set("roles",user.getRoles());

      }else{
        update.set("roles",oldUser.getRoles());

      }
      mongoOperations.updateFirst(query, update, COLLECTIO_NNAME);
    }


  }

  public void deleteUser(String userNm) {
    Query query = new Query();
    query.addCriteria(Criteria.where("u_name").is(userNm));
    mongoOperations.remove(query, COLLECTIO_NNAME);
  }

  public void deleteById(String id) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(id));
    mongoOperations.remove(query, COLLECTIO_NNAME);
  }

  /**
   public Page<GatewayUserVo> getGatewayUserListByUNm(Pageable pageable, String userNm) {
   GatewayUserVo GatewayUserVo = new GatewayUserVo();
   Query query = new Query();
   if (StringUtils.isNoneBlank(userNm)) {
   query.addCriteria(Criteria.where("u_name").is(userNm));
   }
   Sort sort = new Sort(Direction.DESC, "create_time");
   long count = mongoTemplate.count(query, GatewayUser.class, COLLECTIO_NNAME);
   query.with(pageable).with(sort);
   List<GatewayUser> GatewayUsers = mongoTemplate.find(query, GatewayUser.class, COLLECTIO_NNAME);
   GatewayUserVo.setGatewayUserList(GatewayUsers);
   List<GatewayUserVo> GatewayUserVoList = new ArrayList<GatewayUserVo>();
   GatewayUserVoList.add(GatewayUserVo);
   return new PageImpl(GatewayUserVoList, pageable, count);
   }

   public void modifyByUNm(String userNm) {
   Query query = new Query();
   if (StringUtils.isNoneBlank(userNm)) {
   query.addCriteria(Criteria.where("u_name").is(userNm));
   }
   Update update = new BasicUpdate("{'$set':{'u_name':'" + userNm + "'}}");
   mongoTemplate.updateFirst(query, update, COLLECTIO_NNAME);
   }

   public void addGatewayUser(GatewayUser GatewayUser) {
   GatewayUser.setGroupNameList(new ArrayList<String>());
   GatewayUser.setCreateTime(DateUtils.getNowDateTime());
   mongoTemplate.insert(GatewayUser, COLLECTIO_NNAME);
   }

   public void grantGroup(String userNm, String groupNm) {
   Query query = new Query();
   query.addCriteria(Criteria.where("u_name").is(userNm));
   Update update = new BasicUpdate("{'$push':{'group_name_list':'" + groupNm + "'}}");
   mongoTemplate.updateFirst(query, update, COLLECTIO_NNAME);

   }

   public void disGrantGroup(String userNm, String groupNm) {
   Query query = new Query();
   query.addCriteria(Criteria.where("u_name").is(userNm));
   Update update = new BasicUpdate("{'$pull':{'group_name_list':'" + groupNm + "'}}");
   mongoTemplate.updateFirst(query, update, COLLECTIO_NNAME);
   }

   public MongoTemplate getMongoTemplate() {
   return mongoTemplate;
   }

   public void setMongoTemplate(MongoTemplate mongoTemplate) {
   this.mongoTemplate = mongoTemplate;
   }

   public static void main(String[] args) {
   ApplicationContext app = SpringApplication.run(BigDafApplication.class, args);
   MongoTemplate mongoTemplate = app.getBean(MongoTemplate.class);
   GatewayUserDao GatewayUserDao = new GatewayUserDao();
   GatewayUserDao.setMongoTemplate(mongoTemplate);
   GatewayUser GatewayUser = new GatewayUser();
   GatewayUser.setAliasName("antony");
   GatewayUser.setuName("leonado");
   GatewayUser.setCountryCode(86);
   GatewayUser.setPhoneNum("12345678901");
   GatewayUser.setEmail("www.catalina@gmail.com");
   GatewayUser.setRemarks("this is a perfect design");
   //    GatewayUserDao.addGatewayUser(GatewayUser);
   //    GatewayUserDao.grantGroup("leonado","supergroup2");
   //    GatewayUserDao.deleteUser("leonado");
   //    List<GatewayUser> aa = GatewayUserDao.getGatewayUserList();
   //    System.out.println(aa.get(1).getAliasName());
   }
   */
}