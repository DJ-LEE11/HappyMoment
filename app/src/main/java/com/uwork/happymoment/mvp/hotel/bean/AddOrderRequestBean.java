package com.uwork.happymoment.mvp.hotel.bean;

import java.io.Serializable;

/**
 * Created by jie on 2018/5/31.
 */

public class AddOrderRequestBean implements Serializable{
    /**
     * contactName : string
     * contactPhone : string
     * endTime : string
     * hostelRoomId : 0
     * num : 0
     * setMealId : 0
     * startTime : string
     * useIntegral : 0
     */

    private String contactName;
    private String contactPhone;
    private String endTime;
    private int hostelRoomId;
    private int num;
    private int setMealId;
    private String startTime;
    private int useIntegral;

    public AddOrderRequestBean(String contactName, String contactPhone, String endTime, int hostelRoomId, int num, int setMealId, String startTime, int useIntegral) {
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.endTime = endTime;
        this.hostelRoomId = hostelRoomId;
        this.num = num;
        this.setMealId = setMealId;
        this.startTime = startTime;
        this.useIntegral = useIntegral;
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

    public int getHostelRoomId() {
        return hostelRoomId;
    }

    public void setHostelRoomId(int hostelRoomId) {
        this.hostelRoomId = hostelRoomId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getSetMealId() {
        return setMealId;
    }

    public void setSetMealId(int setMealId) {
        this.setMealId = setMealId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getUseIntegral() {
        return useIntegral;
    }

    public void setUseIntegral(int useIntegral) {
        this.useIntegral = useIntegral;
    }
}
