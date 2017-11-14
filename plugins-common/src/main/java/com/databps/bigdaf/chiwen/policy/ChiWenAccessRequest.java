package com.databps.bigdaf.chiwen.policy;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lgc on 17-7-20.
 */
public interface ChiWenAccessRequest {

  ChiWenAccessResource getResource();


  List<String> getForwardedAddresses();

  public String getPath();

  String getAccessType();

  boolean isAccessTypeAny();

  String getUser();

  Set<String> getUserGroups();

  Date getAccessTime();

  String getClientIPAddress();

  String getClientType();

  boolean isAccessTypeDelegatedAdmin();

  String getAction();

  Map<String, Object> getContext();
  String getRequestData();
  String getClusterName();
  ResourceMatchingScope getResourceMatchingScope();
  enum ResourceMatchingScope {SELF, SELF_OR_DESCENDANTS}

  ChiWenAccessRequest getReadOnlyCopy();

  String getSessionId();

}
