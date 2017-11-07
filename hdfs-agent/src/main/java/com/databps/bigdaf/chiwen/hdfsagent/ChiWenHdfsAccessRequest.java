package com.databps.bigdaf.chiwen.hdfsagent;

import com.databps.bigdaf.chiwen.audit.MultiDestAuditProvider;
import com.databps.bigdaf.chiwen.policy.ChiWenAccessRequestImpl;
import com.databps.bigdaf.chiwen.util.StringUtil;
import java.net.InetAddress;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.permission.FsAction;
import org.apache.hadoop.ipc.Server;

/**
 * Created by lgc on 17-7-20.
 */
public class ChiWenHdfsAccessRequest extends ChiWenAccessRequestImpl {
  private static final Log LOG = LogFactory.getLog(ChiWenHdfsAccessRequest.class);

  public ChiWenHdfsAccessRequest(String path, String pathOwner, FsAction access, String accessType, String user, Set<String> groups) {
    super.setPath(path);
    super.setAccessType(accessType);
    super.setUser(user);
    super.setUserGroups(groups);
    super.setAccessTime(StringUtil.getUTCDate());
    super.setClientIPAddress(getRemoteIp());
    super.setAction(access.toString());
  }

  private static String getRemoteIp() {
    String ret = null ;
    InetAddress ip = Server.getRemoteIp() ;
    if (ip != null) {
      ret = ip.getHostAddress();
        LOG.info("==> ip.toString()>>>>>>>>>>>>>>>>>>>>>:"+ip.toString());
    }
    return ret;
  }
}
