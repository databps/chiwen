package com.databps.bigdaf.chiwen.policy;

import com.databps.bigdaf.chiwen.adminclient.ChiWenAdminClient;
import com.databps.bigdaf.chiwen.adminclient.ChiWenAdminRESTClient;
import com.databps.bigdaf.chiwen.audit.ChiWenAuditHandler;
import com.databps.bigdaf.chiwen.audit.ChiWenDefaultAuditHandler;
import com.databps.bigdaf.chiwen.config.ChiWenConfiguration;
import com.databps.bigdaf.chiwen.model.ChiWenPolicyHbaseVo;
import com.databps.bigdaf.chiwen.model.ChiWenPolicyPluginVo;
import com.databps.bigdaf.chiwen.model.ChiWenServiceDef;
import com.databps.bigdaf.chiwen.policyengine.ChiWenPolicyEngine;
import com.databps.bigdaf.chiwen.policyengine.ChiWenPolicyEngineImpl;
import com.databps.bigdaf.chiwen.util.ChiWenAccessResultProcessor;
import com.databps.bigdaf.chiwen.util.GrantRevokeRequest;
import java.io.File;
import java.util.Collection;
import java.util.Timer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by lgc on 17-7-19.
 */
public class ChiWenBasePlugin {

  private static final Log LOG = LogFactory.getLog(ChiWenBasePlugin.class);




  private ChiWenAccessResultProcessor resultProcessor;
  private String appId;
  private String PolicyName;
  private String serviceName;
  private String serviceType;
  private PolicyRefresher policyRefresher;
  private ChiWenPolicyEngine policyEngine;
  private PolicyRefresher refresher;
  private Timer policyEngineRefreshTimer;

  private String clusterName = "";
  //private ChiWenhbasePluginHeartBeat chiWenhbasePluginHeartBeat;

  public ChiWenBasePlugin(String serviceType, String PolicyName,String appId) {
    this.serviceType = serviceType;//此plugin中的值为hbases
    this.appId=appId;
    this.PolicyName = PolicyName;//此plugin中的值为hbase
  }

  public void setResultProcessor(ChiWenAccessResultProcessor resultProcessor) {
    this.resultProcessor = resultProcessor;
  }

  public String getServiceType() {
    return serviceType;
  }

  public void setServiceType(String serviceType) {
    this.serviceType = serviceType;
  }

  public ChiWenPolicyEngine getPolicyEngine() {
    return policyEngine;
  }


  public ChiWenAccessResult isAccessAllowed(ChiWenAccessRequest request) {
    return isAccessAllowed(request, resultProcessor);
  }

  public Collection<ChiWenAccessResult> isAccessAllowed(Collection<ChiWenAccessRequest> requests,
      ChiWenAccessResultProcessor resultProcessor) {
    ChiWenPolicyEngine policyEngine = this.policyEngine;

    if (policyEngine != null) {
      policyEngine.preProcess(requests);

      return policyEngine.isAccessAllowed(requests, resultProcessor);
    }

    return null;
  }

  public ChiWenAccessResult isAccessAllowed(ChiWenAccessRequest request,
      ChiWenAccessResultProcessor resultProcessor) {
    ChiWenPolicyEngine policyEngine = this.policyEngine;

    if (policyEngine != null) {
      policyEngine.preProcess(request);

      return policyEngine.isAccessAllowed(request, resultProcessor);
    }

    return null;
  }


  public Collection<ChiWenAccessResult> checkPolicy(Collection<ChiWenAccessRequest> request,
      ChiWenDefaultAuditHandler handler) {
    return policyEngine.checkPolicy(request, handler);
  }

  public ChiWenAccessResult checkPolicy(ChiWenAccessRequest request,
      ChiWenDefaultAuditHandler handler) {

    return policyEngine.checkPolicy(request, handler);
  }

  public void setPolicyEngine(ChiWenPolicyEngine policyEngine) {
    this.policyEngine = policyEngine;
  }

  public String getPolicyName() {
    return PolicyName;
  }

  public void setPolicyName(String policyName) {
    PolicyName = policyName;
  }

  public PolicyRefresher getPolicyRefresher() {
    return policyRefresher;
  }

  public void setPolicyRefresher(PolicyRefresher policyRefresher) {
    this.policyRefresher = policyRefresher;
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public String getClusterName() {
    return clusterName;
  }

  public void setClusterName(String clusterName) {
    this.clusterName = clusterName;
  }


  public void init() {
    cleanup();
    ChiWenAdminClient adminClient = createAdminClient(serviceName,appId);
    ChiWenConfiguration chiWenConfiguration = ChiWenConfiguration.getInstance();
    chiWenConfiguration.addResourcesForServiceType(serviceType);

    String cacheDir = System.getenv("HOME") + File.separator + "com/databps/bigdaf/chiwen";

    long pollingIntervalMs = 4 * 1000;

    refresher = new PolicyRefresher(this, serviceType, appId, adminClient,
        pollingIntervalMs, cacheDir);
    refresher.setDaemon(true);
    refresher.startRefresher();

    long policyReorderIntervalMs = 60 * 1000;
    if (policyReorderIntervalMs >= 0 && policyReorderIntervalMs < 15 * 1000) {
      policyReorderIntervalMs = 15 * 1000;
    }


  }


  public static ChiWenAdminClient createAdminClient(String chiwenServiceName,String applicationId) {

    ChiWenAdminClient ret = null;

    if (ret == null) {
      ret = new ChiWenAdminRESTClient();
    }
    ret.init(chiwenServiceName, applicationId);
    return ret;
  }

  public void revokeAccess(GrantRevokeRequest request, ChiWenAccessResultProcessor resultProcessor) throws Exception {
    if(LOG.isDebugEnabled()) {
      LOG.debug("==> ChiWenBasePlugin.revokeAccess(" + request + ")");
    }

    PolicyRefresher   refresher = this.refresher;
    ChiWenAdminClient admin     = refresher == null ? null : refresher.getChiWenAdminClient();
    boolean           isSuccess = false;

    try {
      if(admin == null) {
        throw new Exception("chiwen-admin client is null");
      }

      admin.revokeAccess(request);

      isSuccess = true;
    } finally {
      auditGrantRevoke(request, "revoke", isSuccess, resultProcessor);
    }

    if(LOG.isDebugEnabled()) {
      LOG.debug("<== ChiWenBasePlugin.revokeAccess(" + request + ")");
    }
  }


  public Collection<ChiWenAccessResult> isAccessAllowed(Collection<ChiWenAccessRequest> request,
      ChiWenAuditHandler auditHandler) {
    ChiWenPolicyEngine policyEngine = this.policyEngine;

    if (policyEngine != null) {
      return policyEngine.isAccessAllowed(request, auditHandler);
    }

    return null;
  }

  public synchronized void cleanup() {
    PolicyRefresher refresher = this.refresher;

    ChiWenPolicyEngine policyEngine = this.policyEngine;

    Timer policyEngineRefreshTimer = this.policyEngineRefreshTimer;

    //this.serviceName = null;
    this.policyEngine = null;
    this.refresher = null;
    this.policyEngineRefreshTimer = null;

    if (refresher != null) {
      refresher.stopRefresher();
    }

    if (policyEngineRefreshTimer != null) {
      policyEngineRefreshTimer.cancel();
    }

    if (policyEngine != null) {
      policyEngine.cleanup();
    }

  }

  public void grantAccess(GrantRevokeRequest request, ChiWenAccessResultProcessor resultProcessor)
      throws Exception {
    if (LOG.isDebugEnabled()) {
      LOG.debug("==> ChiWenBasePlugin.grantAccess(" + request + ")");
    }

    PolicyRefresher refresher = this.refresher;
    ChiWenAdminClient admin = refresher == null ? null : refresher.getChiwenAdmin();
    boolean isSuccess = false;

    try {
      if (admin == null) {
        throw new Exception("ChiWen-admin client is null");
      }

      admin.grantAccess(request);

      isSuccess = true;
    } finally {
      auditGrantRevoke(request, "grant", isSuccess, resultProcessor);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("<== ChiWenBasePlugin.grantAccess(" + request + ")");
    }
  }

  private void auditGrantRevoke(GrantRevokeRequest request, String action, boolean isSuccess,
      ChiWenAccessResultProcessor resultProcessor) {
    if (request != null && resultProcessor != null) {
      ChiWenAccessRequestImpl accessRequest = new ChiWenAccessRequestImpl();

      accessRequest.setResource(new ChiWenAccessResourceImpl(request.getResource()));
      accessRequest.setUser(request.getGrantor());
      accessRequest.setAccessType(ChiWenPolicyEngine.ADMIN_ACCESS);
      accessRequest.setAction(action);
      accessRequest.setClientIPAddress(request.getClientIPAddress());
      accessRequest.setClientType(request.getClientType());
      accessRequest.setRequestData(request.getRequestData());
      accessRequest.setSessionId(request.getSessionId());
      accessRequest.setClusterName(request.getClusterName());

      // call isAccessAllowed() to determine if audit is enabled or not
      ChiWenAccessResult accessResult = isAccessAllowed(accessRequest, null);

      if (accessResult != null && accessResult.getIsAudited()) {
        accessRequest.setAccessType(action);
        accessResult.setIsAllowed(isSuccess);

        if (!isSuccess) {
          accessResult.setPolicyId(-1);
        }

        resultProcessor.processResult(accessResult);
      }
    }
  }


  public void setHbasePolicies(ChiWenPolicyHbaseVo policies) {

    // guard against catastrophic failure during policy engine Initialization or
    try {
      ChiWenPolicyEngine oldPolicyEngine = this.policyEngine;

      if (policies == null) {
        policies = null;
      }
      if (policies == null) {
        this.policyEngine = null;
      } else {
        ChiWenPolicyEngine policyEngine = new ChiWenPolicyEngineImpl(appId,policies);

        this.policyEngine = policyEngine;
      }

      if (oldPolicyEngine != null && !oldPolicyEngine.preCleanup()) {
        LOG.error("preCleanup() failed on the previous policy engine instance !!");
      }
    } catch (Exception e) {
      LOG.error(
          "setPolicies: policy engine initialization failed!  Leaving current policy engine as-is. Exception : ",
          e);
    }
  }


  public void setPolicies(ChiWenPolicyPluginVo policies) {

    // guard against catastrophic failure during policy engine Initialization or
    try {
      ChiWenPolicyEngine oldPolicyEngine = this.policyEngine;

      if (policies == null) {
        policies = null;
      }
      if (policies == null) {
        this.policyEngine = null;
      } else {
        ChiWenPolicyEngine policyEngine = new ChiWenPolicyEngineImpl(appId,policies);

        this.policyEngine = policyEngine;
      }

      if (oldPolicyEngine != null && !oldPolicyEngine.preCleanup()) {
        LOG.error("preCleanup() failed on the previous policy engine instance !!");
      }
    } catch (Exception e) {
      LOG.error(
          "setPolicies: policy engine initialization failed!  Leaving current policy engine as-is. Exception : ",
          e);
    }
  }


  public int getServiceDefId() {
    ChiWenServiceDef serviceDef = getServiceDef();

    return serviceDef != null && serviceDef.getId() != null ? serviceDef.getId().intValue() : -1;
  }

  public ChiWenServiceDef getServiceDef() {
    ChiWenPolicyEngine policyEngine = this.policyEngine;

    return policyEngine != null ? policyEngine.getServiceDef() : null;
  }
}
