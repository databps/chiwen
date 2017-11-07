package com.databps.bigdaf.chiwen.hdfsagent;

import com.databps.bigdaf.chiwen.audit.ChiWenHdfsAuditHandler;
import com.databps.bigdaf.chiwen.common.ChiWenHadoopConstants;
import com.databps.bigdaf.chiwen.exceptions.ChiWenAccessControlException;
import com.databps.bigdaf.chiwen.policy.ChiWenAccessResult;
import com.google.common.collect.Sets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.permission.FsAction;
import org.apache.hadoop.hdfs.server.namenode.INodeAttributes;
import org.apache.hadoop.hdfs.server.namenode.INodesInPath;
import org.apache.hadoop.security.UserGroupInformation;

/**
 * Created by lgc on 17-7-20.
 */
public class ChiWenFSPermissionChecker {

  private static final Log LOG = LogFactory.getLog(ChiWenFSPermissionChecker.class);

  private static Map<FsAction, Set<String>> access2ActionListMapper = null;

  static {
    access2ActionListMapper = new HashMap<FsAction, Set<String>>();

    access2ActionListMapper.put(FsAction.NONE, new HashSet<String>());
    access2ActionListMapper.put(FsAction.ALL, Sets
        .newHashSet(ChiWenHadoopConstants.READ_ACCCESS_TYPE,
            ChiWenHadoopConstants.WRITE_ACCCESS_TYPE, ChiWenHadoopConstants.EXECUTE_ACCCESS_TYPE));
    access2ActionListMapper
        .put(FsAction.READ, Sets.newHashSet(ChiWenHadoopConstants.READ_ACCCESS_TYPE));
    access2ActionListMapper.put(FsAction.READ_WRITE,
        Sets.newHashSet(ChiWenHadoopConstants.READ_ACCCESS_TYPE,
            ChiWenHadoopConstants.WRITE_ACCCESS_TYPE));
    access2ActionListMapper.put(FsAction.READ_EXECUTE,
        Sets.newHashSet(ChiWenHadoopConstants.READ_ACCCESS_TYPE,
            ChiWenHadoopConstants.EXECUTE_ACCCESS_TYPE));
    access2ActionListMapper
        .put(FsAction.WRITE, Sets.newHashSet(ChiWenHadoopConstants.WRITE_ACCCESS_TYPE));
    access2ActionListMapper.put(FsAction.WRITE_EXECUTE,
        Sets.newHashSet(ChiWenHadoopConstants.WRITE_ACCCESS_TYPE,
            ChiWenHadoopConstants.EXECUTE_ACCCESS_TYPE));
    access2ActionListMapper
        .put(FsAction.EXECUTE, Sets.newHashSet(ChiWenHadoopConstants.EXECUTE_ACCCESS_TYPE));
  }


  private static volatile ChiWenHdfsPlugin chiWenPlugin = null;
  private static ThreadLocal<ChiWenHdfsAuditHandler> currentAuditHandler = new ThreadLocal<ChiWenHdfsAuditHandler>();

  public static boolean check(UserGroupInformation ugi, INodeAttributes inode,String pathStr, FsAction access)
//  check(INodeAttributes inode, String path, FsAction access
      throws ChiWenAccessControlException {
    boolean isHadoopAuthEnable=true;

    String isHadoopAuthEnableStr =System.getenv("IS_HADOOP_AUTH_ENABLED") ;

    if(isHadoopAuthEnableStr!=null&&!"".equals(isHadoopAuthEnableStr)){
      isHadoopAuthEnable =Boolean.valueOf(isHadoopAuthEnableStr);
    }

    if (ugi == null || inode == null || access == null) {
      return false;
    }
    if (LOG.isDebugEnabled()) {
          LOG.debug("==> ChiWenFSPermissionChecker.check() begin");
        }
    String path = pathStr;
    String pathOwner = inode.getUserName();
    String user = ugi.getShortUserName();
    Set<String> groups = Sets.newHashSet(ugi.getGroupNames());

    boolean accessGranted = AuthorizeAccessForUser(path, pathOwner, access, user, groups);

    if (!accessGranted && !isHadoopAuthEnable) {
      String inodeInfo = (inode.isDirectory() ? "directory" : "file") + "=" + "\"" + path + "\"";
      throw new ChiWenAccessControlException(
          "Permission denied: principal{user=" + user + ",groups: " + groups + "}, access=" + access
              + ", " + inodeInfo);
    }
    if (LOG.isDebugEnabled()) {
          LOG.debug("==> ChiWenFSPermissionChecker.check() end");
        }
    return accessGranted;
  }


  public static boolean AuthorizeAccessForUser(String aPathName, String aPathOwnerName,
      FsAction access, String user, Set<String> groups) throws ChiWenAccessControlException {
    if (LOG.isDebugEnabled()) {
      LOG.debug("==> ChiWenFSPermissionChecker.AuthorizeAccessForUser() begin");
    }
    boolean accessGranted = false;

    if (aPathName != null && aPathOwnerName != null && access != null && user != null
        && groups != null) {
      if (ChiWenHadoopConstants.HDFS_ROOT_FOLDER_PATH_ALT.equals(aPathName)) {
        aPathName = ChiWenHadoopConstants.HDFS_ROOT_FOLDER_PATH;
      }

      ChiWenHdfsPlugin plugin = chiWenPlugin;

      if (plugin == null) {
        synchronized (ChiWenFSPermissionChecker.class) {
          plugin = chiWenPlugin;

          if (plugin == null) {
            try {
              plugin = new ChiWenHdfsPlugin("hdfs","hdfs","hdfs");
              plugin.init();

              chiWenPlugin = plugin;
            } catch (Throwable t) {
              LOG.error("Unable to create Authorizer", t);
            }
          }
        }
      }

      if (chiWenPlugin != null) {
        Set<String> accessTypes = access2ActionListMapper.get(access);

        boolean isAllowed = true;
        for (String accessType : accessTypes) {
          ChiWenHdfsAccessRequest request = new ChiWenHdfsAccessRequest(aPathName, aPathOwnerName,
              access, accessType, user, groups);

          ChiWenAccessResult result = chiWenPlugin.isAccessAllowed(request, getCurrentAuditHandler());

          isAllowed = result.getIsAllowed();

          if (!isAllowed) {
            break;
          }
        }

        accessGranted = isAllowed;
      }
    }
    if (LOG.isDebugEnabled()) {
      LOG.debug("==> ChiWenFSPermissionChecker.AuthorizeAccessForUser() end ");
    }
    return accessGranted;
  }

  public static void checkPermissionPre(INodesInPath inodesInPath) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("==> ChiWenFSPermissionChecker.checkPermissionPre() begin");
    }
    ChiWenHdfsAuditHandler auditHandler = new ChiWenHdfsAuditHandler(inodesInPath.getPath());
    currentAuditHandler.set(auditHandler);
    if (LOG.isDebugEnabled()) {
      LOG.debug("==> ChiWenFSPermissionChecker.checkPermissionPre() end");
    }
  }

  public static void checkPermissionPost(INodesInPath inodesInPath) {


    if (LOG.isDebugEnabled()) {
      LOG.debug("==> ChiWenFSPermissionChecker.checkPermissionPost() begin");
    }
    ChiWenHdfsAuditHandler auditHandler = getCurrentAuditHandler();

    if (auditHandler != null) {
      auditHandler.flushAudit();
    }

    currentAuditHandler.set(null);
    if (LOG.isDebugEnabled()) {
      LOG.debug("==> ChiWenFSPermissionChecker.checkPermissionPost() end");
    }
  }

  private static ChiWenHdfsAuditHandler getCurrentAuditHandler() {
    return currentAuditHandler.get();
  }
}
