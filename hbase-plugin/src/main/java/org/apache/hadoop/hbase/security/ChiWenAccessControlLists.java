package org.apache.hadoop.hbase.security;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.hadoop.hbase.TableExistsException;
import org.apache.hadoop.hbase.master.MasterServices;
import org.apache.hadoop.hbase.security.access.AccessControlLists;
import org.apache.log4j.Logger;

/**
 * @author merlin
 * @create 2017-11-07 下午2:49
 */
public class ChiWenAccessControlLists {
  private static final Logger LOG = Logger.getLogger(ChiWenAccessControlLists.class);

  public static void init(MasterServices master) throws IOException {

    Class<AccessControlLists> accessControlListsClass = AccessControlLists.class;
    String cName = accessControlListsClass.getName();

    Class<?>[] params = new Class[1];
    params[0] = MasterServices.class;

    for (String mname : new String[] { "init", "createACLTable" } ) {
      try {
        try {
          Method m = accessControlListsClass.getDeclaredMethod(mname, params);
          if (m != null) {
            try {

              try {
                m.invoke(null, master);
                logInfo("Execute method name [" + mname + "] in Class [" +  cName + "] is successful.");
              } catch (InvocationTargetException e) {
                Throwable cause = e;
                boolean tableExistsExceptionFound = false;
                if  (e != null) {
                  Throwable ecause = e.getTargetException();
                  if (ecause != null) {
                    cause = ecause;
                    if (ecause instanceof TableExistsException) {
                      tableExistsExceptionFound = true;
                    }
                  }
                }
                if (! tableExistsExceptionFound) {
                  logError("Unable to execute the method [" + mname + "] on [" + cName + "] due to exception", cause);
                  throw new IOException(cause);
                }
              }
              return;
            } catch (IllegalArgumentException e) {
              logError("Unable to execute method name [" + mname + "] in Class [" +  cName + "].", e);
              throw new IOException(e);
            } catch (IllegalAccessException e) {
              logError("Unable to execute method name [" + mname + "] in Class [" +  cName + "].", e);
              throw new IOException(e);
            }
          }
        }
        catch(NoSuchMethodException nsme) {
          logInfo("Unable to get method name [" + mname + "] in Class [" +  cName + "]. Ignoring the exception");
        }
      } catch (SecurityException e) {
        logError("Unable to get method name [" + mname + "] in Class [" +  cName + "].", e);
        throw new IOException(e);
      }
    }
    throw new IOException("Unable to initialize() [" + cName + "]");
  }


  private static void logInfo(String msg) {
    // System.out.println(msg);
    LOG.info(msg);
  }

  private static void logError(String msg, Throwable t) {
//		System.err.println(msg);
//		if (t != null) {
//			t.printStackTrace(System.err);
//		}
    LOG.error(msg, t);
  }
}
