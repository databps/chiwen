package com.databps.bigdaf.chiwen.audit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * AuditProviderFactory
 *
 * @author lgc
 * @create 2017-07-26 下午3:40
 */
public class AuditProviderFactory {

  private static final Log LOG = LogFactory.getLog(AuditProviderFactory.class);

  private static AuditProviderFactory sFactory;
  private static final int AUDIT_ASYNC_MAX_QUEUE_SIZE_DEFAULT = 10 * 1024;
  private static final int AUDIT_ASYNC_MAX_FLUSH_INTERVAL_DEFAULT = 5 * 1000;
  private AuditProvider mProvider = null;
  private boolean mInitDone = false;

  public boolean isInitDone() {
    return mInitDone;
  }

  private AuditProviderFactory() {
    LOG.info("AuditProviderFactory: creating..");
    mProvider = getDefaultProvider();
    mProvider.start();
  }

  public static AuditProviderFactory getInstance() {
    if (sFactory == null) {
      synchronized (AuditProviderFactory.class) {
        if (sFactory == null) {
          sFactory = new AuditProviderFactory();
        }
      }
    }

    return sFactory;
  }


  public static AuditProvider getAuditProvider() {

    return AuditProviderFactory.getInstance().getProvider();
  }

  public AuditProvider getProvider() {
    return mProvider;
  }

  private AuditProvider getDefaultProvider() {
    HttpAuditProvider httpAuditProvider = new HttpAuditProvider();
    return new AsyncAuditProvider("HttpAuditProvider", AUDIT_ASYNC_MAX_QUEUE_SIZE_DEFAULT,
        AUDIT_ASYNC_MAX_FLUSH_INTERVAL_DEFAULT, httpAuditProvider);
  }
}