package com.databps.bigdaf.admin.repository;

import com.databps.bigdaf.admin.audit.domain.PersistentAuditEvent;
import java.time.Instant;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the PersistentAuditEvent entity.
 */
public interface PersistenceAuditEventRepository extends
    MongoRepository<PersistentAuditEvent, String> {

    List<PersistentAuditEvent> findByPrincipal(String principal);

    List<PersistentAuditEvent> findByAuditEventDateAfter(Instant after);

    List<PersistentAuditEvent> findByPrincipalAndAuditEventDateAfter(String principal,
        Instant after);

    List<PersistentAuditEvent> findByPrincipalAndAuditEventDateAfterAndAuditEventType(
        String principle, Instant after, String type);

    Page<PersistentAuditEvent> findAllByAuditEventDateBetween(Instant fromDate, Instant toDate,
        Pageable pageable);
}
