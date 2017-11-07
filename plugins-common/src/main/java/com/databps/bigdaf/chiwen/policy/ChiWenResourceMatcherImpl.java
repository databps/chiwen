package com.databps.bigdaf.chiwen.policy;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ChiWenResourceMatcherImpl
 *
 * @author lgc
 * @create 2017-08-18 上午10:45
 */
public class ChiWenResourceMatcherImpl implements ChiWenResourceMatcher{
  private static final Log LOG = LogFactory.getLog(ChiWenResourceMatcherImpl.class);


  @Override
  public boolean isMatch(String resource,String ruleResource) {

    if (LOG.isDebugEnabled()) {
      LOG.debug("==> ChiWenPathResourceMatcher.isMatch(" + resource + ")");
    }
    boolean ret = false;
    if (resource != null) {
      resource = resource.toLowerCase();
      ret = StringUtils.startsWith(resource, ruleResource);
      if (!ret) {
            ret = isRecursiveWildCardMatch(resource, ruleResource) ;
      }
        else {
          ret = StringUtils.equals(resource, ruleResource);

          if(! ret) {
            ret = FilenameUtils.wildcardMatch(resource, ruleResource);
          }
        }
      if (LOG.isDebugEnabled()) {
        LOG.debug("<== ChiWenPathResourceMatcher.isMatch(" + resource + "): " + ret);
      }
      return ret;
    }
    return ret;
  }


  private static boolean isRecursiveWildCardMatch(String pathToCheck, String wildcardPath) {
    if(LOG.isDebugEnabled()) {
      LOG.debug("==> ChiWenPathResourceMatcher.isRecursiveWildCardMatch(" + pathToCheck + ", " + wildcardPath + ")");
    }

    boolean ret = false;

    if (pathToCheck != null) {
      StringBuilder sb = new StringBuilder() ;

      for(String p : pathToCheck.split(org.apache.hadoop.fs.Path.SEPARATOR) ) {
        sb.append(p);

        boolean matchFound = FilenameUtils.wildcardMatch(sb.toString(), wildcardPath) ;

        if (matchFound) {
          ret = true ;

          break;
        }

        sb.append(org.apache.hadoop.fs.Path.SEPARATOR) ;
      }

      sb = null;
    }

    if(LOG.isDebugEnabled()) {
      LOG.debug("<== ChiWenPathResourceMatcher.isRecursiveWildCardMatch(" + pathToCheck + ", " + wildcardPath + "): " + ret);
    }

    return ret;
  }
}