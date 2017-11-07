package com.databps.bigdaf.chiwen.policy;

/**
 * ChiWenResourceMatcher
 *
 * @author lgc
 * @create 2017-08-18 上午10:44
 */
public interface ChiWenResourceMatcher {

  boolean isMatch(String resource, String ruleResource);

//  boolean isSingleAndExactMatch(String resource);
}