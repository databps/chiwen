package com.databps.bigdaf.chiwen.policy;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

/**
 * @author merlin
 * @create 2017-08-13 下午12:59
 */
public class ChiWenAccessResourceReadOnly implements ChiWenAccessResource{

  private final ChiWenAccessResource source;
  private final Set<String> keys;
  private final Map<String, String> map;

  public ChiWenAccessResourceReadOnly(final ChiWenAccessResource source) {
    this.source = source;

    // Cached here for reducing access overhead
    Set<String> sourceKeys = source.getKeys();

    if (CollectionUtils.isEmpty(sourceKeys)) {
      sourceKeys = new HashSet<String>();
    }
    this.keys = Collections.unmodifiableSet(sourceKeys);

    Map<String, String> sourceMap = source.getAsMap();

    if (MapUtils.isEmpty(sourceMap)) {
      sourceMap = new HashMap<String, String>();
    }
    this.map = Collections.unmodifiableMap(sourceMap);
  }

  public String getOwnerUser() { return source.getOwnerUser(); }

  public boolean exists(String name) { return source.exists(name); }

  public String getValue(String name) { return source.getValue(name); }

  @Override
  public String getLeafName() {
    return null;
  }


  public Set<String> getKeys() { return keys; }


  public String getAsString() { return source.getAsString(); }

  public String getCacheKey() { return source.getCacheKey(); }

  public Map<String, String> getAsMap() { return map; }

  public ChiWenAccessResource getReadOnlyCopy() { return this; }
}
