package com.databps.bigdaf.chiwen;

import com.databps.bigdaf.chiwen.adminclient.ChiWenAdminRESTClient;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by lgc on 17-7-21.
 */
public class TestChiWenAdminRESTClient {
  ChiWenAdminRESTClient chiWenAdminRESTClient =null;
@Before
public void setup(){
  this.chiWenAdminRESTClient=new ChiWenAdminRESTClient();
}
  @Test
  public void testGetServicePoliciesIfUpdated(){
    try {
       chiWenAdminRESTClient.getHbaseServicePoliciesIfUpdated("hbase","11111111111");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
