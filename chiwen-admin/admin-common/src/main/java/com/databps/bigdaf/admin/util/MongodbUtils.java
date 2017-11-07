package com.databps.bigdaf.admin.util;

import com.mongodb.BasicDBObject;
import java.util.regex.Pattern;

/**
 * mongodb utils
 */
public class MongodbUtils {

  public static BasicDBObject getLikeStr(String findStr) {
        Pattern pattern = Pattern.compile("^.*" + findStr + ".*$", Pattern.CASE_INSENSITIVE);
        return new BasicDBObject("$regex", pattern);
    }
}
