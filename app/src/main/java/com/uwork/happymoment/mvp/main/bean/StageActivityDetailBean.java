package com.uwork.happymoment.mvp.main.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jie on 2018/5/29.
 */

public class StageActivityDetailBean implements Serializable{
    /**
     * address : string
     * contactWay : string
     * createTime : string
     * endTime : string
     * id : 0
     * introduce : string
     * lngLat : string
     * name : string
     * pictureUrl : ["string"]
     * stageActivities : [{"id":0,"name":"string","pictureUrl":"string"}]
     * stageIntroduce : string
     * stageName : string
     * startTime : string
     * storeId : 0
     * videoUrl : ["string"]
     */

    private String address;
    private String contactWay;
    private String createTime;
    private String endTime;
    private int id;
    private String introduce;
    private String lngLat;
    private String name;
    private String stageIntroduce;
    private String stageName;
    private String startTime;
    private int storeId;
    private List<String> pictureUrl;
    private List<StageActivitiesBean> stageActivities;
    private List<String> videoUrl;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getLngLat() {
        return lngLat;
    }

    public void setLngLat(String lngLat) {
        this.lngLat = lngLat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStageIntroduce() {
        return stageIntroduce;
    }

    public void setStageIntroduce(String stageIntroduce) {
        this.stageIntroduce = stageIntroduce;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public List<String> getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(List<String> pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public List<StageActivitiesBean> getStageActivities() {
        return stageActivities;
    }

    public void setStageActivities(List<StageActivitiesBean> stageActivities) {
        this.stageActivities = stageActivities;
    }

    public List<String> getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(List<String> videoUrl) {
        this.videoUrl = videoUrl;
    }

    public static class StageActivitiesBean {
        /**
         * id : 0
         * name : string
         * pictureUrl : string
         */

        private int id;
        private String name;
        private String pictureUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPictureUrl() {
            return pictureUrl;
        }

        public void setPictureUrl(String pictureUrl) {
            this.pictureUrl = pictureUrl;
        }
    }
}
