package com.databps.bigdaf.admin.manager;

import com.databps.bigdaf.admin.domain.JobInfos;
import com.databps.bigdaf.admin.domain.StatusJob;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobID;
import org.apache.hadoop.mapred.JobStatus;
import org.apache.hadoop.mapred.RunningJob;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yangfan on 17-9-7.
 */
@Component
public class JobStatusManager {


    private static Log log = LogFactory.getLog(JobStatusManager.class);

    /**
     * @1.搜索单个JOB
     * @0.搜索所有JOB
     * @2.条件搜索
     */
    public List<JobInfos> getStatus(String host, Integer port, Configuration conf,
                                    Integer flag, String search) throws IOException {
        InetSocketAddress inetSocket = new InetSocketAddress(host, port);
        List<JobInfos> lj = new ArrayList<>();
        JobClient jobClient = new JobClient(inetSocket, conf);
        JobStatus[] jobsStatus;
        jobsStatus = jobClient.getAllJobs();
        if (flag == 1) {
            JobStatus jobStatus = jobsStatus[0];
            JobInfos jobInfos = getJobInfo(jobStatus.getJobID(), jobStatus, jobClient);
            lj.add(jobInfos);
        }
        if (flag == 0) {
            lj = getAllJobInfo(jobClient);
        }
        if (flag == 2) {
            lj = getCondition(jobClient, search);
        }
        return lj;
    }


    public List<JobInfos> getAllJobInfo(JobClient jobClient) throws IOException {
        List<JobInfos> listJob = new ArrayList<>();
        JobStatus[] jobsStatus;
        jobsStatus = jobClient.getAllJobs();
        if (jobsStatus.length > 0) {
            for (int i = 0; i < jobsStatus.length; i++) {
                JobInfos jobInfos = getJobInfo(jobsStatus[i].getJobID(), jobsStatus[i], jobClient);
                listJob.add(jobInfos);
            }
        }
        return listJob;
    }

    public JobInfos getJobInfo(JobID jobID, JobStatus jobStatus, JobClient jobClient) throws IOException {
        JobInfos jobInfos = null;
        RunningJob runningJob = jobClient.getJob(jobID);
        jobInfos = new JobInfos();
        jobInfos.setJobId(String.valueOf(jobID));
        jobInfos.setJobName(runningJob.getJobName().toString());
        StatusJob sj = stasJob(runningJob.getJobState());
        jobInfos.setRunState(String.valueOf(sj));
        jobInfos.setUserName(jobStatus.getUsername());
        jobInfos.setMapProgress(runningJob.mapProgress());
        jobInfos.setRedProgress(runningJob.reduceProgress());
        return jobInfos;
    }

    public List<JobInfos> getCondition(JobClient jobClient, String searchJob) throws IOException {
        if (searchJob == null) {
            return getAllJobInfo(jobClient);
        }
        List<JobInfos> listJob = new ArrayList<>();
        JobStatus[] jobsStatus;
        jobsStatus = jobClient.getAllJobs();
        if (jobsStatus.length > 0) {
            for (int i = 0; i < jobsStatus.length; i++) {
                JobID jobID = jobsStatus[i].getJobID();
                RunningJob runningJob = jobClient.getJob(jobID);
                //param
                String jobName = runningJob.getJobName().toString();
                String sjstr = String.valueOf(stasJob(runningJob.getJobState()));
                String userName = jobsStatus[i].getUsername();
                String jobIdStr = String.valueOf(jobID);
                Float mapProgress = runningJob.mapProgress();
                Float reduceProgress = runningJob.reduceProgress();
                if (jobIdStr.toLowerCase().contains(searchJob.toLowerCase()) ||
                        jobName.toLowerCase().contains(searchJob.toLowerCase()) ||
                        userName.toLowerCase().contains(searchJob.toLowerCase()) ||
                        sjstr.toLowerCase().contains(searchJob.toLowerCase())
                        ) {
                    JobInfos jobInfos = new JobInfos();
                    jobInfos.setJobId(jobIdStr);
                    jobInfos.setJobName(jobName);
                    jobInfos.setRunState(sjstr);
                    jobInfos.setUserName(userName);
                    jobInfos.setMapProgress(mapProgress);
                    jobInfos.setRedProgress(reduceProgress);
                    listJob.add(jobInfos);
                }
            }
        }
        return listJob;
    }

    private StatusJob stasJob(Integer state) {
        switch (state) {
            case 1:
                return StatusJob.RUNNING;
            case 2:
                return StatusJob.SUCCEEDED;
            case 3:
                return StatusJob.FAILED;
            case 4:
                return StatusJob.PREP;
            case 5:
                return StatusJob.KILLED;
            default:
                return StatusJob.FAILED;
        }
    }
}
