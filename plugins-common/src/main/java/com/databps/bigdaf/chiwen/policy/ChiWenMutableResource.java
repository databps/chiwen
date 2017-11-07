package com.databps.bigdaf.chiwen.policy;

/**
 * @author merlin
 * @create 2017-08-13 下午12:56
 */
public interface ChiWenMutableResource extends ChiWenAccessResource {
  void setOwnerUser(String ownerUser);

  void setValue(String type, String value);
}
