package com.databps.bigdaf.chiwen.policy;


import com.databps.bigdaf.chiwen.adminclient.ChiWenAdminClient;
import com.databps.bigdaf.chiwen.model.ChiWenPolicyHbaseVo;
import com.databps.bigdaf.chiwen.model.ChiWenPolicyPluginVo;
import com.databps.bigdaf.chiwen.util.ChiWenServiceNotFoundException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by lgc on 17-7-18.
 */
public class PolicyRefresher extends Thread {

  private static final Log LOG = LogFactory.getLog(PolicyRefresher.class);
  private ChiWenBasePlugin plugIn = null;
  private String serviceType = null;
  private String serviceId = null;
  private String policyName = null;
  private ChiWenAdminClient chiwenAdmin = null;
  private boolean policiesSetInPlugin;


  public ChiWenAdminClient getChiwenAdmin() {
    return chiwenAdmin;
  }

  public void setChiwenAdmin(ChiWenAdminClient chiwenAdmin) {
    this.chiwenAdmin = chiwenAdmin;
  }

  private long pollingIntervalMs = 4 * 1000;
  private String chiWenUUID = "0";
  private Gson gson = null;
  private final String cacheFileName;
  private final String cacheDir;
  private long lastActivationTimeInMillis;
  private boolean serviceDefSetInPlugin;
  private final boolean disableCacheIfServiceNotFound;


  public PolicyRefresher(ChiWenBasePlugin policyEngine, String serviceType, String appId, ChiWenAdminClient chiwenAdmin, long pollingIntervalMs, String cacheDir) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("==> PolicyRefresher(policyName=" + policyName + ").PolicyRefresher()");
    }

    this.plugIn = policyEngine;
    this.serviceType = serviceType;
    this.chiwenAdmin = chiwenAdmin;
    this.pollingIntervalMs = pollingIntervalMs;
    chiWenUUID = getChiWenUUID();
    serviceId=chiWenUUID;

    String cacheFilename = String.format("%s_%s.json", appId, serviceType);
    cacheFilename = cacheFilename.replace(File.separatorChar, '_');
    cacheFilename = cacheFilename.replace(File.pathSeparatorChar, '_');

    this.cacheFileName = cacheFilename;
    this.cacheDir = cacheDir;
    disableCacheIfServiceNotFound=true;
    try {
      this.gson = new GsonBuilder().setDateFormat("yyyyMMddHHmmssSSS").setPrettyPrinting()
          .create();
    } catch (Throwable excp) {
      LOG.fatal("PolicyRefresher(): failed to create GsonBuilder object", excp);
    }
    if (LOG.isDebugEnabled()) {
      LOG.debug("<== PolicyRefresher(policyName=" + policyName + ").PolicyRefresher()");
    }
  }

  public ChiWenAdminClient getChiWenAdminClient() {
    return chiwenAdmin;
  }


  private void switchPolicy(){
    if(serviceType.equals("hdfs")){
      loadPolicy();
    }else if(serviceType.equals("hbase")|| serviceType.equals("hive")){
      loadHbasePolicy();
    }
  }


  public void run() {

    if(LOG.isDebugEnabled()) {
      LOG.debug("==> PolicyRefresher(serviceName=" + serviceId + ").run()");
    }

    while(true) {
      switchPolicy();

      try {
        Thread.sleep(pollingIntervalMs);
      } catch(InterruptedException excp) {
        LOG.info("PolicyRefresher(serviceName=" + serviceId + ").run(): interrupted! Exiting thread", excp);
        break;
      }
    }

    if(LOG.isDebugEnabled()) {
      LOG.debug("<== PolicyRefresher(serviceName=" + serviceId + ").run()");
    }
  }

  private void loadHbasePolicy() {

    if (LOG.isDebugEnabled()) {
      LOG.debug("==> PolicyRefresher(serviceName=" + serviceId + ").loadPolicy()");
    }

    try {
      //load policy from PolicyAdmin
      ChiWenPolicyHbaseVo svcPolicies = loadHbasePolicyfromPolicyAdmin();

      if (svcPolicies == null) {
        //if Policy fetch from Policy Admin Fails, load from cache09iujhu90
        if (!policiesSetInPlugin) {
          svcPolicies = loadHbaseFromCache();
        }
      } else {
        saveHbaseToCache(svcPolicies);
      }

      if (svcPolicies != null) {
        plugIn.setHbasePolicies(svcPolicies);
        policiesSetInPlugin = true;
        setLastActivationTimeInMillis(System.currentTimeMillis());
      } else {
        if (!policiesSetInPlugin && !serviceDefSetInPlugin) {
          plugIn.setPolicies(null);
          serviceDefSetInPlugin = true;
        }
      }
    } catch (ChiWenServiceNotFoundException snfe) {
      if (disableCacheIfServiceNotFound) {
        disableCache();
        plugIn.setPolicies(null);
        setLastActivationTimeInMillis(System.currentTimeMillis());
        serviceDefSetInPlugin = true;
      }
    } catch (Exception excp) {
      LOG.error("Encountered unexpected exception, ignoring..", excp);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("<== PolicyRefresher(serviceName=" + serviceId + ").loadPolicy()");
    }
  }


  private void loadPolicy() {

    if (LOG.isDebugEnabled()) {
      LOG.debug("==> PolicyRefresher(serviceName=" + serviceId + ").loadPolicy()");
    }

    try {
      //load policy from PolicyAdmin
      ChiWenPolicyPluginVo svcPolicies = loadHdfsPolicyfromPolicyAdmin();

      if (svcPolicies == null) {
        //if Policy fetch from Policy Admin Fails, load from cache09iujhu90
        if (!policiesSetInPlugin) {
          svcPolicies = loadFromCache();
        }
      } else {
        saveToCache(svcPolicies);
      }

      if (svcPolicies != null) {
        plugIn.setPolicies(svcPolicies);
        policiesSetInPlugin = true;
        setLastActivationTimeInMillis(System.currentTimeMillis());
      } else {
        if (!policiesSetInPlugin && !serviceDefSetInPlugin) {
          plugIn.setPolicies(null);
          serviceDefSetInPlugin = true;
        }
      }
    } catch (ChiWenServiceNotFoundException snfe) {
      if (disableCacheIfServiceNotFound) {
        disableCache();
        plugIn.setPolicies(null);
        setLastActivationTimeInMillis(System.currentTimeMillis());
        serviceDefSetInPlugin = true;
      }
    } catch (Exception excp) {
      LOG.error("Encountered unexpected exception, ignoring..", excp);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("<== PolicyRefresher(serviceName=" + serviceId + ").loadPolicy()");
    }
  }

  public void setLastActivationTimeInMillis(long lastActivationTimeInMillis) {
    this.lastActivationTimeInMillis = lastActivationTimeInMillis;
  }

  private ChiWenPolicyPluginVo loadHdfsPolicyfromPolicyAdmin() throws ChiWenServiceNotFoundException {

    if (LOG.isDebugEnabled()) {
      LOG.debug("==> PolicyRefresher(serviceId=" + serviceId + ").loadHdfsPolicyfromPolicyAdmin()");
    }

    ChiWenPolicyPluginVo svcPolicies = null;

    try {
      svcPolicies = chiwenAdmin.getHdfsServicePoliciesIfUpdated(policyName, chiWenUUID);

      boolean isUpdated = svcPolicies != null;

      if (isUpdated) {

        if (!StringUtils.equals(serviceId, svcPolicies.getServiceId())) {
          LOG.warn(
              "PolicyRefresher(serviceId=" + serviceId + "): ignoring unexpected serviceId '"
                  + svcPolicies.getServiceId() + "' in service-store");

          svcPolicies.setServiceId(serviceId);
        }

        LOG.info("PolicyRefresher(serviceId=" + serviceId + "): found updated version. ");

      } else {
        if (LOG.isDebugEnabled()) {
          LOG.debug("PolicyRefresher(serviceId=" + serviceId + ").run(): no update found. ");
        }
      }
    } catch (ChiWenServiceNotFoundException snfe) {
      LOG.error("PolicyRefresher(serviceId=" + serviceId
          + "): failed to find service. Will clean up local cache of policies ", snfe);
      throw snfe;
    } catch (Exception excp) {
      LOG.error("PolicyRefresher(serviceId=" + serviceId
              + "): failed to refresh policies. Will continue to use last known version of policies ",
          excp);
      svcPolicies = null;
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("<== PolicyRefresher(serviceId=" + serviceId + ").loadPolicyfromPolicyAdmin()");
    }

    return svcPolicies;
  }

  //hbase
  private ChiWenPolicyHbaseVo loadHbasePolicyfromPolicyAdmin() throws ChiWenServiceNotFoundException {

    if (LOG.isDebugEnabled()) {
      LOG.debug("==> PolicyRefresher(serviceId=" + serviceId + ").loadHbasePolicyfromPolicyAdmin()");
    }

    ChiWenPolicyHbaseVo svcPolicies = null;

    try {
      svcPolicies = chiwenAdmin.getHbaseServicePoliciesIfUpdated(policyName, chiWenUUID);

      boolean isUpdated = svcPolicies != null;

      if (isUpdated) {

        if (!StringUtils.equals(serviceId, svcPolicies.getServiceId())) {
          LOG.warn(
              "PolicyRefresher(serviceId=" + serviceId + "): ignoring unexpected serviceId '"
                  + svcPolicies.getServiceId() + "' in service-store");

          svcPolicies.setServiceId(serviceId);
        }

        LOG.info("PolicyRefresher(serviceId=" + serviceId + "): found updated version. ");

      } else {
        if (LOG.isDebugEnabled()) {
          LOG.debug("PolicyRefresher(serviceId=" + serviceId + ").run(): no update found. ");
        }
      }
    } catch (ChiWenServiceNotFoundException snfe) {
      LOG.error("PolicyRefresher(serviceId=" + serviceId
          + "): failed to find service. Will clean up local cache of policies ", snfe);
      throw snfe;
    } catch (Exception excp) {
      LOG.error("PolicyRefresher(serviceId=" + serviceId
              + "): failed to refresh policies. Will continue to use last known version of policies ",
          excp);
      svcPolicies = null;
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("<== PolicyRefresher(serviceId=" + serviceId + ").loadPolicyfromPolicyAdmin()");
    }

    return svcPolicies;
  }


  private ChiWenPolicyHbaseVo loadHbaseFromCache() {

    ChiWenPolicyHbaseVo policies = null;

    if (LOG.isDebugEnabled()) {
      LOG.debug("==> PolicyRefresher(serviceId=" + serviceId + ").loadHbaseFromCache()");
    }
    File cacheFile = cacheDir == null ? null : new File(cacheDir + File.separator + cacheFileName);

    if (cacheFile != null && cacheFile.isFile() && cacheFile.canRead()) {
      Reader reader = null;
      try {
        reader = new FileReader(cacheFile);

        policies = gson.fromJson(reader, ChiWenPolicyHbaseVo.class);

        if (policies != null) {
          if (!StringUtils.equals(serviceId, policies.getServiceId())) {
            LOG.warn("ignoring unexpected serviceId '" + policies.getServiceId()
                + "' in cache file '" + cacheFile.getAbsolutePath() + "'");

            policies.setServiceId(serviceId);
          }

        }
      } catch (Exception excp) {
        LOG.error("failed to load policies from cache file " + cacheFile.getAbsolutePath(), excp);
      } finally {
        if (reader != null) {
          try {
            reader.close();
          } catch (Exception excp) {
            LOG.error("error while closing opened cache file " + cacheFile.getAbsolutePath(), excp);
          }
        }
      }
    } else {
      getChiWenUUID();
      File paCacheFile = cacheFile.getParentFile();
      try {
        if (paCacheFile != null && !paCacheFile.exists()) {
          paCacheFile.mkdirs();
        }
        cacheFile.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
      LOG.warn("cache file does not exist or not readable '" + (cacheFile == null ? null
          : cacheFile.getAbsolutePath()) + "'");
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("<== PolicyRefresher(serviceId=" + serviceId + ").loadHbaseFromCache()");
    }

    return policies;
  }


  private ChiWenPolicyPluginVo loadFromCache() {

    ChiWenPolicyPluginVo policies = null;

    if (LOG.isDebugEnabled()) {
      LOG.debug("==> PolicyRefresher(serviceId=" + serviceId + ").loadFromCache()");
    }
    File cacheFile = cacheDir == null ? null : new File(cacheDir + File.separator + cacheFileName);

    if (cacheFile != null && cacheFile.isFile() && cacheFile.canRead()) {
      Reader reader = null;
      try {
        reader = new FileReader(cacheFile);

        policies = gson.fromJson(reader, ChiWenPolicyPluginVo.class);

        if (policies != null) {
          if (!StringUtils.equals(serviceId, policies.getServiceId())) {
            LOG.warn("ignoring unexpected serviceId '" + policies.getServiceId()
                + "' in cache file '" + cacheFile.getAbsolutePath() + "'");

            policies.setServiceId(serviceId);
          }

        }
      } catch (Exception excp) {
        LOG.error("failed to load policies from cache file " + cacheFile.getAbsolutePath(), excp);
      } finally {
        if (reader != null) {
          try {
            reader.close();
          } catch (Exception excp) {
            LOG.error("error while closing opened cache file " + cacheFile.getAbsolutePath(), excp);
          }
        }
      }
    } else {
      getChiWenUUID();
      File paCacheFile = cacheFile.getParentFile();
      try {
        if (paCacheFile != null && !paCacheFile.exists()) {
          paCacheFile.mkdirs();
        }
        cacheFile.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
      LOG.warn("cache file does not exist or not readable '" + (cacheFile == null ? null
          : cacheFile.getAbsolutePath()) + "'");
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("<== PolicyRefresher(serviceId=" + serviceId + ").loadFromCache()");
    }

    return policies;
  }


  private void saveHbaseToCache(ChiWenPolicyHbaseVo policies) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("==> PolicyRefresher(serviceId=" + serviceId + ").saveHbaseToCache()");
    }

    if (policies != null) {
      File cacheFile = null;
      if (cacheDir != null) {
        // Create the cacheDir if it doesn't already exist
        File cacheDirTmp = new File(cacheDir);
        if (cacheDirTmp.exists()) {
          cacheFile = new File(cacheDir + File.separator + cacheFileName);
        } else {
          try {
            cacheDirTmp.mkdirs();
            cacheFile = new File(cacheDir + File.separator + cacheFileName);
          } catch (SecurityException ex) {
            LOG.error("Cannot create cache directory", ex);
          }
        }
      }

      if (cacheFile != null) {

        Writer writer = null;

        try {
          writer = new FileWriter(cacheFile);

          gson.toJson(policies, writer);
        } catch (Exception excp) {
          LOG.error("failed to save policies to cache file '" + cacheFile.getAbsolutePath() + "'",
              excp);
        } finally {
          if (writer != null) {
            try {
              writer.close();
            } catch (Exception excp) {
              LOG.error(
                  "error while closing opened cache file '" + cacheFile.getAbsolutePath() + "'",
                  excp);
            }
          }
        }

      }
    } else {
      LOG.info("policies is null. Nothing to save in cache");
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("<== PolicyRefresher(serviceId=" + serviceId + ").saveToCache()");
    }
  }



  private void saveToCache(ChiWenPolicyPluginVo policies) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("==> PolicyRefresher(serviceId=" + serviceId + ").saveToCache()");
    }

    if (policies != null) {
      File cacheFile = null;
      if (cacheDir != null) {
        // Create the cacheDir if it doesn't already exist
        File cacheDirTmp = new File(cacheDir);
        if (cacheDirTmp.exists()) {
          cacheFile = new File(cacheDir + File.separator + cacheFileName);
        } else {
          try {
            cacheDirTmp.mkdirs();
            cacheFile = new File(cacheDir + File.separator + cacheFileName);
          } catch (SecurityException ex) {
            LOG.error("Cannot create cache directory", ex);
          }
        }
      }

      if (cacheFile != null) {

        Writer writer = null;

        try {
          writer = new FileWriter(cacheFile);

          gson.toJson(policies, writer);
        } catch (Exception excp) {
          LOG.error("failed to save policies to cache file '" + cacheFile.getAbsolutePath() + "'",
              excp);
        } finally {
          if (writer != null) {
            try {
              writer.close();
            } catch (Exception excp) {
              LOG.error(
                  "error while closing opened cache file '" + cacheFile.getAbsolutePath() + "'",
                  excp);
            }
          }
        }

      }
    } else {
      LOG.info("policies is null. Nothing to save in cache");
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("<== PolicyRefresher(serviceId=" + serviceId + ").saveToCache()");
    }
  }

  public void switchResult(){
    if(serviceType.equals("hdfds")){
      loadPolicy();
    }else if(serviceType.equals("hbase")||serviceType.equals("hive")){
      loadHbasePolicy();
    }
  }

  public void startRefresher() {
    switchResult();

    super.start();
  }


  public void stopRefresher() {
    super.interrupt();

    try {
      super.join();
    } catch (InterruptedException excp) {
      LOG.warn(
          "PolicyRefresher(policyName=" + policyName + "): error while waiting for thread to exit",
          excp);
    }
  }

  private String getChiWenUUID() {
    String retStr = null;
    String fileStr =
        System.getenv("HOME") + File.separator + "com/databps/bigdaf/chiwen" + File.separator
            + ".HBASEUUIDfile.txt";
    File file = new File(fileStr);
    File parentFile = file.getParentFile();
    if (parentFile != null && !parentFile.exists()) {
      parentFile.mkdirs();
    }
    if (file != null && !file.exists()) {
      try {
        file.createNewFile();
      } catch (IOException e) {
        LOG.debug(
            "getChiWenUUID: create .HBASEUUIDfile.txt error! ");
        e.printStackTrace();
      }
    }
    FileReader reader = null;
    try {
      reader = new FileReader(file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    BufferedReader br = new BufferedReader(reader);
    try {
      retStr = br.readLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (null == retStr || "".equals(retStr)) {
      retStr = UUID.randomUUID().toString();
      try {
        FileWriter writer = new FileWriter(file);
        writer.write(retStr);
        writer.close();
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
    return retStr;
  }





  private void disableCache() {
    if (LOG.isDebugEnabled()) {
      LOG.debug("==> PolicyRefresher.disableCache(serviceName=" + serviceId + ")");
    }

    File cacheFile = cacheDir == null ? null : new File(cacheDir + File.separator + cacheFileName);

    if (cacheFile != null && cacheFile.isFile() && cacheFile.canRead()) {
      LOG.warn("Cleaning up local cache");
      String renamedCacheFile = cacheFile.getAbsolutePath() + "_" + System.currentTimeMillis();
      if (!cacheFile.renameTo(new File(renamedCacheFile))) {
        LOG.error("Failed to move " + cacheFile.getAbsolutePath() + " to " + renamedCacheFile);
      } else {
        LOG.warn("Moved " + cacheFile.getAbsolutePath() + " to " + renamedCacheFile);
      }
    } else {
      if (LOG.isDebugEnabled()) {
        LOG.debug("No local policy cache found. No need to disable it!");
      }
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("<== PolicyRefresher.disableCache(serviceId=" + serviceId + ")");
    }
  }
}
