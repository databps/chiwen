package com.databps.bigdaf.admin.dao;

import com.databps.bigdaf.admin.audit.domain.PersistentAuditEvent;
import com.databps.bigdaf.admin.domain.Audit;
import com.databps.bigdaf.core.mongo.plugin.MongoPage;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * @author shibingxin
 * @create 2017-08-18 下午2:47
 */
@Repository
public class AuditEventDao {

  private static final String COLLECTIO_1NNAME = "persistent_audit_event";
  @Autowired
  private MongoOperations mongoOperations;

  public List<PersistentAuditEvent> findPersistentAuditEvent(MongoPage page) {
    page.setTotalResults(count());
    Query query = new Query();
    Sort sort = new Sort(Direction.DESC, "auditEventDate");
    query.with(sort).skip(page.getFirstResult()).limit(page.getMaxResults());
    return  mongoOperations.find(query, PersistentAuditEvent.class, COLLECTIO_1NNAME);
  }

  public long count() {
    Query query = new Query();
    long count = mongoOperations.count(query, PersistentAuditEvent.class, COLLECTIO_1NNAME);
    return count;
  }
}