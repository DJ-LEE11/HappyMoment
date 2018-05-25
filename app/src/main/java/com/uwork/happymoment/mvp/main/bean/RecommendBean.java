package com.uwork.happymoment.mvp.main.bean;

import java.io.Serializable;

/**
 * Created by jie on 2018/5/25.
 */

public class RecommendBean implements Serializable{
    /**
     * id : 1
     * title : 养老
     * imgLink : 安达市大大大所多
     */

    private int id;
    private String title;
    private String imgLink;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }
}
