package com.databps.bigdaf.chiwen.policyengine;

import com.databps.bigdaf.chiwen.audit.ChiWenAuditHandler;
import com.databps.bigdaf.chiwen.audit.ChiWenDefaultAuditHandler;
import com.databps.bigdaf.chiwen.model.ChiWenPolicyPluginVo;
import com.databps.bigdaf.chiwen.model.ChiWenServiceDef;
import com.databps.bigdaf.chiwen.policy.ChiWenAccessRequest;
import com.databps.bigdaf.chiwen.policy.ChiWenAccessResult;
import com.databps.bigdaf.chiwen.util.ChiWenAccessRequestUtil;
import com.databps.bigdaf.chiwen.util.ChiWenAccessResultProcessor;
import java.util.Collection;

/**
 * Created by lgc on 17-7-19.
 */
public interface ChiWenPolicyEngine {

  String GROUP_PUBLIC = "public";
  String ANY_ACCESS = "_any";
  String ADMIN_ACCESS = "_admin";

  String AUDIT_ALL = "audit-all";
  String AUDIT_NONE = "audit-none";
  String AUDIT_DEFAULT = "audit-default";

  String USER_CURRENT = "{" + ChiWenAccessRequestUtil.KEY_USER + "}";
  String RESOURCE_OWNER = "{OWNER}";


  public ChiWenAccessResult isAccessAllowed(ChiWenAccessRequest request,
      ChiWenAuditHandler auditHandler);

  public Collection<ChiWenAccessResult> isAccessAllowed(Collection<ChiWenAccessRequest> request,
      ChiWenAuditHandler auditHandler);

  public ChiWenAccessResult createAccessResult(ChiWenAccessRequest request);

  Collection<ChiWenAccessResult> checkPolicy(Collection<ChiWenAccessRequest> request,
      ChiWenDefaultAuditHandler handler);

  ChiWenAccessResult checkPolicy(ChiWenAccessRequest request, ChiWenDefaultAuditHandler handler);

  ChiWenServiceDef getServiceDef();

  void cleanup();

  boolean preCleanup();

  Collection<ChiWenAccessResult> isAccessAllowed(Collection<ChiWenAccessRequest> requests,
      ChiWenAccessResultProcessor resultProcessor);

  ChiWenAccessResult isAccessAllowed(ChiWenAccessRequest request,
      ChiWenAccessResultProcessor resultProcessor);

  void preProcess(Collection<ChiWenAccessRequest> requests);

  void preProcess(ChiWenAccessRequest request);



}
