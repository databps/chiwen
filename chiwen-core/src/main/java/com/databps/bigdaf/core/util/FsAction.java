package com.databps.bigdaf.core.util;

/**
 * @author shibingxin
 * @create 2017-08-29 下午1:48
 */
public enum FsAction {
  // POSIX style
  NONE("---","空"),
  EXECUTE("--x","执行"),
  WRITE("-w-","写"),
  WRITE_EXECUTE("-wx","写执行"),
  READ("r--","读"),
  READ_EXECUTE("r-x","读执行"),
  READ_WRITE("rw-","读写"),
  ALL("rwx","读写执行");

  /** Retain reference to value array. */
  private final static FsAction[] vals = values();

  /** Symbolic representation */
  public final String SYMBOL;

  public final String NAME;
  private FsAction(String s,String name) {
    SYMBOL = s;
    NAME =name;
  }

  /**
   * Return true if this action implies that action.
   * @param that
   */
  public boolean implies(FsAction that) {
    if (that != null) {
      return (ordinal() & that.ordinal()) == that.ordinal();
    }
    return false;
  }

  /** AND operation. */
  public FsAction and(FsAction that) {
    return vals[ordinal() & that.ordinal()];
  }
  /** OR operation. */
  public FsAction or(FsAction that) {
    return vals[ordinal() | that.ordinal()];
  }
  /** NOT operation. */
  public FsAction not() {
    return vals[7 - ordinal()];
  }

  /**
   * Get the FsAction enum for String representation of permissions
   *
   * @param permission
   *          3-character string representation of permission. ex: rwx
   * @return Returns FsAction enum if the corresponding FsAction exists for permission.
   *         Otherwise returns null
   */
  public static FsAction getFsAction(String permission) {
    for (FsAction fsAction : vals) {
      if (fsAction.SYMBOL.equals(permission)) {
        return fsAction;
      }
    }
    return null;
  }
}