package com.databps.bigdaf.chiwen.policyengine;

import com.databps.bigdaf.chiwen.audit.ChiWenAuditHandler;
import com.databps.bigdaf.chiwen.audit.ChiWenDefaultAuditHandler;
import com.databps.bigdaf.chiwen.model.ChiWenHiveRole;
import com.databps.bigdaf.chiwen.model.ChiWenPolicyHbaseVo;
import com.databps.bigdaf.chiwen.model.ChiWenPolicyPluginVo;
import com.databps.bigdaf.chiwen.model.ChiWenPrivilege;
import com.databps.bigdaf.chiwen.model.ChiWenPrivilegeItem;
import com.databps.bigdaf.chiwen.model.ChiWenServiceDef;
import com.databps.bigdaf.chiwen.model.HbasePrivilegeVo;
import com.databps.bigdaf.chiwen.model.HbaseRoleResponse;
import com.databps.bigdaf.chiwen.model.Privilege;
import com.databps.bigdaf.chiwen.plugin.ChiWenContextEnricher;
import com.databps.bigdaf.chiwen.policy.ChiWenAccessRequest;
import com.databps.bigdaf.chiwen.policy.ChiWenAccessResource;
import com.databps.bigdaf.chiwen.policy.ChiWenAccessResult;
import com.databps.bigdaf.chiwen.policy.ChiWenMutableResource;
import com.databps.bigdaf.chiwen.policy.ChiWenResourceMatcher;
import com.databps.bigdaf.chiwen.policy.ChiWenResourceMatcherImpl;
import com.databps.bigdaf.chiwen.util.ChiWenAccessRequestUtil;
import com.databps.bigdaf.chiwen.util.ChiWenAccessResultProcessor;
import com.databps.bigdaf.chiwen.util.MatchUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by lgc on 17-7-18.
 */
public class ChiWenPolicyEngineImpl implements ChiWenPolicyEngine {

  private static final Log LOG = LogFactory.getLog(ChiWenPolicyEngineImpl.class);
  private String policyName;
  private String serviceType;
  private String serviceId;


  ChiWenPolicyRepository policyRepository;
  ChiWenResourceMatcher matcher;


  public ChiWenPolicyEngineImpl(String appId,ChiWenPolicyPluginVo vo) {
    policyRepository = new ChiWenPolicyRepository(appId, vo);
    matcher = new ChiWenResourceMatcherImpl();
    serviceId=vo.getServiceId();
    serviceType=vo.getServiceType();

  }

  public ChiWenPolicyEngineImpl(String appId,ChiWenPolicyHbaseVo vo) {
    policyRepository = new ChiWenPolicyRepository(appId, vo);
    matcher = new ChiWenResourceMatcherImpl();
    serviceId=vo.getServiceId();
    serviceType=vo.getServiceType();

  }



  private List<ChiWenContextEnricher> allContextEnrichers;



  private void setResourceServiceDef(ChiWenAccessRequest request) {
    ChiWenAccessResource resource = request.getResource();

    if (resource instanceof ChiWenMutableResource) {
      ChiWenMutableResource mutable = (ChiWenMutableResource) resource;
    } else {
      LOG.debug("ChiWenPolicyEngineImpl.setResourceServiceDef(): Cannot set ServiceDef in RangerMutableResource.");
    }
  }

  private ChiWenAccessResult switchResult(ChiWenAccessRequest request){
    ChiWenAccessResult ret =null;
    if(serviceType.equals("hdfs")){//需要一个公共方法来判断是否是hdfs或者hbase请求
      ret=isAccessAllowedNoAudit(request);
    }else if (serviceType.equals("hbase")|| serviceType.equals("hive")){
      ret=isHbaseAccessAllowedNoAudit(request);
    }
    return ret;
  }
  @Override
  public ChiWenAccessResult isAccessAllowed(ChiWenAccessRequest request, ChiWenAccessResultProcessor resultProcessor) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("==> ChiWenPolicyEngineImpl.isAccessAllowed(" + request + ")");
    }
    ChiWenAccessResult ret =switchResult(request);



    updatePolicyUsageCounts(request, ret);

    if (resultProcessor != null) {


      resultProcessor.processResult(ret);

    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("<== ChiWenPolicyEngineImpl.isAccessAllowed(" + request + "): " + ret);
    }

    return ret;
  }

  @Override
  public void preProcess(Collection<ChiWenAccessRequest> requests) {

  }

  private void updatePolicyUsageCounts(ChiWenAccessRequest accessRequest, ChiWenAccessResult accessResult) {

    boolean auditCountUpdated = false;

  }


  @Override
  public void preProcess(ChiWenAccessRequest request) {
    if(LOG.isDebugEnabled()) {
      LOG.debug("==> ChiWenPolicyEngineImpl.preProcess(" + request + ")");
    }

    setResourceServiceDef(request);


    ChiWenAccessRequestUtil.setCurrentUserInContext(request.getContext(), request.getUser());


    if(LOG.isDebugEnabled()) {
      LOG.debug("<== ChiWenPolicyEngineImpl.preProcess(" + request + ")");
    }
  }


  public Collection<ChiWenAccessResult> checkPolicy(Collection<ChiWenAccessRequest> requests , ChiWenDefaultAuditHandler handler) {
    Collection<ChiWenAccessResult>  _results=new ArrayList<ChiWenAccessResult>();
    for(ChiWenAccessRequest  request : requests) {


//      ChiWenAccessResult _result = createAccessResult(request);
//      _result.setAllowed(true);
//      _result.setAudited(true);
//      _result.setReason("success");
////    LOG.info("=================> request:"+request.toString());
//      System.out.println("ccccccccccc"+policy.getRoles().get(0).getRoleName());
//
//      boolean isEnabled = policy.isEnabled();
//      if (isEnabled) {
//        List<ChiWenHiveRole> roleList = policy.getRoles();
//        ChiWenAccessResource resource = request.getResource();
//        String user = request.getUser();
//        String accessType = request.getAccessType();
//        Map<String, String> tablesMap = resource.getAsMap();
//        boolean check = checkTable(roleList, tablesMap, request.getUserGroups(), request.getUser(), accessType);
//        if (check) {
//          _result.setAllowed(true);
//        } else {
//          _result.setAllowed(false);
//          _result.setReason("failure");
//        }
//      }
////    LOG.info("=================> _result " +_result.toString());
//      AuthzAuditEvent event = handler.getAuthzEvents(_result);
////    LOG.info("=================> event  " + event.toString());
//      _results.add(_result);
    }
    return _results;
  }

  @Override
  public ChiWenAccessResult checkPolicy(ChiWenAccessRequest request, ChiWenDefaultAuditHandler handler) {
//    ChiWenAccessResult _result =  createAccessResult(request);
//    _result.setAllowed(true);
//    _result.setAudited(true);
//    _result.setReason("success");
//
////    LOG.info("=================> request:"+request.toString());
//    boolean isEnabled = policy.isEnabled();
//    if(isEnabled) {
//      List<ChiWenHiveRole> roleList = policy.getRoles();
//      ChiWenAccessResource resource = request.getResource();
//      String user = request.getUser();
//      String accessType = request.getAccessType();
//      Map<String,String> tablesMap = resource.getAsMap();
//      boolean check = checkTable(roleList,tablesMap,request.getUserGroups(),request.getUser(),accessType);
//      if(check) {
//        _result.setAllowed(true);
//      }else {
//        _result.setAllowed(false);
//        _result.setReason("failure");
//      }
//    }
////    LOG.info("=================> _result " +_result.toString());
//    AuthzAuditEvent event = handler.getAuthzEvents(_result);
//    handler.logAuthzAudit(event);
//    LOG.info("=================> event  " + event.toString());
    return null;  }

  @Override
  public ChiWenServiceDef getServiceDef() {
    return null;
  }

  @Override
  public void cleanup() {
    if (LOG.isDebugEnabled()) {
      LOG.debug("==> ChiWenPolicyEngineImpl.cleanup()");
    }


    preCleanup();

    if (CollectionUtils.isNotEmpty(allContextEnrichers)) {
//      for (ChiWenContextEnricher contextEnricher : allContextEnrichers) {
//        contextEnricher.cleanup();
//      }
    }

    this.allContextEnrichers = null;


    if (LOG.isDebugEnabled()) {
      LOG.debug("<== ChiWenPolicyEngineImpl.cleanup()");
    }
  }


  @Override
  public boolean preCleanup() {

    boolean ret = true;
    if (LOG.isDebugEnabled()) {
      LOG.debug("==> ChiWenPolicyEngineImpl.preCleanup()");
    }

    if (CollectionUtils.isNotEmpty(allContextEnrichers)) {
      for (ChiWenContextEnricher contextEnricher : allContextEnrichers) {
//        boolean notReadyForCleanup = contextEnricher.preCleanup();
//        if (!notReadyForCleanup) {
//          if (LOG.isDebugEnabled()) {
//            LOG.debug("contextEnricher.preCleanup() failed for contextEnricher=" + contextEnricher.getName());
//          }
//          ret = false;
//        }
      }
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("<== ChiWenPolicyEngineImpl.preCleanup() : result=" + ret);
    }

    return ret;
  }

  @Override
  public Collection<ChiWenAccessResult> isAccessAllowed(Collection<ChiWenAccessRequest> requests,
      ChiWenAccessResultProcessor resultProcessor) {
    return null;
  }


  public boolean checkTable(List<HbaseRoleResponse> roleList , Map<String,String> tablesMap , Set<String>  userGroups , String user,String accessType) {
    boolean bool = false ;
    if(roleList ==null)
      return false ;
    if(tablesMap ==null || tablesMap.isEmpty()) { //TODO shibingxin 这块是因为启动的时候HRegion 会put 一个空tablesMap的结构 不能拦截否则服务会Shutdown
      return true;
    }
    try {
      HbaseRoleResponse hbaseRole = null;
      for (HbaseRoleResponse rolePolicy : roleList) {
        List<String> users = rolePolicy.getUsers();
        if(users.contains(user)) {
          hbaseRole = rolePolicy;
          break;
        }
      }
      if (hbaseRole == null)
        return false;
      List<HbasePrivilegeVo> privileges = hbaseRole.getPrivileges();
      if (privileges == null)
        return false;
      for (HbasePrivilegeVo privilege : privileges) {

        if (!checkHbasePermission(privilege.getPermissions(), accessType)) {
          continue;
        }
        String resource = privilege.getResource();
        bool = checkHbaseResource(resource, tablesMap);
        if (bool) {
          break;
        }
      }
    }catch (Exception e) {
      e.printStackTrace();
      return bool;
    }
    return bool;
  }
  private boolean checkHbasePermission(List<String> permissions ,String accessType) {
    boolean bool =false;
    if(permissions ==null){
      return false;
    }
    bool = permissions.contains(accessType);
    return bool;
  }

  private boolean checkHbaseResource(String resource , Map<String,String> tablesMap) {
    boolean match = false ;
    if(resource ==null)
      return false ;
    if(tablesMap ==null)
      return false;


    String  table = null;
    String column_family = null;
    String column =null;
    String [] resourceArray = resource.split("/");
    table = resourceArray[0];
    if(resourceArray.length>=2) {
      column_family = resourceArray[1];
    }
    if(resourceArray.length >=3) {
      column = resourceArray[2];
    }
    String hbase_table = tablesMap.get("table");
    String hbase_column_family = tablesMap.get("column-family");
    String hbase_column = tablesMap.get("column");
    match = MatchUtils.match(table,hbase_table);
    if(!match)
      return match;
    if(column_family !=null) {
      String [] colum_family_array = column_family.split(",");
      for(String family: colum_family_array) {
        if(MatchUtils.match(family,hbase_column_family)) {
          match = true;
          break;
        }else{
          match =false;
        }
      }
    }
    if(column !=null) {
      String [] column_array = column.split(",");
      for(String _column: column_array) {
        if(MatchUtils.match(_column,hbase_column)) {
          match = true;
          break;
        }else{
          match =false;
        }
      }
    }
    return match;
  }






  @Override
  public ChiWenAccessResult isAccessAllowed(ChiWenAccessRequest request,
      ChiWenAuditHandler auditHandler) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("==> ChiWenPolicyEngineImpl.isAccessAllowed(" + request + ")");
    }

    ChiWenAccessResult ret = isAccessAllowedNoAudit(request);

    if (auditHandler != null) {
      auditHandler.logAudit(ret);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("<== ChiWenPolicyEngineImpl.isAccessAllowed(" + request + "): " + ret);
    }

    return ret;
  }

  @Override
  public Collection<ChiWenAccessResult> isAccessAllowed(Collection<ChiWenAccessRequest> request, ChiWenAuditHandler auditHandler) {
    return null;
  }




  protected ChiWenAccessResult isHbaseAccessAllowedNoAudit(ChiWenAccessRequest request) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("==> ChiWenPolicyEngineImpl.isAccessAllowedNoAudit(" + request + ")");
    }

    ChiWenAccessResult ret = createAccessResult(request);
    if (ret != null && request != null) {

      if (policyRepository.getHbasePolicies() == null) {
        ret.setIsAccessDetermined(true);
        ret.setIsAllowed(true);
      } else {
        if (policyRepository.getHbasePolicies().getEnabled()) {
          if (request != null) {
            List<HbaseRoleResponse>  roleList=policyRepository.getHbasePolicies().getRoles();
            ChiWenAccessResource resource = request.getResource();
            Map<String,String> tablesMap = resource.getAsMap();
            String accessType = request.getAccessType();
            boolean check = checkTable(roleList,tablesMap,request.getUserGroups(),request.getUser(),accessType);
            if(check) {
              ret.setIsAllowed(true);
            }else {
              ret.setIsAllowed(false);
            }
          }
          if (ret.getIsAllowed()) {
            ret.setIsAccessDetermined(true);
          }
        } else {
          ret.setIsAccessDetermined(true);
          ret.setIsAllowed(true);
        }


      }
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("<== ChiWenPolicyEngineImpl.isAccessAllowedNoAudit(" + request + "): " + ret);
    }

    return ret;
  }


  protected ChiWenAccessResult isAccessAllowedNoAudit(ChiWenAccessRequest request) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("==> ChiWenPolicyEngineImpl.isAccessAllowedNoAudit(" + request + ")");
    }

    ChiWenAccessResult ret = createAccessResult(request);
    if (ret != null && request != null) {

      if (policyRepository.getPolicies() == null) {
        ret.setIsAccessDetermined(true);
        ret.setIsAllowed(true);
      } else {
        ret.setAudited(policyRepository.getPolicies().getAudited());//判断是否审计
        if (policyRepository.getPolicies().getEnabled()) {
          if (request != null) {
            if (checkIsInSuperPath(request)) {
              ret.setIsAllowed(true);
            } else {
              List<ChiWenPrivilege> privileges = policyRepository.getPolicies().getPrivileges();
              if (privileges != null) {
                for (ChiWenPrivilege privilege : privileges) {
                  evaluate(privilege, request, ret);
                  if (ret.getIsAllowed() && ret.getIsAudited()) {
                    break;
                  }
                }
              }
            }
          }
          if (ret.getIsAllowed()) {
            ret.setIsAccessDetermined(true);
          }
        } else {
          ret.setIsAccessDetermined(true);
          ret.setIsAllowed(true);
        }


      }
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("<== ChiWenPolicyEngineImpl.isAccessAllowedNoAudit(" + request + "): " + ret);
    }

    return ret;
  }


  private void evaluate(ChiWenPrivilege privilege, ChiWenAccessRequest request,
      ChiWenAccessResult ret) {

    List<String> path = privilege.getResources().getPath().getValues();
    boolean flag = false;
    for (String pathStr : path) {
      flag = matcher.isMatch(request.getPath(), pathStr);
      if (flag) {
        break;
      }
    }
    if (!flag) {
      ret.setIsAllowed(false);
      return;
    }
    if (!matchUserGroupAccessType(privilege, request.getUser(), request.getUserGroups(),
        request.getAccessType())) {
      ret.setIsAllowed(false);
      return;
    }
    ret.setIsAllowed(true);
  }


  private boolean checkIsInSuperPath(ChiWenAccessRequest request) {
      List<ChiWenPrivilege> privileges = policyRepository.getPolicies().getPrivileges();
      ChiWenPrivilege chiWenPrivilege = null;
      boolean ret = false;
      for (ChiWenPrivilege tmpPrivilege : privileges) {
        if ("/*".equalsIgnoreCase(tmpPrivilege.getResources().getPath().getValues().get(0))) {
          chiWenPrivilege = tmpPrivilege;
          break;
        }
      }
      if (chiWenPrivilege == null) {
        return false;
      }
      for (ChiWenPrivilegeItem privilegeItem : chiWenPrivilege.getPrivilegeItems()) {
        if (privilegeItem != null) {
          if (!ret && request.getUser() != null && privilegeItem.getUsers() != null) {
            ret = privilegeItem.getUsers().contains(request.getUser());
          }
          if (!ret && request.getUserGroups() != null && privilegeItem.getGroups() != null) {
            ret = !Collections.disjoint(privilegeItem.getGroups(), request.getUserGroups());
          }
        }
        if (ret) {
          break;
        }
      }
      return ret;
    }


  private boolean matchUserGroupAccessType(ChiWenPrivilege privilege, String user,
      Collection<String> groups, String accessType) {
    if (LOG.isDebugEnabled()) {
      LOG.debug(
          "==> ChiWenDefaultPolicyEvaluator.matchUserGroupAccessType(" + privilege + ", " + user
              + ", " + groups + ")");
    }

    boolean ret = false;
    boolean isUserPrivilNotUsed = true;

    for (ChiWenPrivilegeItem privilegeItem : privilege.getPrivilegeItems()) {
      if (privilegeItem != null) {
        if (!ret && user != null && privilegeItem.getUsers() != null) {
          ret = privilegeItem.getUsers().contains(user);
          isUserPrivilNotUsed = false;
        }
        if (ret && privilegeItem.getAccesses().get(0) != null
            && privilegeItem.getAccesses().get(0).getType() != null) {
          ret = privilegeItem.getAccesses().get(0).getType().equalsIgnoreCase(accessType);
        }
        if (ret) {
          break;
        }
      }
    }

    if (isUserPrivilNotUsed) {
      for (ChiWenPrivilegeItem privilegeItem : privilege.getPrivilegeItems()) {
        if (privilegeItem != null) {
          if (!ret && groups != null && privilegeItem.getGroups() != null) {
            ret = !Collections.disjoint(privilegeItem.getGroups(), groups);
          }
          if (ret && privilegeItem.getAccesses().get(0) != null
              && privilegeItem.getAccesses().get(0).getType() != null) {
            ret = privilegeItem.getAccesses().get(0).getType().equalsIgnoreCase(accessType);
          }
          if (ret) {
            break;
          }
        }
      }
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug(
          "<== ChiWenDefaultPolicyEvaluator.matchUserGroupAccessType(" + privilege + ", " + user
              + ", " + groups + "): " + ret);
    }

    return ret;
  }


  @Override
  public ChiWenAccessResult createAccessResult(ChiWenAccessRequest request) {
    return new ChiWenAccessResult(serviceType,serviceId, request);
  }

}
