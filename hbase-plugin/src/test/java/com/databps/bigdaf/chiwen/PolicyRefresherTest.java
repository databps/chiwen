package com.databps.bigdaf.chiwen;

import com.databps.bigdaf.chiwen.adminclient.ChiWenAdminClient;
import com.databps.bigdaf.chiwen.adminclient.ChiWenAdminRESTClient;
import com.databps.bigdaf.chiwen.model.ChiWenPolicyHbaseVo;
import com.databps.bigdaf.chiwen.model.ChiWenPrivilege;
import com.databps.bigdaf.chiwen.policy.PolicyRefresher;
import com.databps.bigdaf.chiwen.policyengine.ChiWenPolicyEngine;
import com.databps.bigdaf.chiwen.policyengine.ChiWenPolicyEngineImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.Before;
import org.junit.Test;

/**
 * TestPolicyRefresher
 *
 * @author lgc
 * @create 2017-07-22 下午3:07
 */
public class PolicyRefresherTest {

  Class<?> policyRefresherClass = null;
  Class<?> policyEngineClass = null;
  Method loadFromCacheMethod = null;
  Method saveToCache = null;
  PolicyRefresher policyRefresher = null;
  private Gson gson = null;
  ChiWenPolicyEngine policyEngine = null;


  @Before
  public void setup() {
    try {
      policyRefresherClass = Class.forName("com.databps.bigdaf.chiwen.policy.PolicyRefresher");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    try {
      policyEngineClass = Class.forName("com.databps.bigdaf.chiwen.policy.ChiWenPolicyEngineImpl");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    Class[] params = new Class[]{};
    try {
      loadFromCacheMethod = policyRefresherClass.getDeclaredMethod("loadFromCache", params);
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
    loadFromCacheMethod.setAccessible(true);
    Class[] paramsForSTCache = new Class[]{ChiWenPolicyHbaseVo.class};

    try {
      saveToCache = policyRefresherClass.getDeclaredMethod("saveToCache", paramsForSTCache);
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
    saveToCache.setAccessible(true);

    this.gson = new GsonBuilder().setDateFormat("yyyyMMddHHmmssSSS").setPrettyPrinting()
        .create();

    //ChiWenPolicyEngine policyEngine = new ChiWenPolicyEngineImpl();
    String serviceType = "hbase";
    String serviceName = "hbase";
    String policyName = "hbase";
//    String cacheDir = "F:/test/testfile";
    ChiWenAdminClient chiWenAdmin = new ChiWenAdminRESTClient();
    //policyRefresher = new PolicyRefresher(policyEngine, serviceType, serviceName, policyName,chiWenAdmin, 30 * 1000, "");
  }


  @Test
  public void testLoadFromCache() {
    try {
      loadFromCacheMethod.invoke(policyRefresher, null);
      try {
        Field policyEngineField = policyRefresherClass.getDeclaredField("policyEngine");
        policyEngineField.setAccessible(true);
        policyEngine = (ChiWenPolicyEngine) policyEngineField.get(policyRefresher);
        Field policy = policyEngineClass.getDeclaredField("policy");
        policy.setAccessible(true);

        ChiWenPolicyHbaseVo policyInstance = (ChiWenPolicyHbaseVo) policy.get(policyEngine);
        System.out.println("QQQQQQQQ" + gson.toJson(policyInstance).toString());
      } catch (NoSuchFieldException e) {
        e.printStackTrace();
      }
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testSaveToCache() {
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.setPrettyPrinting();
//        Gson gson1 = gsonBuilder.create();

//        System.out.printf(gson1.toString());
    String jsonTest = "{\n"
        + "  pluginUid:\"jliuyhiu2313hbk12379hkjhjih\",\n"
        + "  lastVersion:\"1234567890\",\n"
        + "  updateTime:\"20170830172020111\",\n"
        + "  isEnabled:\"true\",\n"
        + "  type: \"hbase\",\n"
        + "  roles:[{\n"
        + "  \"roleName\": \"role1\",\n"
        + "  \"users\": [\n"
        + "    \"superma\",\n"
        + "    \"billionairewang\"\n"
        + "  ],\n"
        + "  \"privileges\": [\n"
        + "    {\n"
        + "      \"resource\": \"/table1/\",\n"
        + "      \"permissions\": [\n"
        + "        \"read\",\n"
        + "        \"write\",\n"
        + "        \"create\",\n"
        + "        \"admin\",\n"
        + "        \"execute\"\n"
        + "      ]\n"
        + "    },\n"
        + "    {\n"
        + "      \"resource\": \"/table2/\",\n"
        + "      \"permissions\": [\n"
        + "        \"read\",\n"
        + "        \"write\",\n"
        + "        \"admin\",\n"
        + "        \"execute\"\n"
        + "      ]\n"
        + "    },\n"
        + "    {\n"
        + "      \"resource\": \"/table3/\",\n"
        + "      \"permissions\": [\n"
        + "        \"read\",\n"
        + "        \"write\"\n"
        + "      ]\n"
        + "    }\n"
        + "  ]\n"
        + "},{\n"
        + "  \"roleName\": \"role2\",\n"
        + "  \"users\": [\n"
        + "    \"ssssssuperma\",\n"
        + "    \"bbbbbbillionairewang\"\n"
        + "  ],\n"
        + "  \"privileges\": [\n"
        + "    {\n"
        + "      \"resource\": \"/table111/family1,family2\",\n"
        + "      \"permissions\": [\n"
        + "        \"read\",\n"
        + "        \"write\",\n"
        + "        \"create\",\n"
        + "        \"admin\",\n"
        + "        \"execute\"\n"
        + "      ]\n"
        + "    },\n"
        + "    {\n"
        + "      \"resource\": \"/table222/family1/column1,column2\",\n"
        + "      \"permissions\": [\n"
        + "        \"read\",\n"
        + "        \"write\",\n"
        + "        \"admin\",\n"
        + "        \"execute\"\n"
        + "      ]\n"
        + "    },\n"
        + "    {\n"
        + "      \"resource\": \"/table333/familySuper\",\n"
        + "      \"permissions\": [\n"
        + "        \"read\",\n"
        + "        \"write\"\n"
        + "      ]\n"
        + "    }\n"
        + "  ]\n"
        + "}]\n"
        + "}";
    System.out.print(jsonTest);
    ChiWenPolicyHbaseVo policy = gson.fromJson(jsonTest, ChiWenPolicyHbaseVo.class);
    try {
      saveToCache.invoke(policyRefresher, policy);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
  }
}


