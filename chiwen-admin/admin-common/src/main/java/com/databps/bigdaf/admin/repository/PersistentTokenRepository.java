package com.databps.bigdaf.admin.repository;

import com.databps.bigdaf.admin.security.domain.Admin;
import com.databps.bigdaf.admin.security.domain.PersistentToken;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the PersistentToken entity.
 */
public interface PersistentTokenRepository extends MongoRepository<PersistentToken, String> {

    List<PersistentToken> findByUser(Admin user);

    List<PersistentToken> findByTokenDateBefore(LocalDate localDate);

}
