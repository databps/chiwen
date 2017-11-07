package com.databps.bigdaf.chiwen.audit;

import java.util.Properties;

/**
 * Created by lgc on 17-7-25.
 */
public interface AuditProvider {

  public boolean log(AuthzAuditEvent event);

  public void init(Properties props);

  public void start();

  public void stop();

  public void flush();
}
