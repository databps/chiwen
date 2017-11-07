package com.databps.bigdaf.chiwen.exceptions;

import org.apache.hadoop.security.AccessControlException;

/**
 * Created by lgc on 17-7-20.
 */
public class ChiWenAccessControlException  extends AccessControlException {

  private static final long serialVersionUID = -4673975720243484927L;

  public ChiWenAccessControlException(String aMsg) {
    super(aMsg) ;
  }

}
