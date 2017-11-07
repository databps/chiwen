package com.databps.bigdaf.admin.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * @author haipeng
 * @create 17-10-11 下午2:33
 */
public class AuditDailyStatistics implements Serializable {
    @Id
    private String id;

    @Field("cmpy_id")
    private String cmpyId ="5968802a01cbaa4673823e3d";
    @Field("access_type")
    private String accessType;
    @Field("statistic_date")
    private String statisticDate;
    @Field("filure_count")
    private String filureCount;
    @Field("success_count")
    private String successCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCmpyId() {
        return cmpyId;
    }

    public void setCmpyId(String cmpyId) {
        this.cmpyId = cmpyId;
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

    public String getFilureCount() {
        return filureCount;
    }

    public void setFilureCount(String filureCount) {
        this.filureCount = filureCount;
    }

    public String getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(String successCount) {
        this.successCount = successCount;
    }
}
