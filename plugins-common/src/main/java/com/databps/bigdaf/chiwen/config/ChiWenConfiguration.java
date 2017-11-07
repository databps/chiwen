package com.databps.bigdaf.chiwen.config;

import com.databps.bigdaf.chiwen.adminclient.ChiWenAdminRESTClient;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;

/**
 * Created by lgc on 17-7-19.
 */
public class ChiWenConfiguration extends Configuration {

  private static final Log LOG = LogFactory.getLog(ChiWenAdminRESTClient.class);


  private static ChiWenConfiguration config = null;

  private ChiWenConfiguration() {
    super(false);
  }

  public void addResourcesForServiceType(String serviceName) {
    String securityCfg = "chiwen-" + serviceName + "-security.xml";
    addResourceIfReadable(securityCfg);
  }


  private void addResourceIfReadable(String aResourceName) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("==> addResourceIfReadable(" + aResourceName + ")");
    }

    String fName = getFileLocation(aResourceName);
    if (fName != null) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("<== addResourceIfReadable(" + aResourceName + "): resource file is " + fName);
      }

      File f = new File(fName);
      if (f.exists() && f.canRead()) {
        URL fUrl = null;
        try {
          fUrl = f.toURL();
          addResource(fUrl);
        } catch (MalformedURLException e) {
          if (LOG.isDebugEnabled()) {
            LOG.debug("Unable to find URL for the resource name [" + aResourceName
                + "]. Ignoring the resource:" + aResourceName);
          }
        }
      } else {
        if (LOG.isDebugEnabled()) {
          LOG.debug("<== addResourceIfReadable(" + aResourceName + "): resource not readable");
        }
      }
    } else {
      if (LOG.isDebugEnabled()) {
        LOG.debug("<== addResourceIfReadable(" + aResourceName
            + "): couldn't find resource file location");
      }
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("<== addResourceIfReadable(" + aResourceName + ")");
    }
  }


  public static ChiWenConfiguration getInstance() {
    if (config == null) {
      synchronized (ChiWenConfiguration.class) {
        ChiWenConfiguration temp = config;
        if (temp == null) {
          config = new ChiWenConfiguration();
        }
      }
    }
    return config;
  }

  private String getFileLocation(String fileName) {

    String ret = null;

    URL lurl = ChiWenConfiguration.class.getClassLoader().getResource(fileName);

    if (lurl == null) {
      lurl = ChiWenConfiguration.class.getClassLoader().getResource("/" + fileName);
    }

    if (lurl != null) {
      ret = lurl.getFile();
    }

    return ret;
  }

}
