package com.databps.bigdaf.chiwen;

import com.databps.bigdaf.chiwen.adminclient.ChiWenAdminClient;
import com.databps.bigdaf.chiwen.adminclient.ChiWenAdminRESTClient;
import com.databps.bigdaf.chiwen.model.ChiWenPolicyPluginVo;
import com.databps.bigdaf.chiwen.policy.ChiWenBasePlugin;
import com.databps.bigdaf.chiwen.policyengine.ChiWenPolicyEngine;
import com.databps.bigdaf.chiwen.policy.PolicyRefresher;
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
public class TestPolicyRefresher {

  Class<?> policyRefresherClass = null;
  Class<?> policyEngineClass = null;
  Method loadFromCacheMethod = null;
  Method saveToCache = null;
  PolicyRefresher policyRefresher = null;
  private Gson gson = null;
  ChiWenPolicyEngine policyEngine =null;


  @Before
  public void setup() {
    try {
      policyRefresherClass = Class.forName("com.databps.bigdaf.chiwen.policy.PolicyRefresher");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    try {
      policyEngineClass = Class.forName(
          "com.databps.bigdaf.chiwen.policyengine.ChiWenPolicyEngineImpl");
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
    Class[] paramsForSTCache = new Class[]{ChiWenPolicyPluginVo.class};

    try {
      saveToCache = policyRefresherClass.getDeclaredMethod("saveToCache", paramsForSTCache);
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
    saveToCache.setAccessible(true);


    this.gson = new GsonBuilder().setDateFormat("yyyyMMddHHmmssSSS").setPrettyPrinting()
        .create();


    String serviceType = "hdfs";
    String serviceName = "hdfs";
    String policyName = "hdfs";
    String cacheDir = "F:/test/testfile";

    ChiWenBasePlugin policyEngine = new ChiWenBasePlugin(serviceType,serviceType,serviceType);

    ChiWenAdminClient chiWenAdmin = new ChiWenAdminRESTClient();
    policyRefresher = new PolicyRefresher(policyEngine, serviceType, policyName,
        chiWenAdmin, 30 * 1000, cacheDir);
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

       ChiWenPolicyPluginVo policyInstance = (ChiWenPolicyPluginVo) policy.get(policyEngine);
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
    ChiWenPolicyPluginVo policy = gson.fromJson("{\n"
        + "  \"pluginUid\":\"0d047247-bafe-4cf8-8e9b-d5d377284b2d\",\n"
        + "  \"lastVersion\":24,\n"
        + "  \"updateTime\":\"20160530123417000\",\n"
        + "  \"isEnabled\":true,\n"
        + "  \"privileges\":[\n"
        + "    {\n"
        + "      \"name\": \"TmpdirWrite\",\n"
        + "      \"resources\": {\n"
        + "        \"path\": {\n"
        + "          \"values\": [\n"
        + "            \"tmptmpdir2\"\n"
        + "          ]\n"
        + "        }\n"
        + "      },\n"
        + "      \"privilegeItems\": [\n"
        + "        {\n"
        + "          \"accesses\": [\n"
        + "            {\n"
        + "              \"type\": \"write\"\n"
        + "            }\n"
        + "          ],\n"
        + "          \"users\": [\"hive\"],\n"
        + "          \"groups\": [\n"
        + "            \"IT\"\n"
        + "          ]\n"
        + "        },\n"
        + "        {\n"
        + "          \"accesses\": [\n"
        + "            {\n"
        + "              \"type\": \"write\"\n"
        + "            }\n"
        + "          ],\n"
        + "          \"users\": [\n"
        + "            \"bob\"\n"
        + "          ],\n"
        + "          \"groups\": []\n"
        + "        }\n"
        + "      ]\n"
        + "    }\n"
        + "  ]\n"
        + "}", ChiWenPolicyPluginVo.class);
    try {
      saveToCache.invoke(policyRefresher,policy);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
  }
}