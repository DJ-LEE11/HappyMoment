package com.uwork.happymoment.mvp.social.circle.bean;

import java.util.List;

/**
 * Created by jie on 2018/5/20.
 */

public class SendCircleRequestBean {
    /**
     * city : string
     * content : string
     * latitude : string
     * location : string
     * longitude : string
     * picture : ["string"]
     * userId : 0
     */

    private int userId;
    private String content;
    private String picture2;
    private String city;
    private String location;
    private String latitude;
    private String longitude;
    private List<String> picture;

    public SendCircleRequestBean(int userId, String content, String picture2, String city, String location, String latitude, String longitude, List<String> picture) {
        this.userId = userId;
        this.content = content;
        this.picture2 = picture2;
        this.city = city;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.picture = picture;
    }


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

    public String getPicture2() {
        return picture2;
    }

    public void setPicture2(String picture2) {
        this.picture2 = picture2;
    }
}
