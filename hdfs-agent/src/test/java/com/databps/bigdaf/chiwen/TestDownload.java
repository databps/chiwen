package com.databps.bigdaf.chiwen;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class TestDownload {
//    Configuration conf = new Configuration();
//    FileSystem fs = FileSystem.get(conf);
//    Path src = new Path("hdfs://192.168.1.235:9000/cool/bb.txt");
//    FSDataInputStream in = fs.open(src);
//    FileOutputStream os = new FileOutputStream("F:\\download\\abbcc.txt");
//    IOUtils.copy(in,os);

    static  Configuration conf = new Configuration(true);
    static{
        //指定hadoop fs的地址
        conf.set("fs.default.name", "hdfs://192.168.1.105:9000");
    }

    @Test
    public void downLoadFileFromHDFS() throws Exception {
        String src = "/user/litao/hadoop/100_2M.txt";
        String outPath = "F:\\download\\down\\"+ UUID.randomUUID().toString()+"data.txt";
        FileSystem fs = FileSystem.get(conf);
        Path srcPath = new Path(src);
        InputStream in = fs.open(srcPath);
        OutputStream out = new FileOutputStream(outPath);
        System.out.println("kaishi" + srcPath);
        try {
            System.out.println("开始" + (System.currentTimeMillis()));

            org.apache.hadoop.io.IOUtils.copyBytes(in, out, 4096, false);
            System.out.println(in);
            System.out.println(out);
        } finally {
            System.out.println("结束" + (System.currentTimeMillis()));
            org.apache.hadoop.io.IOUtils.closeStream(in);
            //fs.close();
        }
    }


}
