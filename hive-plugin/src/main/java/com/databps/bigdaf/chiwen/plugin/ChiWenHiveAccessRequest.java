package com.databps.bigdaf.chiwen.plugin;

import com.databps.bigdaf.chiwen.policy.ChiWenAccessRequestImpl;
import com.databps.bigdaf.chiwen.policyengine.ChiWenPolicyEngine;
import com.databps.bigdaf.chiwen.util.ChiWenAccessRequestUtil;
import java.util.Date;
import java.util.Set;
import org.apache.hadoop.hive.ql.security.authorization.plugin.HiveAuthzContext;
import org.apache.hadoop.hive.ql.security.authorization.plugin.HiveAuthzSessionContext;
import org.apache.hadoop.hive.ql.security.authorization.plugin.HiveOperationType;

/**
 * @author merlin
 * @create 2017-11-07 下午6:26
 */
public class ChiWenHiveAccessRequest extends ChiWenAccessRequestImpl {

  private HiveAccessType accessType = HiveAccessType.NONE;

  public ChiWenHiveAccessRequest() {
    super();
  }

  public ChiWenHiveAccessRequest(ChiWenHiveResource      resource,
      String                  user,
      Set<String> userGroups,
      String                  hiveOpTypeName,
      HiveAccessType          accessType,
      HiveAuthzContext context,
      HiveAuthzSessionContext sessionContext) {
    this.setResource(resource);
    this.setUser(user);
    this.setUserGroups(userGroups);
    this.setAccessTime(new Date());
    this.setAction(hiveOpTypeName);
    this.setHiveAccessType(accessType);

    if(context != null) {
      this.setRequestData(context.getCommandString());
      //this.setForwardedAddresses(context.getForwardedAddresses());
      this.setRemoteIPAddress(context.getIpAddress());
    }

    if(sessionContext != null) {
      this.setClientType(sessionContext.getClientType() == null ? null : sessionContext.getClientType().toString());
      this.setSessionId(sessionContext.getSessionString());
    }

  }

  public ChiWenHiveAccessRequest(ChiWenHiveResource      resource,
      String                  user,
      Set<String>             userGroups,
      HiveOperationType hiveOpType,
      HiveAccessType          accessType,
      HiveAuthzContext        context,
      HiveAuthzSessionContext sessionContext) {
    this(resource, user, userGroups, hiveOpType.name(), accessType, context, sessionContext);
  }

  public ChiWenHiveAccessRequest(ChiWenHiveResource resource, String user, Set<String> groups, HiveAuthzContext context, HiveAuthzSessionContext sessionContext, String clusterName) {
    this(resource, user, groups, "METADATA OPERATION", HiveAccessType.USE, context, sessionContext);
  }

  public HiveAccessType getHiveAccessType() {
    return accessType;
  }

  public void setHiveAccessType(HiveAccessType accessType) {
    this.accessType = accessType;

    if(accessType == HiveAccessType.USE) {
      this.setAccessType(ChiWenPolicyEngine.ANY_ACCESS);
    } else if(accessType == HiveAccessType.ADMIN) {
      this.setAccessType(ChiWenPolicyEngine.ADMIN_ACCESS);
    } else {
      this.setAccessType(accessType.name().toLowerCase());
    }
  }

  public ChiWenHiveAccessRequest copy() {
    ChiWenHiveAccessRequest ret = new ChiWenHiveAccessRequest();

    ret.setResource(getResource());
    ret.setAccessType(getAccessType());
    ret.setUser(getUser());
    ret.setUserGroups(getUserGroups());
    ret.setAccessTime(getAccessTime());
    ret.setAction(getAction());
    ret.setClientIPAddress(getClientIPAddress());
    ret.setRemoteIPAddress(getRemoteIPAddress());
    ret.setForwardedAddresses(getForwardedAddresses());
    ret.setRequestData(getRequestData());
    ret.setClientType(getClientType());
    ret.setSessionId(getSessionId());
    ret.setContext(ChiWenAccessRequestUtil.copyContext(getContext()));
    ret.accessType = accessType;
    ret.setClusterName(getClusterName());

    return ret;
  }
}
