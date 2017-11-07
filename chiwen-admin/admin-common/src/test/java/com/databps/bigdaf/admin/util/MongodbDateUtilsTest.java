package com.databps.bigdaf.admin.util;

import com.databps.bigdaf.core.util.DateUtils;
import org.junit.Test;

/**
 * Created by yyh on 17-7-18.
 */
public class MongodbDateUtilsTest {

  @Test
  public void longStartTime() throws Exception {
    System.out.println(DateUtils.formatDateNow());
    System.out.println(MongodbDateUtils.longStartTime());
  }

  @Test
  public void longEndTime() throws Exception {
    System.out.println(DateUtils.formatDateNow());
    System.out.println(MongodbDateUtils.longEndTime());
  }

}