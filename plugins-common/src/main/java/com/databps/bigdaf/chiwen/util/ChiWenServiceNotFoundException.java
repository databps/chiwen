package com.databps.bigdaf.chiwen.util;

import org.apache.commons.lang.StringUtils;

/**
 * @author merlin
 * @create 2017-10-25 下午2:33
 */
public class ChiWenServiceNotFoundException extends Exception {

  static private final String formatString = "\"ChiWen_ERROR_SERVICE_NOT_FOUND: ServiceName=%s\"";
  private static final long serialVersionUID = -1924466335281990128L;

  public ChiWenServiceNotFoundException(String serviceName) {
    super(serviceName);
  }

  public static final String buildExceptionMsg(String serviceName) {
    return String.format(formatString, serviceName);
  }

  public static final void throwExceptionIfServiceNotFound(String serviceName, String exceptionMsg)
      throws ChiWenServiceNotFoundException {
    String expectedExceptionMsg = buildExceptionMsg(serviceName);
    if (StringUtils.startsWith(exceptionMsg, expectedExceptionMsg)) {
      throw new ChiWenServiceNotFoundException(serviceName);
    }
  }
}
