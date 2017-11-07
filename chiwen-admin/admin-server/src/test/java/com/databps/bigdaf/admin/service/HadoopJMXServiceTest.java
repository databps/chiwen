package com.databps.bigdaf.admin.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.Before;
import org.junit.Test;

/**
 * hadoop JMX 工具类
 *
 * @author shibingxin
 * @create 2017-07-31 上午10:42
 */
public class HadoopJMXServiceTest {

  private String url;
  private String type;

  @Before
  public void init() {
    url = "http://192.168.1.132:50070/jmx";
  }

  @Test
  public void testGetJMX(){
//    //HadoopJMXDao hadoopJMXDao = new HadoopJMXDao();
//    JsonElement jsonDao = new JsonParser().parse(hadoopJMXDao.getJMX(url));
//    JsonArray beanJsonArray = jsonDao.getAsJsonObject().get("beans").getAsJsonArray();
//    System.out.println(beanJsonArray);
  }


}