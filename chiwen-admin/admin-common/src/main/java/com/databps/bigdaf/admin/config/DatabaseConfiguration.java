package com.databps.bigdaf.admin.config;

import com.github.mongobee.Mongobee;
import com.mongodb.MongoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories("com.databps.bigdaf.admin.repository")
@Import(value = MongoAutoConfiguration.class)
public class DatabaseConfiguration {

  private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);



  @Bean
  public Mongobee mongobee(MongoClient mongoClient, MongoTemplate mongoTemplate,
      MongoProperties mongoProperties) {
    log.debug("Configuring Mongobee");
    Mongobee mongobee = new Mongobee(mongoClient);
    mongobee.setDbName(mongoProperties.getDatabase());

    ((MappingMongoConverter)mongoTemplate.getConverter()).setTypeMapper(new DefaultMongoTypeMapper(null));

    mongobee.setMongoTemplate(mongoTemplate);
    // package to scan for migrations
    mongobee.setChangeLogsScanPackage("com.databps.bigdaf.admin.config.dbmigrations");
    mongobee.setEnabled(true);

    return mongobee;
  }


}
