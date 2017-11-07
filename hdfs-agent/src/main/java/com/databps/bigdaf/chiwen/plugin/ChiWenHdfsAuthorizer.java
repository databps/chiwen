package com.databps.bigdaf.chiwen.plugin;

import static com.databps.bigdaf.chiwen.common.ChiWenHadoopConstants.EXECUTE_ACCCESS_TYPE;
import static com.databps.bigdaf.chiwen.common.ChiWenHadoopConstants.READ_ACCCESS_TYPE;
import static com.databps.bigdaf.chiwen.common.ChiWenHadoopConstants.WRITE_ACCCESS_TYPE;

import com.databps.bigdaf.chiwen.audit.AuthzAuditEvent;
import com.databps.bigdaf.chiwen.audit.ChiWenDefaultAuditHandler;
import com.databps.bigdaf.chiwen.common.ChiWenHadoopConstants;
import com.databps.bigdaf.chiwen.exceptions.ChiWenAccessControlException;
import com.databps.bigdaf.chiwen.policy.ChiWenAccessRequest;
import com.databps.bigdaf.chiwen.policy.ChiWenAccessRequestImpl;
import com.databps.bigdaf.chiwen.policy.ChiWenAccessResource;
import com.databps.bigdaf.chiwen.policy.ChiWenAccessResourceImpl;
import com.databps.bigdaf.chiwen.policy.ChiWenAccessResult;
import com.databps.bigdaf.chiwen.util.ChiWenAccessRequestUtil;
import com.google.common.collect.Sets;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.fs.permission.FsAction;
import org.apache.hadoop.hdfs.server.namenode.INode;
import org.apache.hadoop.hdfs.server.namenode.INodeAttributeProvider;
import org.apache.hadoop.hdfs.server.namenode.INodeAttributes;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.databps.bigdaf.chiwen.hdfsagent.ChiWenHdfsPlugin;
import org.apache.hadoop.hdfs.server.namenode.INodeDirectory;
import org.apache.hadoop.hdfs.util.ReadOnlyList;
import org.apache.hadoop.ipc.ProtobufRpcEngine.Server;
import org.apache.hadoop.security.AccessControlException;
import org.apache.hadoop.security.UserGroupInformation;

/**
 * @author merlin
 * @create 2017-08-13 上午11:52
 */
public class ChiWenHdfsAuthorizer extends INodeAttributeProvider {

  private static final Log LOG = LogFactory.getLog(ChiWenHdfsAuthorizer.class);
  private Map<FsAction, Set<String>> access2ActionListMapper = new HashMap<FsAction, Set<String>>();
  private ChiWenHdfsPlugin ChiWenPlugin = null;

  public static final String KEY_FILENAME = "FILENAME";

  public static final String KEY_RESOURCE_PATH = "path";

  public static final String KEY_BASE_FILENAME = "BASE_FILENAME";

  @Override
  public void start() {
    if (LOG.isDebugEnabled()) {
      LOG.debug("==> ChiWenHdfsAuthorizer.start()");
    }

    ChiWenHdfsPlugin plugin = new ChiWenHdfsPlugin("hdfs","hdfs","hdfs");
    plugin.init();

    access2ActionListMapper.put(FsAction.NONE, new HashSet<String>());
    access2ActionListMapper.put(FsAction.ALL, Sets
        .newHashSet(READ_ACCCESS_TYPE, WRITE_ACCCESS_TYPE, EXECUTE_ACCCESS_TYPE));
    access2ActionListMapper.put(FsAction.READ, Sets.newHashSet(READ_ACCCESS_TYPE));
    access2ActionListMapper
        .put(FsAction.READ_WRITE, Sets.newHashSet(READ_ACCCESS_TYPE, WRITE_ACCCESS_TYPE));
    access2ActionListMapper
        .put(FsAction.READ_EXECUTE, Sets.newHashSet(READ_ACCCESS_TYPE, EXECUTE_ACCCESS_TYPE));
    access2ActionListMapper.put(FsAction.WRITE, Sets.newHashSet(WRITE_ACCCESS_TYPE));
    access2ActionListMapper
        .put(FsAction.WRITE_EXECUTE, Sets.newHashSet(WRITE_ACCCESS_TYPE, EXECUTE_ACCCESS_TYPE));
    access2ActionListMapper.put(FsAction.EXECUTE, Sets.newHashSet(EXECUTE_ACCCESS_TYPE));

    ChiWenPlugin = plugin;

    if (LOG.isDebugEnabled()) {
      LOG.debug("<== ChiWenHdfsAuthorizer.start()");
    }
  }

  @Override
  public void stop() {
    if (LOG.isDebugEnabled()) {
      LOG.debug("==> ChiWenHdfsAuthorizer.stop()");
    }

    ChiWenHdfsPlugin plugin = ChiWenPlugin;
    ChiWenPlugin = null;

    if (plugin != null) {
      plugin.cleanup();
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("<== ChiWenHdfsAuthorizer.stop()");
    }

  }

  @Override
  public INodeAttributes getAttributes(String fullPath, INodeAttributes inode) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("==> ChiWenHdfsAuthorizer.getAttributes(" + fullPath + ")");
    }

    INodeAttributes ret = inode; // return default attributes

    if (LOG.isDebugEnabled()) {
      LOG.debug("<== ChiWenHdfsAuthorizer.getAttributes(" + fullPath + "): " + ret);
    }

    return ret;
  }

  @Override
  public AccessControlEnforcer getExternalAccessControlEnforcer(
      AccessControlEnforcer defaultEnforcer) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("==> ChiWenHdfsAuthorizer.getExternalAccessControlEnforcer()");
    }

    ChiWenAccessControlEnforcer ChiWenAce = new ChiWenAccessControlEnforcer(defaultEnforcer);

    if (LOG.isDebugEnabled()) {
      LOG.debug("<== ChiWenHdfsAuthorizer.getExternalAccessControlEnforcer()");
    }

    return ChiWenAce;
  }


  @Override
  public INodeAttributes getAttributes(String[] pathElements, INodeAttributes inode) {
    if (LOG.isDebugEnabled()) {
      LOG.debug(
          "==> ChiWenHdfsAuthorizer.getAttributes(pathElementsCount=" + (pathElements == null ? 0
              : pathElements.length) + ")");
    }

    INodeAttributes ret = inode; // return default attributes

    if (LOG.isDebugEnabled()) {
      LOG.debug(
          "<== ChiWenHdfsAuthorizer.getAttributes(pathElementsCount=" + (pathElements == null ? 0
              : pathElements.length) + "): " + ret);
    }

    return ret;
  }

  private enum AuthzStatus {ALLOW, DENY, NOT_DETERMINED}

  ;

  class ChiWenAccessControlEnforcer implements AccessControlEnforcer {

    private INodeAttributeProvider.AccessControlEnforcer defaultEnforcer = null;

    public ChiWenAccessControlEnforcer(AccessControlEnforcer defaultEnforcer) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("==> ChiWenAccessControlEnforcer.ChiWenAccessControlEnforcer()");
      }

      this.defaultEnforcer = defaultEnforcer;

      if (LOG.isDebugEnabled()) {
        LOG.debug("<== ChiWenAccessControlEnforcer.ChiWenAccessControlEnforcer()");
      }
    }


    @Override
    public void checkPermission(String fsOwner, String superGroup, UserGroupInformation ugi,
        INodeAttributes[] inodeAttrs, INode[] inodes, byte[][] pathByNameArr,
        int snapshotId, String path, int ancestorIndex, boolean doCheckOwner,
        FsAction ancestorAccess, FsAction parentAccess, FsAction access,
        FsAction subAccess, boolean ignoreEmptyDir) throws AccessControlException {
      AuthzStatus authzStatus = AuthzStatus.NOT_DETERMINED;
      ChiWenHdfsPlugin plugin = ChiWenPlugin;
      ChiWenHdfsAuditHandler auditHandler = null;
      String user = ugi != null ? ugi.getShortUserName() : null;
      Set<String> groups = ugi != null ? Sets.newHashSet(ugi.getGroupNames()) : null;

      if (LOG.isDebugEnabled()) {
        LOG.debug("==> ChiWenAccessControlEnforcer.checkPermission("
            + "fsOwner=" + fsOwner + "; superGroup=" + superGroup + ", inodesCount=" + (
            inodes != null ? inodes.length : 0)
            + ", snapshotId=" + snapshotId + ", user=" + user + ", path=" + path
            + ", ancestorIndex=" + ancestorIndex
            + ", doCheckOwner=" + doCheckOwner + ", ancestorAccess=" + ancestorAccess
            + ", parentAccess=" + parentAccess
            + ", access=" + access + ", subAccess=" + subAccess + ", ignoreEmptyDir="
            + ignoreEmptyDir + ")");
      }

      try {
        boolean isTraverseOnlyCheck =
            access == null && parentAccess == null && ancestorAccess == null && subAccess == null;
        INode ancestor = null;
        INode parent = null;
        INode inode = null;

        if (plugin != null && !ArrayUtils.isEmpty(inodes)) {
          if (ancestorIndex >= inodes.length) {
            ancestorIndex = inodes.length - 1;
          }

          for (; ancestorIndex >= 0 && inodes[ancestorIndex] == null; ancestorIndex--)
            ;

          authzStatus = AuthzStatus.ALLOW;

          ancestor = inodes.length > ancestorIndex && ancestorIndex >= 0 ? inodes[ancestorIndex]
              : null;//祖先节点
          parent = inodes.length > 1 ? inodes[inodes.length - 2] : null;//父节点
          inode = inodes[inodes.length - 1]; // could be null while creating a new file

          auditHandler = new ChiWenHdfsAuditHandler(path, isTraverseOnlyCheck);

          if (isTraverseOnlyCheck) {
            INode nodeToCheck = inode;
            INodeAttributes nodeAttribs =
                inodeAttrs.length > 0 ? inodeAttrs[inodeAttrs.length - 1] : null;

            if (nodeToCheck == null || nodeToCheck.isFile()) {
              if (parent != null) {
                nodeToCheck = parent;
                nodeAttribs = inodeAttrs.length > 1 ? inodeAttrs[inodeAttrs.length - 2] : null;
              } else if (ancestor != null) {
                nodeToCheck = ancestor;
                nodeAttribs = inodeAttrs.length > ancestorIndex ? inodeAttrs[ancestorIndex] : null;
              }
            }

            if (nodeToCheck != null) {
              authzStatus = isAccessAllowed(nodeToCheck, nodeAttribs, FsAction.EXECUTE, user,
                  groups, plugin, auditHandler);
            }
          }

          // checkStickyBit
          if (authzStatus == AuthzStatus.ALLOW && parentAccess != null && parentAccess
              .implies(FsAction.WRITE) && parent != null && inode != null) {
            if (parent.getFsPermission() != null && parent.getFsPermission().getStickyBit()) {
              // user should be owner of the parent or the inode
              authzStatus = (StringUtils.equals(parent.getUserName(), user) || StringUtils
                  .equals(inode.getUserName(), user)) ? AuthzStatus.ALLOW
                  : AuthzStatus.NOT_DETERMINED;
            }
          }

          // checkAncestorAccess
          if (authzStatus == AuthzStatus.ALLOW && ancestorAccess != null && ancestor != null) {
            INodeAttributes ancestorAttribs =
                inodeAttrs.length > ancestorIndex ? inodeAttrs[ancestorIndex] : null;

            authzStatus = isAccessAllowed(ancestor, ancestorAttribs, ancestorAccess, user, groups,
                plugin, auditHandler);
            if (authzStatus == AuthzStatus.NOT_DETERMINED) {
              authzStatus = checkDefaultEnforcer(fsOwner, superGroup, ugi, inodeAttrs, inodes,
                  pathByNameArr, snapshotId, path, ancestorIndex, doCheckOwner,
                  ancestorAccess, FsAction.NONE, FsAction.NONE, FsAction.NONE, ignoreEmptyDir,
                  isTraverseOnlyCheck, ancestor, parent, inode, auditHandler);
            }
          }

          // checkParentAccess
          if (authzStatus == AuthzStatus.ALLOW && parentAccess != null && parent != null) {
            INodeAttributes parentAttribs =
                inodeAttrs.length > 1 ? inodeAttrs[inodeAttrs.length - 2] : null;

            authzStatus = isAccessAllowed(parent, parentAttribs, parentAccess, user, groups, plugin,
                auditHandler);
            if (authzStatus == AuthzStatus.NOT_DETERMINED) {
              authzStatus = checkDefaultEnforcer(fsOwner, superGroup, ugi, inodeAttrs, inodes,
                  pathByNameArr, snapshotId, path, ancestorIndex, doCheckOwner,
                  FsAction.NONE, parentAccess, FsAction.NONE, FsAction.NONE, ignoreEmptyDir,
                  isTraverseOnlyCheck, ancestor, parent, inode, auditHandler);
            }
          }

          // checkINodeAccess
          if (authzStatus == AuthzStatus.ALLOW && access != null && inode != null) {
            INodeAttributes inodeAttribs =
                inodeAttrs.length > 0 ? inodeAttrs[inodeAttrs.length - 1] : null;

            authzStatus = isAccessAllowed(inode, inodeAttribs, access, user, groups, plugin,
                auditHandler);
            if (authzStatus == AuthzStatus.NOT_DETERMINED) {
              authzStatus = checkDefaultEnforcer(fsOwner, superGroup, ugi, inodeAttrs, inodes,
                  pathByNameArr, snapshotId, path, ancestorIndex, doCheckOwner,
                  FsAction.NONE, FsAction.NONE, access, FsAction.NONE, ignoreEmptyDir,
                  isTraverseOnlyCheck, ancestor, parent, inode, auditHandler);
            }
          }

          // checkSubAccess
          if (authzStatus == AuthzStatus.ALLOW && subAccess != null && inode != null && inode
              .isDirectory()) {
            Stack<INodeDirectory> directories = new Stack<INodeDirectory>();

            for (directories.push(inode.asDirectory()); !directories.isEmpty(); ) {
              INodeDirectory dir = directories.pop();
              ReadOnlyList<INode> cList = dir.getChildrenList(snapshotId);

              if (!(cList.isEmpty() && ignoreEmptyDir)) {
                INodeAttributes dirAttribs = dir.getSnapshotINode(snapshotId);

                authzStatus = isAccessAllowed(dir, dirAttribs, subAccess, user, groups, plugin,
                    auditHandler);

                if (authzStatus != AuthzStatus.ALLOW) {
                  break;
                }
              }

              for (INode child : cList) {
                if (child.isDirectory()) {
                  directories.push(child.asDirectory());
                }
              }
            }
            if (authzStatus == AuthzStatus.NOT_DETERMINED) {
              authzStatus = checkDefaultEnforcer(fsOwner, superGroup, ugi, inodeAttrs, inodes,
                  pathByNameArr, snapshotId, path, ancestorIndex, doCheckOwner,
                  FsAction.NONE, FsAction.NONE, FsAction.NONE, subAccess, ignoreEmptyDir,
                  isTraverseOnlyCheck, ancestor, parent, inode, auditHandler);
            }
          }

          // checkOwnerAccess
          if (authzStatus == AuthzStatus.ALLOW && doCheckOwner) {
            INodeAttributes inodeAttribs =
                inodeAttrs.length > 0 ? inodeAttrs[inodeAttrs.length - 1] : null;
            String owner = inodeAttribs != null ? inodeAttribs.getUserName() : null;

            authzStatus =
                StringUtils.equals(user, owner) ? AuthzStatus.ALLOW : AuthzStatus.NOT_DETERMINED;
          }
        }

        if (authzStatus == AuthzStatus.NOT_DETERMINED) {
          authzStatus = checkDefaultEnforcer(fsOwner, superGroup, ugi, inodeAttrs, inodes,
              pathByNameArr, snapshotId, path, ancestorIndex, doCheckOwner,
              ancestorAccess, parentAccess, access, subAccess, ignoreEmptyDir,
              isTraverseOnlyCheck, ancestor, parent, inode, auditHandler);
        }

        if (authzStatus != AuthzStatus.ALLOW) {
          FsAction action = access;

          if (action == null) {
            if (parentAccess != null) {
              action = parentAccess;
            } else if (ancestorAccess != null) {
              action = ancestorAccess;
            } else {
              action = FsAction.EXECUTE;
            }
          }

          throw new ChiWenAccessControlException(
              "Permission denied: user=" + user + ", access=" + action + ", inode=\"" + path
                  + "\"");
        }
      } finally {
        if (auditHandler != null) {
          auditHandler.flushAudit();
        }

        if (LOG.isDebugEnabled()) {
          LOG.debug(
              "<== ChiWenAccessControlEnforcer.checkPermission(" + path + ", " + access + ", user="
                  + user + ") : " + authzStatus);
        }
      }
    }

    private AuthzStatus checkDefaultEnforcer(String fsOwner, String superGroup,
        UserGroupInformation ugi,
        INodeAttributes[] inodeAttrs, INode[] inodes, byte[][] pathByNameArr,
        int snapshotId, String path, int ancestorIndex, boolean doCheckOwner,
        FsAction ancestorAccess, FsAction parentAccess, FsAction access,
        FsAction subAccess, boolean ignoreEmptyDir,
        boolean isTraverseOnlyCheck, INode ancestor,
        INode parent, INode inode, ChiWenHdfsAuditHandler auditHandler
    ) throws AccessControlException {
      AuthzStatus authzStatus = AuthzStatus.NOT_DETERMINED;
      if (ChiWenHdfsPlugin.isHadoopAuthEnabled() && defaultEnforcer != null) {
        try {
          defaultEnforcer.checkPermission(fsOwner, superGroup, ugi, inodeAttrs, inodes,
              pathByNameArr, snapshotId, path, ancestorIndex, doCheckOwner,
              ancestorAccess, parentAccess, access, subAccess, ignoreEmptyDir);

          authzStatus = AuthzStatus.ALLOW;
        } finally {
          if (auditHandler != null) {
            INode nodeChecked = inode;
            FsAction action = access;
            if (isTraverseOnlyCheck) {
              if (nodeChecked == null || nodeChecked.isFile()) {
                if (parent != null) {
                  nodeChecked = parent;
                } else if (ancestor != null) {
                  nodeChecked = ancestor;
                }
              }

              action = FsAction.EXECUTE;
            } else if (action == null || action == FsAction.NONE) {
              if (parentAccess != null && parentAccess != FsAction.NONE) {
                nodeChecked = parent;
                action = parentAccess;
              } else if (ancestorAccess != null && ancestorAccess != FsAction.NONE) {
                nodeChecked = ancestor;
                action = ancestorAccess;
              } else if (subAccess != null && subAccess != FsAction.NONE) {
                action = subAccess;
              }
            }

            String pathChecked = nodeChecked != null ? nodeChecked.getFullPathName() : path;

            auditHandler.logHadoopEvent(pathChecked, action, authzStatus == AuthzStatus.ALLOW);
          }
        }
        return authzStatus;
      }
      return authzStatus;
    }

    class ChiWenHdfsResource extends ChiWenAccessResourceImpl {

      public ChiWenHdfsResource(String path, String owner) {
        super.setValue(ChiWenHdfsAuthorizer.KEY_RESOURCE_PATH, path);
        super.setOwnerUser(owner);
      }
    }

    class ChiWenHdfsAccessRequest extends ChiWenAccessRequestImpl {

      public ChiWenHdfsAccessRequest(INode inode, String path, String pathOwner, FsAction access,
          String accessType, String user, Set<String> groups) {
        super.setResource(new ChiWenHdfsResource(path, pathOwner));
        super.setAccessType(accessType);
        super.setPath(path);
        super.setUser(user);
        super.setUserGroups(groups);
        super.setAccessTime(new Date());
        super.setClientIPAddress(getRemoteIp());
        super.setAction(access.toString());

        if (inode != null) {
          buildRequestContext(inode);
        }
      }

      private String getRemoteIp() {
        String ret = "0.0.0.0";
        InetAddress ip = Server.getRemoteIp();
        if (ip != null) {
          ret = ip.getHostAddress();
        }
        return ret;
      }

      private void buildRequestContext(final INode inode) {
        if (inode.isFile()) {
          String fileName = inode.getLocalName();
          ChiWenAccessRequestUtil
              .setTokenInContext(getContext(), ChiWenHdfsAuthorizer.KEY_FILENAME, fileName);
          int lastExtensionSeparatorIndex = fileName
              .lastIndexOf(ChiWenHdfsPlugin.getFileNameExtensionSeparator());
          if (lastExtensionSeparatorIndex != -1) {
            String baseFileName = fileName.substring(0, lastExtensionSeparatorIndex);
            ChiWenAccessRequestUtil
                .setTokenInContext(getContext(), ChiWenHdfsAuthorizer.KEY_BASE_FILENAME,
                    baseFileName);
          }
        }
      }
    }


    private AuthzStatus isAccessAllowed(INode inode, INodeAttributes inodeAttribs, FsAction access,
        String user, Set<String> groups, ChiWenHdfsPlugin plugin,
        ChiWenHdfsAuditHandler auditHandler) {
      AuthzStatus ret = null;
      String path = inode != null ? inode.getFullPathName() : null;//整条路径
      String pathOwner = inodeAttribs != null ? inodeAttribs.getUserName() : null;//所属owner

      if (pathOwner == null && inode != null) {
        pathOwner = inode.getUserName();
      }

      if (ChiWenHadoopConstants.HDFS_ROOT_FOLDER_PATH_ALT.equals(path)) {
        path = ChiWenHadoopConstants.HDFS_ROOT_FOLDER_PATH;
      }

      if (LOG.isDebugEnabled()) {
        LOG.debug(
            "==> ChiWenAccessControlEnforcer.isAccessAllowed(" + path + ", " + access + ", " + user
                + ")");
      }

      Set<String> accessTypes = access2ActionListMapper.get(access);

      if (accessTypes == null) {
        LOG.warn("ChiWenAccessControlEnforcer.isAccessAllowed(" + path + ", " + access + ", " + user
            + "): no ChiWen accessType found for " + access);

        accessTypes = access2ActionListMapper.get(FsAction.NONE);
      }

      for (String accessType : accessTypes) {
        ChiWenHdfsAccessRequest request = new ChiWenHdfsAccessRequest(inode, path, pathOwner,
            access, accessType, user, groups);

        ChiWenAccessResult result = plugin.isAccessAllowed(request, auditHandler);

        if (result == null || !result.getIsAccessDetermined()) {
          ret = AuthzStatus.NOT_DETERMINED;
          // don't break yet; subsequent accessType could be denied
        } else if (!result.getIsAllowed()) { // explicit deny
          ret = AuthzStatus.DENY;
          break;
        } else { // allowed
          if (!AuthzStatus.NOT_DETERMINED
              .equals(ret)) { // set to ALLOW only if there was no NOT_DETERMINED earlier
            ret = AuthzStatus.ALLOW;
          }
        }
      }

      if (ret == null) {
        ret = AuthzStatus.NOT_DETERMINED;
      }

      if (LOG.isDebugEnabled()) {
        LOG.debug(
            "<== ChiWenAccessControlEnforcer.isAccessAllowed(" + path + ", " + access + ", " + user
                + "): " + ret);
      }

      return ret;
    }
  }
}
class ChiWenHdfsAuditHandler extends ChiWenDefaultAuditHandler {

    private static final Log LOG = LogFactory.getLog(ChiWenHdfsAuditHandler.class);

    private boolean isAuditEnabled = false;
    private AuthzAuditEvent auditEvent = null;
    private final String pathToBeValidated;
    private final boolean auditOnlyIfDenied;

    private static final String HadoopModuleName = "hadoop-acl";
    private static final String excludeUserList = "";
    private static HashSet<String> excludeUsers = null;


    public ChiWenHdfsAuditHandler(String pathToBeValidated, boolean auditOnlyIfDenied) {
      this.pathToBeValidated = pathToBeValidated;
      this.auditOnlyIfDenied = auditOnlyIfDenied;
    }

    @Override
    public void processResult(ChiWenAccessResult result) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("==> ChiWenHdfsAuditHandler.logAudit(" + result + ")");
      }

      if (!isAuditEnabled && result.getIsAudited()) {
        isAuditEnabled = true;
      }

      if (auditEvent == null) {
        auditEvent = super.getAuthzEvents(result);
      }

      if (auditEvent != null) {
        ChiWenAccessRequest request = result.getAccessRequest();
        ChiWenAccessResource resource = request.getResource();
        String resourcePath = resource != null ? resource.getAsString() : null;

        // Overwrite fields in original auditEvent
        auditEvent.setAccessTime(request.getAccessTime());
        auditEvent.setAccessType(request.getAction());
        auditEvent.setResourcePath(this.pathToBeValidated);
        auditEvent.setResultReason(resourcePath);

        auditEvent.setAccessResult((result.getIsAllowed() ? "success": "failure"));
        auditEvent.setPolicyId(result.getPolicyId());
        auditEvent.setServiceId(result.getServiceId());



      }

      if (LOG.isDebugEnabled()) {
        LOG.debug("<== ChiWenHdfsAuditHandler.logAudit(" + result + "): " + auditEvent);
      }
    }

    public void logHadoopEvent(String path, FsAction action, boolean accessGranted) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("==> ChiWenHdfsAuditHandler.logHadoopEvent(" + path + ", " + action + ", "
            + accessGranted + ")");
      }

      if (auditEvent != null) {
        auditEvent.setResultReason(path);
        auditEvent.setAccessResult((accessGranted ? "success": "failure"));
        auditEvent.setAccessType(action == null ? null : action.toString());
        auditEvent.setAclEnforcer(HadoopModuleName);
        auditEvent.setPolicyId(-1);
      }

      if (LOG.isDebugEnabled()) {
        LOG.debug("<== ChiWenHdfsAuditHandler.logHadoopEvent(" + path + ", " + action + ", "
            + accessGranted + "): " + auditEvent);
      }
    }

    public void flushAudit() {
      if (LOG.isDebugEnabled()) {
        LOG.debug(
            "==> ChiWenHdfsAuditHandler.flushAudit(" + isAuditEnabled + ", " + auditEvent + ")");
      }

      if (isAuditEnabled && auditEvent != null && !StringUtils
          .isEmpty(auditEvent.getAccessType())) {
        String username = auditEvent.getUser();

        boolean skipLog =
            (username != null && excludeUsers != null && excludeUsers.contains(username)) || (
                auditOnlyIfDenied && auditEvent.getIsAllowed() != 0);

        if (!skipLog) {
          super.logAuthzAudit(auditEvent);
        }
      }

      if (LOG.isDebugEnabled()) {
        LOG.debug(
            "<== ChiWenHdfsAuditHandler.flushAudit(" + isAuditEnabled + ", " + auditEvent + ")");
      }
    }
}
