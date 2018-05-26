package com.uwork.happymoment.mvp.main.bean;

import android.graphics.Bitmap;

/**
 * Created by jie on 2018/5/25.
 */

public class VideoBean {
    private String title;
    private String url;
    private Bitmap bitmap;

    public VideoBean(String title, String url, Bitmap bitmap) {
        this.title = title;
        this.url = url;
        this.bitmap = bitmap;
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

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
