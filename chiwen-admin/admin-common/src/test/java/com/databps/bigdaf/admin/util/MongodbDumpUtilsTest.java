package com.databps.bigdaf.admin.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * @author merlin
 * @create 2017-09-06 下午5:25
 */
public class MongodbDumpUtilsTest {

  @Test
  public void testCommand() throws IOException, InterruptedException {
    //-h $host -d $database -c $collection -q '{"access_time":{$gt:"'$start_time'",$lt:"'$end_time'"}}' -o $bak_dir

 /*   List<String> command = new ArrayList<>();
    command.add(
        "/Users/merlin/Documents/eclipse-workspace/ideaProject/bigdaf/bigdaf-admin/admin/target/classes/bin/mongodump");
    command.add("-h");
    command.add("192.168.1.108:27007");
    command.add("-u");
    command.add("");
    command.add("-p");
    command.add("");
    command.add("-d");
    command.add("bigdafAdmin");
    command.add("-c");
    command.add("audit");
    command.add("-q");
    command.add("{\"access_time\":{$gt:\"'$start_time'\",$lt:\"'$end_time'\"}}");
    command.add("-o");
    command.add("/Users/merlin/Documents/example");
//    command.add("");
//    command.add("");
//    command.add("20170729131138428");
//    command.add("20170902131138428");

    ShellCommandUtils.Result result = ShellCommandUtils
        .runCommand(command.toArray(new String[command.size()]));
    System.out.println("out==========>" + result.getStdout());
    System.out.println("code==========>" + result.getExitCode());
    System.out.println("err==========>" + result.getStderr());

    if (result.getExitCode() == 0) {
      //delete
    }*/
  }
}
