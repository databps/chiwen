package com.databps.bigdaf.chiwen.adminclient;

import com.databps.bigdaf.chiwen.model.ChiWenPolicyHbaseVo;
import com.databps.bigdaf.chiwen.model.ChiWenPolicyPluginVo;
import com.databps.bigdaf.chiwen.util.GrantRevokeRequest;

/**
 * Created by lgc on 17-7-18.
 */
public interface ChiWenAdminClient {

  void init(String serviceName, String appId);

  ChiWenPolicyPluginVo getHdfsServicePoliciesIfUpdated(String serviceName, String chiWenUUID)
      throws Exception;

  ChiWenPolicyHbaseVo getHbaseServicePoliciesIfUpdated(String serviceName, String chiWenUUID)
      throws Exception;

  void grantAccess(GrantRevokeRequest request) throws Exception;

  void revokeAccess(GrantRevokeRequest request) throws Exception;

}
