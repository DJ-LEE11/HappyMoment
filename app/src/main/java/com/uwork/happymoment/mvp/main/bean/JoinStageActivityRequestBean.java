package com.uwork.happymoment.mvp.main.bean;

import java.io.Serializable;

/**
 * Created by jie on 2018/5/29.
 */

public class JoinStageActivityRequestBean implements Serializable{
    /**
     * activityId : 0
     * address : string
     * contactName : string
     * contactPhone : string
     * endTime : string
     * startTime : string
     */

    private int activityId;
    private String address;
    private String contactName;
    private String contactPhone;
    private String startTime;
    private String endTime;

    public JoinStageActivityRequestBean(int activityId, String address, String contactName, String contactPhone, String startTime, String endTime) {
        this.activityId = activityId;
        this.address = address;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
