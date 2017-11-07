package com.databps.bigdaf.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BigDafApplication {
  public static void main(String[] args) {
    SpringApplication.run(BigDafApplication.class, args);
  }
}
