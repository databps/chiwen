package com.databps.bigdaf.admin.service;

import com.databps.bigdaf.admin.domain.JobInfos;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Created by Yangfan on 17-6-26.
 */
public interface SendboxService {


    public void montage(Map<String, Object> omap)throws IOException,TimeoutException;

    public List<JobInfos> monitorAlljob()throws IOException,TimeoutException;

    public Map<String, Object> getConfMap(String cmpyid)throws IOException,TimeoutException;

    List<JobInfos> searchJob(String searchJob) throws IOException, TimeoutException;
}
