package com.databps.bigdaf.chiwen;

import com.databps.bigdaf.chiwen.model.HbaseRoleResponse;
import com.databps.bigdaf.chiwen.policyengine.ChiWenPolicyEngineImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

/**
 * @author shibingxin
 * @create 2017-09-11 下午3:34
 */
public class ChiWenPolicyEngineTest {

  private List<HbaseRoleResponse> roleList = null;
  private Map<String,String> map = new HashMap<String, String>();
  private Set<String> groups =new HashSet<String>();
  private String user =null;
  private String accessType = "create";
  @Before
  public void init() {

//    String jsonString ="[{\"roleName\":\"superrole\",\"users\":[\"superma\"],\"privileges\":[{\"_id\":\"59a8ca39f275830f7996b7d3\",\"cmpyId\":\"5968802a01cbaa46738eee3d\",\"name\":\"billionarieprivilege\",\"resource\":\"table1\",\"rolesName\":[\"superrole\",\"billionarierole\"],\"permissions\":[\"read\",\"write\",\"create\",\"admin\"],\"createTime\":\"20170829160122111\",\"description\":\"billionaire\"}]},{\"roleName\":\"billionarierole\",\"users\":[\"billionariewang\"],\"privileges\":[{\"_id\":\"59a8ca39f275830f7996b7d3\",\"cmpyId\":\"5968802a01cbaa46738eee3d\",\"name\":\"billionarieprivilege\",\"resource\":\"member/address,address2/city1,city\",\"rolesName\":[\"superrole\",\"billionarierole\"],\"permissions\":[\"read\",\"write\",\"create\",\"admin\"],\"createTime\":\"20170829160122111\",\"description\":\"billionaire\"}]}]";
    String jsonString ="[{\"roleName\":\"superrole\",\"users\":[\"superma\"],\"privileges\":[{\"_id\":\"59a8ca39f275830f7996b7d3\",\"cmpyId\":\"5968802a01cbaa46738eee3d\",\"name\":\"billionarieprivilege\",\"resource\":\"/table1*\",\"rolesName\":[\"superrole\",\"billionarierole\"],\"permissions\":[\"read\",\"write\",\"create\",\"admin\"],\"createTime\":\"20170829160122111\",\"description\":\"billionaire\"}]},{\"roleName\":\"billionarierole\",\"users\":[\"billionariewang\"],\"privileges\":[{\"_id\":\"59a8ca39f275830f7996b7d3\",\"cmpyId\":\"5968802a01cbaa46738eee3d\",\"name\":\"billionarieprivilege\",\"resource\":\"/table1*\",\"rolesName\":[\"superrole\",\"billionarierole\"],\"permissions\":[\"read\",\"write\",\"create\",\"admin\"],\"createTime\":\"20170829160122111\",\"description\":\"billionaire\"}]},{\"roleName\":\"root\",\"users\":[\"root\"],\"privileges\":[{\"_id\":\"59b66dc641baa20357318e4e\",\"cmpyId\":\"5968802a01cbaa46738eee3d\",\"name\":\"root\",\"resource\":\"table_abc\",\"rolesName\":[\"root\"],\"permissions\":[\"read\",\"write\",\"create\",\"admin\"],\"createTime\":\"20170829160122111\",\"description\":\"billionaire\"},{\"_id\":\"59b66de041baa20357318e4f\",\"cmpyId\":\"5968802a01cbaa46738eee3d\",\"name\":\"root\",\"resource\":\"table_a/cf_a\",\"rolesName\":[\"root\"],\"permissions\":[\"read\",\"write\",\"create\",\"admin\"],\"createTime\":\"20170829160122111\",\"description\":\"billionaire\"},{\"_id\":\"59b66df941baa20357318e50\",\"cmpyId\":\"5968802a01cbaa46738eee3d\",\"name\":\"root\",\"resource\":\"table_b/cf_b,cf_bb\",\"rolesName\":[\"root\"],\"permissions\":[\"read\",\"write\",\"create\",\"admin\"],\"createTime\":\"20170829160122111\",\"description\":\"billionaire\"},{\"_id\":\"59b66e1241baa20357318e51\",\"cmpyId\":\"5968802a01cbaa46738eee3d\",\"name\":\"root\",\"resource\":\"table_c\",\"rolesName\":[\"root\"],\"permissions\":[\"read\",\"write\",\"create\",\"admin\"],\"createTime\":\"20170829160122111\",\"description\":\"billionaire\"}]}]";
    roleList = new Gson().fromJson(jsonString, new TypeToken<List<HbaseRoleResponse>>(){}.getType());
    map.put("table","table_b");
//    map.put("column-family","cf_b");

    groups.add("root");
    user = "root";
  }

  @Test
  public void test(){
    //ChiWenPolicyEngineImpl policyEngine = new ChiWenPolicyEngineImpl();
    //boolean match =  policyEngine.checkTable(roleList,map,groups,user,accessType);
    //System.out.println(match);

  }
}