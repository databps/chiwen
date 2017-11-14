package com.databps.bigdaf.chiwen;


import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

/**
 * 测试文件：
 * @author z714303584
 *
 * HDFS文件上传下载实例
 */
public class HbaseTest {

    //hadoop fs的配置文件
    static  Configuration conf = new Configuration(true);
    static{
        //指定hadoop fs的地址
        conf.set("fs.default.name", "hdfs://192.168.1.235:9000");
    }

    /**
     * 将本地文件(filePath)上传到HDFS服务器的指定路径(dst)
     * @param filePath
     * @param dst
     * @throws Exception
     */
    public static void uploadFileToHDFS(String filePath,String dst) throws Exception {
        //创建一个文件系统
        FileSystem fs = FileSystem.get(conf);
        Path srcPath = new Path(filePath);
        Path dstPath = new Path(dst);
        Long start = System.currentTimeMillis();
        fs.copyFromLocalFile(false, srcPath, dstPath);
        System.out.println("Time:"+ (System.currentTimeMillis() - start));

        System.out.println("________________________Upload to "+conf.get("fs.default.name")+"________________________");
        fs.close();
        getDirectoryFromHdfs(dst);
    }
    /**
     * 下载文件
     * @param src
     * @throws Exception
     */
    public static void downLoadFileFromHDFS(String src,String outPath) throws Exception {
        FileSystem fs = FileSystem.get(conf);
        Path  srcPath = new Path(src);
        InputStream in = fs.open(srcPath);
        OutputStream out = new FileOutputStream(outPath);
        try {
            //将文件COPY到标准输出(即控制台输出)
            IOUtils.copyBytes(in,out, 4096,false);
        }finally{
            IOUtils.closeStream(in);
            fs.close();
        }
    }
    /**
     * 遍历指定目录(direPath)下的所有文件
     * @param direPath
     * @throws Exception
     */
    public static void  getDirectoryFromHdfs(String direPath) throws Exception{

        FileSystem fs = FileSystem.get(URI.create(direPath),conf);
        FileStatus[] filelist = fs.listStatus(new Path(direPath));
        for (int i = 0; i < filelist.length; i++) {
            System.out.println("_________________***********************____________________");
            FileStatus fileStatus = filelist[i];
            System.out.println("Name:"+fileStatus.getPath().getName());
            System.out.println("size:"+fileStatus.getLen());
            System.out.println("_________________***********************____________________");
        }
        fs.close();
    }
    /**
     * 测试方法
     * @param args
     */
    public static void main(String[] args) {
        try {
//          getDirectoryFromHdfs("/cool/");

          uploadFileToHDFS("F:/download/upload/aabbcc.txt", "/cool/");

            downLoadFileFromHDFS("/cool/cc.txt","F:\\download\\down\\"+"data1.txt");


        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

}
