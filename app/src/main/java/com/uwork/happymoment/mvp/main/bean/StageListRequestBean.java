package com.uwork.happymoment.mvp.main.bean;

import java.io.Serializable;

/**
 * Created by jie on 2018/5/31.
 */

public class StageListRequestBean implements Serializable{
    /**
     * location : 113.353739,22.526847
     * name :
     */

    private String location;
    private String name;

    public StageListRequestBean(String location, String name) {
        this.location = location;
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
