package com.databps.bigdaf.chiwen.util;//package com.databps.bigdaf.chiwen.util;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by lgc on 17-7-6.
// */
//public class HdfsOpTranslator {
//
//private final static Map<String,String> transMap;
//
//static {
//  transMap=new HashMap<String,String>();
//  transMap.put("addBlock,create,CREATE","create");
//  transMap.put("mkdirs,MKDIRS","mkdirs");
//  transMap.put("delete,DELETE","DELETE");
//  transMap.put("getListing,LISTSTATUS","liststatus");
//  transMap.put("rename,RENAME","create");
//  transMap.put("create,CREATE","create");
//  transMap.put("create,CREATE","create");
//  transMap.put("create,CREATE","create");
//  transMap.put("create,CREATE","create");
//  transMap.put("create,CREATE","create");
//  transMap.put("create,CREATE","create");
//
//
//}
//
//  public static String opTranslator(String opStr){
//    String opTransStr=opStr;
//
//    for (String key:transMap.keySet()
//    ) {
//      if(key.contains(opTransStr)){
//        opTransStr=  transMap.get(key);
//        return opTransStr;
//      }
//
//    }
//  return opTransStr;
//
//  }
//
//}
//
