package com.databps.bigdaf.chiwen;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class TestUpload {
    static  Configuration conf = new Configuration(true);
    static{
        //指定hadoop fs的地址
        conf.set("fs.default.name", "hdfs://192.168.1.105:9000");
    }
    static {
        conf.set("HADOOP_USER_NAME", "hdfs");
    }

    @Test
    public void upLoadFileFromHDFS() throws Exception {

//        for (int i=0;i<1;i++){

            String dst = "/user/litao/hadoop/uplode1000_1/1000"+ UUID.randomUUID().toString()+"a.txt";
            String filePath = "F:\\download\\upload\\100_2M.txt";
            FileSystem fs = FileSystem.get(conf);
            Path srcPath = new Path(filePath);
            Path dstPath = new Path(dst);
            Long start = System.currentTimeMillis();
            fs.copyFromLocalFile(false, srcPath, dstPath);
            System.out.println("Time:"+ (System.currentTimeMillis() - start));

            System.out.println("________________________Upload to "+conf.get("fs.default.name")+"________________________");
//            fs.close();
//        }
    }


}
