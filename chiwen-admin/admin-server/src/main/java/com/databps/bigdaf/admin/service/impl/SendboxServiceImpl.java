package com.databps.bigdaf.admin.service.impl;

import com.databps.bigdaf.admin.dao.ServicesDao;
import com.databps.bigdaf.admin.domain.JobInfos;
import com.databps.bigdaf.admin.manager.JobStatusManager;
import com.databps.bigdaf.admin.service.SendboxService;
import com.databps.bigdaf.admin.vo.SubJobVo;
import com.databps.bigdaf.core.common.AuditType;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.Map;

/**
 * Created by Yangfan on 17-6-26.
 */
@Service
public class SendboxServiceImpl implements SendboxService, Serializable, Runnable {

    private static final long serialVersionUID = 7526471155622776147L;
    private static Log log = LogFactory.getLog(SendboxServiceImpl.class);
    private SubJobVo subJobVo;
    private Configuration conf;
    private String cmpyId = "5968802a01cbaa46738eee3d";
    @Autowired
    private ServicesDao servicesDao;
    @Autowired
    private JobStatusManager jobStatusManager;

    public void setSubJobVo(SubJobVo subJobVo) {
        this.subJobVo = subJobVo;
    }


    @Override
    public Map<String, Object> getConfMap(String cmpyid) throws IOException {
        Map<String, String> map = servicesDao.getServiceConfigMap(cmpyid, AuditType.HDFS.getName());
        Map<String, Object> strObj = new HashedMap();
        if (MapUtils.isEmpty(map)) {
            return strObj;
        }
        String fsDefaultName = map.get("hdfs_default_name");
        String[] hostPort = fsDefaultName.split(":");
        String host = hostPort[0];
        Integer port = Integer.valueOf(hostPort[1]);
        String yarnResoManager = map.get("yarn_rm_hostname");
        String mjh = map.get("mapreduce_job_hostname");
        if (StringUtils.isEmpty(mjh) || StringUtils.isEmpty(fsDefaultName)
                || StringUtils.isEmpty(yarnResoManager)
                || hostPort.length < 2) {
            return strObj;
        }
        String[] mjhHost = mjh.split(":");
        Integer flag = tryInetSocket(mjhHost[0], Integer.parseInt(mjhHost[1]));
        if (flag == 0) {
            return strObj;
        }
        Integer f2 = tryInetSocket(host, port);
        if (f2 == 0) {
            return strObj;
        }

        conf = new Configuration();
        conf.set("mapreduce.framework.name", "yarn");
        conf.set("fs.default.name", "hdfs://" + fsDefaultName);
        conf.set("yarn.resourcemanager.hostname", yarnResoManager);
        conf.set("mapreduce.jobhistory.address", mjh);

        strObj.put("conf", conf);
        strObj.put("host", host);
        strObj.put("port", port);
        return strObj;
    }

    public Integer tryInetSocket(String host, Integer port) throws IOException {
        Integer flag = 1;
        try {
            Socket socket = new Socket();
            InetSocketAddress inetSocket = new InetSocketAddress(host, port);
            socket.connect(inetSocket, 5000);
            socket.close();
        } catch (IOException e) {
            flag = 0;
        }
        return flag;
    }

    @Override
    public List<JobInfos> searchJob(String searchJob) throws IOException {
        Map<String, Object> map = null;
        map = getConfMap(cmpyId);
        if (MapUtils.isEmpty(map)) {
            return (List<JobInfos>) map;
        }
        conf = (Configuration) map.get("conf");
        String host = (String) map.get("host");
        Integer port = (Integer) map.get("port");
        return jobStatusManager.getStatus(host, port, conf, 2, searchJob);
    }

    @Override
    public void montage(Map<String, Object> omap) throws IOException {
        /**
         * 由界面输入参数/进行执行Job 和 查询 job
         */
        String outPath = (String) omap.get("outputPath");
        String inPath = (String) omap.get("inputPath");
        String jName = (String) omap.get("jName");
        String jarPath = (String) omap.get("jarPath");
        Map<String, Object> map = getConfMap(cmpyId);
        if (MapUtils.isEmpty(map)) {
            return;
        }
        conf = (Configuration) map.get("conf");
        SendboxServiceImpl senboxThread = new SendboxServiceImpl();
        subJobVo = new SubJobVo(outPath, inPath, jName, jarPath);
        senboxThread.setSubJobVo(subJobVo);
        senboxThread.conf = conf;
        Thread thread = new Thread(senboxThread);
        thread.setName("HADOOP_JOB_RUNNABLE");
        thread.start();
    }


    @Override
    public List<JobInfos> monitorAlljob() throws IOException {
        List<JobInfos> ljs = null;
        Map<String, Object> map = getConfMap(cmpyId);
        if (MapUtils.isEmpty(map)) {
            return ljs;
        }
        conf = (Configuration) map.get("conf");
        String host = (String) map.get("host");
        Integer port = (Integer) map.get("port");
        ljs = jobStatusManager.getStatus(host, port, conf, 0, null);
        return ljs;
    }

    @Override
    public void run() {
        Job job = null;
        try {
            job = Job.getInstance(conf, subJobVo.getjName());
            FileSystem fs = FileSystem.get(conf);
            Path pOut = new Path(subJobVo.getOutPath());
            if (fs.exists(pOut)) {
                fs.delete(pOut, true);
            }
            Path pIn = new Path(subJobVo.getInPath());
            job.setJar(subJobVo.getJarPath());
            FileInputFormat.addInputPath(job, pIn);
            FileOutputFormat.setOutputPath(job, pOut);
            Integer success = (job.waitForCompletion(true) ? 0 : 1);
            if (success == 1) {
                File delFile = new File(subJobVo.getJarPath());
                if (delFile.exists()) {
                    delFile.delete();
                }
            }
            log.info("Map reduce Job exit ......");
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }
}
