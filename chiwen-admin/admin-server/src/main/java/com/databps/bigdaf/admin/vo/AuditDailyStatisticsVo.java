package com.databps.bigdaf.admin.vo;

import java.io.Serializable;

/**
 * @author haipeng
 * @create 17-10-11 下午1:44
 */
public class AuditDailyStatisticsVo implements Serializable {

    private int filureCount;
    private int successCount;
    private String accessType;
    private String statisticDate;

    public int getFilureCount() {
        return filureCount;
    }

    public void setFilureCount(int filureCount) {
        this.filureCount = filureCount;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getStatisticDate() {
        return statisticDate;
    }

    public void setStatisticDate(String statisticDate) {
        this.statisticDate = statisticDate;
    }
}
