package com.uwork.happymoment.mvp.social.chat.bean;

import java.io.Serializable;

/**
 * Created by jie on 2018/5/18.
 */

public class AddGroupBean implements Serializable{
    /**
     * createTime : string
     * id : 0
     * name : string
     * updateTime : string
     * userIds : string
     */

    private String createTime;
    private int id;
    private String name;
    private String updateTime;
    private String userIds;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }
}
