package com.databps.bigdaf.chiwen.common;

/**
 * Created by lgc on 17-7-20.
 */
public class ChiWenHadoopConstants {

  public static final boolean CHIWEN_ADD_HDFS_PERMISSION_DEFAULT = true;//默认校验permisson
  public static final String CHIWEN_ADD_HDFS_PERMISSION_PROP = "xasecure.add-hadoop-authorization";
  public static final String READ_ACCCESS_TYPE = "read";
  public static final String WRITE_ACCCESS_TYPE = "write";
  public static final String EXECUTE_ACCCESS_TYPE = "execute";

  public static final String HDFS_ROOT_FOLDER_PATH_ALT = "";
  public static final String HDFS_ROOT_FOLDER_PATH = "/";

  public static final String  HIVE_UPDATE_ChiWen_POLICIES_ON_GRANT_REVOKE_PROP 	     = "xasecure.hive.update.xapolicies.on.grant.revoke";
  public static final boolean HIVE_UPDATE_ChiWen_POLICIES_ON_GRANT_REVOKE_DEFAULT_VALUE = true;
  public static final String  HIVE_BLOCK_UPDATE_IF_ROWFILTER_COLUMNMASK_SPECIFIED_PROP          = "xasecure.hive.block.update.if.rowfilter.columnmask.specified";
  public static final boolean HIVE_BLOCK_UPDATE_IF_ROWFILTER_COLUMNMASK_SPECIFIED_DEFAULT_VALUE = true;
  public static final String  HIVE_DESCRIBE_TABLE_SHOW_COLUMNS_AUTH_OPTION_PROP	= "xasecure.hive.describetable.showcolumns.authorization.option";
  public static final String  HIVE_DESCRIBE_TABLE_SHOW_COLUMNS_AUTH_OPTION_PROP_DEFAULT_VALUE	= "NONE";


}
