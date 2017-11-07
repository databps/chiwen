package com.databps.bigdaf.admin.config;

import ch.qos.logback.classic.LoggerContext;
import io.github.jhipster.config.JHipsterProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfiguration {

    private final Logger log = LoggerFactory.getLogger(LoggingConfiguration.class);

    private LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

    private final String appName;

    private final String serverPort;

    private final JHipsterProperties jHipsterProperties;

    public LoggingConfiguration(@Value("${spring.application.name}") String appName, @Value("${server.port}") String serverPort,
         JHipsterProperties jHipsterProperties) {
        this.appName = appName;
        this.serverPort = serverPort;
        this.jHipsterProperties = jHipsterProperties;

    }



}
