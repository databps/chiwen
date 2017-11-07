package com.databps.bigdaf.admin.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * @author haipeng
 * @create 17-10-18 上午11:33
 */
public class AuditHomePage implements Serializable{
    @Id
    private String id;
    @Field("cmpy_id")
    private String cmpyId ="5968802a01cbaa46738eee3d";
    @Field("today_access")
    private String todayAccess;
    @Field("ip_total_access")
    private String ipTotalAccess;
    @Field("today_violation")
    private String todayViolation;
    @Field("total_violation")
    private String totalViolation;
    @Field("high_day")
    private String highDay;
    @Field("day")
    private String day;
    @Field("high_hour")
    private String highhour;
    @Field("hour")
    private String hour;
    @Field("runtime")
    private String runtime;
    @Field("loophole_url")
    private String loopholeUrl;
    @Field("loophole_score")
    private int loopholeScore;

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

    public String getTodayAccess() {
        return todayAccess;
    }

    public void setTodayAccess(String todayAccess) {
        this.todayAccess = todayAccess;
    }

    public String getIpTotalAccess() {
        return ipTotalAccess;
    }

    public void setIpTotalAccess(String ipTotalAccess) {
        this.ipTotalAccess = ipTotalAccess;
    }

    public String getTodayViolation() {
        return todayViolation;
    }

    public void setTodayViolation(String todayViolation) {
        this.todayViolation = todayViolation;
    }

    public String getTotalViolation() {
        return totalViolation;
    }

    public void setTotalViolation(String totalViolation) {
        this.totalViolation = totalViolation;
    }

    public String getHighDay() {
        return highDay;
    }

    public void setHighDay(String highDay) {
        this.highDay = highDay;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHighhour() {
        return highhour;
    }

    public void setHighhour(String highhour) {
        this.highhour = highhour;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getLoopholeUrl() {
        return loopholeUrl;
    }

    public void setLoopholeUrl(String loopholeUrl) {
        this.loopholeUrl = loopholeUrl;
    }

    public int getLoopholeScore() {
        return loopholeScore;
    }

    public void setLoopholeScore(int loopholeScore) {
        this.loopholeScore = loopholeScore;
    }
}
