package com.databps.bigdaf.admin.vo;

/**
 * Created by Yangfan on 17-7-4.
 */
public class SubJobVo {

    private String outPath;
    private String inPath;
    private String hdfs;
    private String rm;
    private String jName;
    private String jarPath;
    private String delPath;

    public SubJobVo(String outPath, String inPath, String hdfs, String rm, String jName, String jarPath, String delPath, String jobHistory) {
        this.outPath = outPath;
        this.inPath = inPath;
        this.hdfs = hdfs;
        this.rm = rm;
        this.jName = jName;
        this.jarPath = jarPath;
        this.delPath = delPath;
        this.jobHistory = jobHistory;
    }

    public SubJobVo(String outPath, String inPath, String jName, String jarPath) {
        this.outPath = outPath;
        this.inPath = inPath;
        this.jName = jName;
        this.jarPath = jarPath;
    }


    public String getOutPath() {
        return outPath;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }

    public String getInPath() {
        return inPath;
    }

    public void setInPath(String inPath) {
        this.inPath = inPath;
    }

    public String getHdfs() {
        return hdfs;
    }

    public void setHdfs(String hdfs) {
        this.hdfs = hdfs;
    }

    public String getRm() {
        return rm;
    }

    public void setRm(String rm) {
        this.rm = rm;
    }

    public String getjName() {
        return jName;
    }

    public void setjName(String jName) {
        this.jName = jName;
    }

    public String getJarPath() {
        return jarPath;
    }

    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }

    public String getDelPath() {
        return delPath;
    }

    public void setDelPath(String delPath) {
        this.delPath = delPath;
    }

    public String getJobHistory() {
        return jobHistory;
    }

    public void setJobHistory(String jobHistory) {
        this.jobHistory = jobHistory;
    }

    private  String jobHistory ;



}
