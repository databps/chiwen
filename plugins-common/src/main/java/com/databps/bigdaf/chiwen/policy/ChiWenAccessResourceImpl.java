package com.databps.bigdaf.chiwen.policy;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.ObjectUtils;

/**
 * @author merlin
 * @create 2017-08-13 下午12:54
 */
public class ChiWenAccessResourceImpl implements ChiWenMutableResource {
  private String              ownerUser;
  private Map<String, String> elements;
  private String              stringifiedValue;
  private String              stringifiedCacheKeyValue;
  private String              leafName;


  public ChiWenAccessResourceImpl() {
    this(null, null);
  }

  public ChiWenAccessResourceImpl(Map<String, String> elements) {
    this(elements, null);
  }

  public ChiWenAccessResourceImpl(Map<String, String> elements, String ownerUser) {
    this.elements  = elements;
    this.ownerUser = ownerUser;
  }

  @Override
  public String getOwnerUser() {
    return ownerUser;
  }

  @Override
  public boolean exists(String name) {
    return elements != null && elements.containsKey(name);
  }

  @Override
  public String getValue(String name) {
    String ret = null;

    if(elements != null && elements.containsKey(name)) {
      ret = elements.get(name);
    }

    return ret;
  }

  @Override
  public String getLeafName() {
    String ret = leafName;

    if(elements != null) {
      StringBuilder sb = new StringBuilder();
      for (String key : elements.keySet()) {


        if(sb.length() > 0) {
          sb.append(RESOURCE_SEP);
        }

        sb.append(key);
        if(sb.length() > 0) {
          ret = leafName = sb.toString();
        }
      }



    }

    return ret;
  }

  @Override
  public Set<String> getKeys() {
    Set<String> ret = null;

    if(elements != null) {
      ret = elements.keySet();
    }

    return ret;
  }

  @Override
  public void setOwnerUser(String ownerUser) {
    this.ownerUser = ownerUser;
  }

  @Override
  public void setValue(String name, String value) {
    if(value == null) {
      if(elements != null) {
        elements.remove(name);

        if(elements.isEmpty()) {
          elements = null;
        }
      }
    } else {
      if(elements == null) {
        elements = new HashMap<String, String>();
      }
      elements.put(name, value);
    }

    // reset, so that these will be computed again with updated elements
    stringifiedValue = stringifiedCacheKeyValue = null;
  }




  @Override
  public String getAsString() {
    String ret = stringifiedValue;

    if(elements != null) {
      StringBuilder sb = new StringBuilder();

      for (String value : elements.values()) {


        if(sb.length() > 0) {
          sb.append(RESOURCE_SEP);
        }

        sb.append(value);
      }

      if(sb.length() > 0) {
        ret = stringifiedValue = sb.toString();
      }
    }

    return ret;
  }

  @Override
  public String getCacheKey() {
    String ret = stringifiedCacheKeyValue;

    if(ret == null) {

    }

    return ret;
  }

  @Override
  public Map<String, String> getAsMap() {
    if(elements ==null)
      return null;
    return Collections.unmodifiableMap(elements);
  }

  @Override
  public ChiWenAccessResource getReadOnlyCopy() {
    return new ChiWenAccessResourceReadOnly(this);
  }

  @Override
  public boolean equals(Object obj) {
    if(obj == null || !(obj instanceof ChiWenAccessResourceImpl)) {
      return false;
    }

    if(this == obj) {
      return true;
    }

    ChiWenAccessResourceImpl other = (ChiWenAccessResourceImpl) obj;

    return ObjectUtils.equals(ownerUser, other.ownerUser) &&
        ObjectUtils.equals(elements, other.elements);
  }

  @Override
  public int hashCode() {
    int ret = 7;

    ret = 31 * ret + ObjectUtils.hashCode(ownerUser);
    ret = 31 * ret + ObjectUtils.hashCode(elements);

    return ret;
  }

  @Override
  public String toString( ) {
    StringBuilder sb = new StringBuilder();

    toString(sb);

    return sb.toString();
  }

  public StringBuilder toString(StringBuilder sb) {
    sb.append("ChiWenResourceImpl={");

    sb.append("ownerUser={").append(ownerUser).append("} ");

    sb.append("elements={");
    if(elements != null) {
      for(Map.Entry<String, String> e : elements.entrySet()) {
        sb.append(e.getKey()).append("=").append(e.getValue()).append("; ");
      }
    }
    sb.append("} ");

    sb.append("}");

    return sb;
  }
}