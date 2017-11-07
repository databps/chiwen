package com.databps.bigdaf.chiwen.audit;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * MultiDestAuditProvider
 *
 * @author lgc
 * @create 2017-07-25 下午8:18
 */
public class MultiDestAuditProvider extends BaseAuditProvider {

  private static final Log LOG = LogFactory.getLog(MultiDestAuditProvider.class);


  protected List<AuditProvider> mProviders = new ArrayList<AuditProvider>();

  @Override
  public void init(Properties props) {
    LOG.info("MultiDestAuditProvider.init()");

    super.init(props);

    for (AuditProvider provider : mProviders) {
      try {
        provider.init(props);
      } catch (Throwable excp) {
        LOG.info(
            "MultiDestAuditProvider.init(): failed " + provider.getClass().getCanonicalName() + ")",
            excp);
      }
    }
  }

  public void addAuditProvider(AuditProvider provider) {
    if(provider != null) {
      LOG.info("MultiDestAuditProvider.addAuditProvider(providerType=" + provider.getClass().getCanonicalName() + ")");

      mProviders.add(provider);
    }
  }

  public void addAuditProviders(List<AuditProvider> providers) {
    if(providers != null) {
      for(AuditProvider provider : providers) {
        addAuditProvider(provider);
      }
    }
  }


  @Override
  public boolean log(AuthzAuditEvent event) {
    for(AuditProvider provider : mProviders) {
      try {
        provider.log(event);
      } catch(Throwable excp) {
        logFailedEvent(event, excp);
      }
    }
    return true;
  }

  @Override
  public void start() {
    for(AuditProvider provider : mProviders) {
      try {
        provider.start();
      } catch(Throwable excp) {
        LOG.error("AsyncAuditProvider.start(): failed for provider { " + provider.getClass().getName() + " }", excp);
      }
    }
  }

  @Override
  public void stop() {
    for(AuditProvider provider : mProviders) {
      try {
        provider.stop();
      } catch(Throwable excp) {
        LOG.error("AsyncAuditProvider.stop(): failed for provider { " + provider.getClass().getName() + " }", excp);
      }
    }
  }

  @Override
  public void flush() {
    for(AuditProvider provider : mProviders) {
      try {
        provider.flush();
      } catch(Throwable excp) {
        LOG.error("AsyncAuditProvider.flush(): failed for provider { " + provider.getClass().getName() + " }", excp);
      }
    }
  }
}