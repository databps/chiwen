package com.databps.bigdaf.chiwen.hdfsagent;

import java.lang.instrument.Instrumentation;

/**
 * Created by lgc on 17-7-6.
 */
public class AuthCodeInjectionJavaAgent {

  public static final String AUTHORIZATION_AGENT_PARAM = "hdfsAgentOn";

  public static void premain(String agentArgs, Instrumentation inst) {
    boolean flg = false;
    if (agentArgs != null && AUTHORIZATION_AGENT_PARAM.equalsIgnoreCase(agentArgs.trim())) {
      inst.addTransformer(new HadoopAuthClassTransformer());
      System.out.println(
          "AuthCodeInjectionJavaAgent begin! ADMIN_URL: " + System.getenv("ADMIN_URL") +"IS_HADOOP_AUTH_ENABLED:"+ System
              .getenv("IS_HADOOP_AUTH_ENABLED"));
      flg = true;
    }
    System.out.println("bigdaf namenode hdfs agent started " + (flg ? "success" : "failure"));
  }

}