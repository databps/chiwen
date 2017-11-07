package com.databps.bigdaf.chiwen.policyengine;

import com.databps.bigdaf.chiwen.model.ChiWenPolicyHbaseVo;
import com.databps.bigdaf.chiwen.model.ChiWenPolicyPluginVo;
import java.util.List;

/**
 * @author merlin
 * @create 2017-11-01 下午4:33
 */
public class ChiWenPolicyRepository {

  private final String serviceId;
  private final String appId;
  private ChiWenPolicyPluginVo policies;
  private ChiWenPolicyHbaseVo hbasePolicies;

  ChiWenPolicyRepository(String appId, ChiWenPolicyPluginVo servicePolicies) {
    super();
    this.policies=servicePolicies;
    this.appId = appId;
    this.serviceId=servicePolicies.getServiceId();

  }

  ChiWenPolicyRepository(String appId, ChiWenPolicyHbaseVo servicePolicies) {
    super();
    this.hbasePolicies=servicePolicies;
    this.appId = appId;
    this.serviceId=servicePolicies.getServiceId();

  }


  public ChiWenPolicyPluginVo getPolicies() {
    return policies;
  }

  public ChiWenPolicyHbaseVo getHbasePolicies() {
    return hbasePolicies;
  }

}
