package com.databps.bigdaf.chiwen.hdfsagent;

import com.databps.bigdaf.chiwen.common.ChiWenHadoopConstants;
import com.databps.bigdaf.chiwen.config.ChiWenConfiguration;
import com.databps.bigdaf.chiwen.policy.ChiWenBasePlugin;

/**
 * Created by lgc on 17-7-20.
 */


public class ChiWenHdfsPlugin extends ChiWenBasePlugin {

  private static String fileNameExtensionSeparator=".";

  private static boolean hadoopAuthEnabled = ChiWenHadoopConstants.CHIWEN_ADD_HDFS_PERMISSION_DEFAULT;

  public ChiWenHdfsPlugin(String serviceType, String PolicyName, String appId) {
    super(serviceType, PolicyName, appId);
  }


  public void init() {
    super.init();

    ChiWenHdfsPlugin.hadoopAuthEnabled = ChiWenConfiguration.getInstance()
        .getBoolean(ChiWenHadoopConstants.CHIWEN_ADD_HDFS_PERMISSION_PROP,
            ChiWenHadoopConstants.CHIWEN_ADD_HDFS_PERMISSION_DEFAULT);
  }

  public static boolean isHadoopAuthEnabled() {
    return ChiWenHdfsPlugin.hadoopAuthEnabled;
  }

  public static String getFileNameExtensionSeparator() {
    return ChiWenHdfsPlugin.fileNameExtensionSeparator;
  }
}

