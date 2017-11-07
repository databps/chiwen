package com.databps.bigdaf.admin.vo;

import com.databps.bigdaf.admin.domain.AuditDailyStatistics;
import com.databps.bigdaf.admin.domain.FileAccessType;

import java.io.Serializable;
import java.util.List;

/**
 * @author haipeng
 * @create 17-10-12 下午1:26
 */
public class AuditDailyStatisticHomeVo implements Serializable{
    private List<AuditDailyStatistics> auditDailyStatisticsList;
    private  List<FileAccessType>  fileAccessTypeList;

    public List<FileAccessType> getFileAccessTypeList() {
        return fileAccessTypeList;
    }

    public void setFileAccessTypeList(List<FileAccessType> fileAccessTypeList) {
        this.fileAccessTypeList = fileAccessTypeList;
    }

    public List<AuditDailyStatistics> getAuditDailyStatisticsList() {
        return auditDailyStatisticsList;
    }

    public void setAuditDailyStatisticsList(List<AuditDailyStatistics> auditDailyStatisticsList) {
        this.auditDailyStatisticsList = auditDailyStatisticsList;
    }


}
