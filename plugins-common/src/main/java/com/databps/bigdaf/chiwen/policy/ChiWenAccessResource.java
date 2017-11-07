package com.databps.bigdaf.chiwen.policy;

import java.util.Map;
import java.util.Set;

/**
 * @author merlin
 * @create 2017-08-13 下午12:56
 */

public interface ChiWenAccessResource {
  String RESOURCE_SEP = "/";
  String RESOURCE_NAME_VAL_SEP = "=";

  String getOwnerUser();

  boolean exists(String name);

  String getValue(String name);
  String getLeafName();


  Set<String> getKeys();


  String getAsString();

  String getCacheKey();

  Map<String, String> getAsMap();

  ChiWenAccessResource getReadOnlyCopy();
}