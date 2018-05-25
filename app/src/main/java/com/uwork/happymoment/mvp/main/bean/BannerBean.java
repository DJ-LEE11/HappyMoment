package com.uwork.happymoment.mvp.main.bean;

import java.io.Serializable;

/**
 * Created by jie on 2018/5/25.
 */

public class BannerBean implements Serializable{
    /**
     * id : 0
     * picture : string
     * type : 0
     */

    private int id;
    private String picture;
    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
