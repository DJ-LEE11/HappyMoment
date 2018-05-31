package com.uwork.happymoment.mvp.main.bean;

/**
 * Created by jie on 2018/5/25.
 */

public class VideoBean {
    private String title;
    private String url;
    private String img;

    public VideoBean(String title, String url, String img) {
        this.title = title;
        this.url = url;
        this.img = img;
    }

    public VideoBean(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
