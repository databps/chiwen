package com.databps.bigdaf.chiwen.plugin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.security.HiveAuthenticationProvider;
import org.apache.hadoop.hive.ql.security.authorization.plugin.HiveAuthorizer;
import org.apache.hadoop.hive.ql.security.authorization.plugin.HiveAuthorizerFactory;
import org.apache.hadoop.hive.ql.security.authorization.plugin.HiveAuthzPluginException;
import org.apache.hadoop.hive.ql.security.authorization.plugin.HiveAuthzSessionContext;
import org.apache.hadoop.hive.ql.security.authorization.plugin.HiveMetastoreClientFactory;

/**
 * @author merlin
 * @create 2017-10-25 上午10:35
 */
public class ChiWenHiveAuthorizerFactoryShim implements HiveAuthorizerFactory {
  private static final Log LOG  = LogFactory.getLog(ChiWenHiveAuthorizerFactoryShim.class);

  @Override
  public HiveAuthorizer createHiveAuthorizer(HiveMetastoreClientFactory hiveMetastoreClientFactory,
      HiveConf hiveConf, HiveAuthenticationProvider hiveAuthenticationProvider,
      HiveAuthzSessionContext hiveAuthzSessionContext) throws HiveAuthzPluginException {



    return null;

  }
}








