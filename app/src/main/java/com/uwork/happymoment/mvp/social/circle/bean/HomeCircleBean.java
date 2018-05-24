package com.uwork.happymoment.mvp.social.circle.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jie on 2018/5/23.
 */

public class HomeCircleBean implements Serializable{
    /**
     * city : string
     * content : string
     * createTime : string
     * id : 0
     * latitude : string
     * location : string
     * longitude : string
     * picture : ["string"]
     * userId : 0
     */

    private String city;
    private String content;
    private String createTime;
    private int id;
    private String latitude;
    private String location;
    private String longitude;
    private int userId;
    private List<String> picture;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<String> getPicture() {
        return picture;
    }

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }
}
