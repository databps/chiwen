package com.databps.bigdaf.chiwen.hdfsagent;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

/**
 * Created by lgc on 17-7-4.
 */
public class HadoopAuthClassTransformer implements ClassFileTransformer {

  volatile byte[] transformedClassByteCode = null;

  @Override
  public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
      ProtectionDomain protectionDomain, byte[] classfileBuffer)
      throws IllegalClassFormatException {
    byte[] ret = classfileBuffer;
    if (className.equals("org/apache/hadoop/hdfs/server/namenode/FSPermissionChecker")) {
      System.out.println("org/apache/hadoop/hdfs/server/namenode/FSPermissionChecker catched!!!");
      byte[] result = transformedClassByteCode;
      if (result == null) {

        byte[] injectedClassCode = injectFSPermissionCheckerHooks(className);

        if (injectedClassCode != null) {
          synchronized (HadoopAuthClassTransformer.class) {
            result = transformedClassByteCode;
            if (result == null) {
              transformedClassByteCode = result = injectedClassCode;
            }
          }
        }
      }

      if (result != null) {
        ret = result;
      }
    }

    return ret;
  }

  private static byte[] injectFSPermissionCheckerHooks(String aClassName)
      throws IllegalClassFormatException {
    byte[] ret = null;

    System.out.println(
        "Injection code is Invoked in JVM [" + Runtime.getRuntime() + "] for class [" + aClassName
            + "] ....");
    try {
      CtClass curClass = getCtClass(aClassName.replaceAll("/", "."));
      CtClass stringClass = getCtClass("java.lang.String");
      CtClass throwable = getCtClass("java.lang.Throwable");
      CtClass fsActionClass = getCtClass("org.apache.hadoop.fs.permission.FsAction");
      CtClass fsDirClass = getCtClass("org.apache.hadoop.hdfs.server.namenode.FSDirectory");
      CtClass inodeClass = getCtClass("org.apache.hadoop.hdfs.server.namenode.INode");
      CtClass iNodeAttributes = getCtClass("org.apache.hadoop.hdfs.server.namenode.INodeAttributes");
      CtClass inodesInPathClass = getCtClass("org.apache.hadoop.hdfs.server.namenode.INodesInPath");
      CtClass accCtrlExcp = getCtClass("org.apache.hadoop.security.AccessControlException");

      boolean is3ParamsCheckMethod = false;

      CtMethod checkMethod = null;
      CtMethod checkPermissionMethod = null;

      if (checkMethod == null && curClass != null && inodeClass != null && fsActionClass != null) {
        try {
          System.out.print("looking for check(INodeAttributes inode, String path, FsAction access)...");

          CtClass[] paramArgs = new CtClass[]{iNodeAttributes,stringClass, fsActionClass};
          //inodeclass 文件元信息 //fsActionClass 文件的请求操作信息（读/写）
          checkMethod = curClass.getDeclaredMethod("check", paramArgs);

          is3ParamsCheckMethod = true;

          System.out.println("found");
        } catch (NotFoundException nfe) {
          System.out.println("not found");
        }
      }

      if (checkMethod == null && curClass != null && inodeClass != null && fsActionClass != null) {
        try {
          System.out.print("looking for check(INode, FsAction)...");

          CtClass[] paramArgs = new CtClass[]{inodeClass, fsActionClass};

          checkMethod = curClass.getDeclaredMethod("check", paramArgs);

          is3ParamsCheckMethod = false;

          System.out.println("found");
        } catch (NotFoundException nfe) {
          System.out.println("not found");
        }
      }

      if (checkPermissionMethod == null && curClass != null && inodesInPathClass != null
          && fsActionClass != null) {
        try {
          System.out.print(
              "looking for checkPermission(INodesInPath, boolean, FsAction, FsAction, FsAction, FsAction, boolean)...");

          CtClass[] paramArgs = new CtClass[]{inodesInPathClass,
              CtClass.booleanType,
              fsActionClass,
              fsActionClass,
              fsActionClass,
              fsActionClass,
              CtClass.booleanType
          };

          checkPermissionMethod = curClass.getDeclaredMethod("checkPermission", paramArgs);

          System.out.println("found");
        } catch (NotFoundException nfe) {
          System.out.println("not found");
        }
      }

      if (checkPermissionMethod == null && curClass != null && stringClass != null
          && fsDirClass != null && fsActionClass != null) {
        try {
          System.out.print(
              "looking for checkPermission(String, FSDirectory, boolean, FsAction, FsAction, FsAction, FsAction, boolean, boolean)...");

          CtClass[] paramArgs = new CtClass[]{stringClass,
              fsDirClass,
              CtClass.booleanType,
              fsActionClass,
              fsActionClass,
              fsActionClass,
              fsActionClass,
              CtClass.booleanType,
              CtClass.booleanType
          };

          checkPermissionMethod = curClass.getDeclaredMethod("checkPermission", paramArgs);

          System.out.println("found");
        } catch (NotFoundException nfe) {
          System.out.println("not found");
        }
      }

      if (curClass != null) {
        if (checkMethod != null) {
          System.out.print("injecting check() hooks...");
//
//          checkMethod.insertAfter(
//              "org.apache.hadoop.hdfs.server.namenode.ChiWenFSPermissionChecker.logHadoopEvent($1,true);");
//          checkMethod.addCatch(
//              "{ org.apache.hadoop.hdfs.server.namenode.ChiWenFSPermissionChecker.logHadoopEvent($1,false); throw $e; }",
//              throwable);

          if (is3ParamsCheckMethod) {
            checkMethod.insertBefore("{ if ( com.databps.bigdaf.chiwen.hdfsagent.ChiWenFSPermissionChecker.check(this.callerUgi,$1,$2,$3) ) { return; } }");
          } else {
            checkMethod.insertBefore(
                "{ if ( com.databps.bigdaf.chiwen.hdfsagent.ChiWenFSPermissionChecker.check(this.callerUgi,groups,$1,$2) ) { return; } }");//取inode和FsAction
          }

          System.out.println("done");

          if (checkPermissionMethod != null) {
            System.out.print("injecting checkPermission() hooks...");
            //插入的checkPermissionPost等方法就是发送一些checkpermission的审计日志，hadoop里面checkPermission的方法调用的是check方法，checkPermission的逻辑是判断一些ancestor，parent的check先后逻辑，check方法做真正的acl等权限验证
            checkPermissionMethod.insertAfter(
                "com.databps.bigdaf.chiwen.hdfsagent.ChiWenFSPermissionChecker.checkPermissionPost($1);");
            checkPermissionMethod.addCatch(
                "{ com.databps.bigdaf.chiwen.hdfsagent.ChiWenFSPermissionChecker.checkPermissionPost($1); throw $e; }",
                accCtrlExcp);
            checkPermissionMethod.insertBefore(
                "com.databps.bigdaf.chiwen.hdfsagent.ChiWenFSPermissionChecker.checkPermissionPre($1);");

            System.out.println("done");
          }

          ret = curClass.toBytecode();
        } else {
          System.out.println("Unable to identify check() method on class: [" + aClassName
              + "]. Found following methods:");

          for (CtMethod m : curClass.getDeclaredMethods()) {
            System.err.println("  found Method: " + m);
          }

          System.out.println("Injection failed. Continue without Injection");
        }
      }
    } catch (CannotCompileException e) {
      System.err
          .println("Can not compile Exception for class Name: " + aClassName + " Exception: " + e);
      e.printStackTrace();
    } catch (IOException e) {
      System.err.println("IO Exception for class Name: " + aClassName + " Exception: " + e);
      e.printStackTrace();
    }

    return ret;
  }

  private static CtClass getCtClass(String className) {
    CtClass ret = null;

    try {
      ClassPool cp = ClassPool.getDefault();

      ret = cp.get(className);
    } catch (NotFoundException nfe) {
      System.err.println("Unable to find Class for [" + className + "]" + nfe);
      ret = null;
    }

    return ret;
  }
}
