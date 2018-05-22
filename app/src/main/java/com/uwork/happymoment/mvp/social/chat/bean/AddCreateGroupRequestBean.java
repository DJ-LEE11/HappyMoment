package com.uwork.happymoment.mvp.social.chat.bean;

/**
 * Created by jie on 2018/5/18.
 */

public class AddCreateGroupRequestBean {
    /**
     * name : string
     * userIds : string
     */

    private String name;
    private String userIds;

    public AddCreateGroupRequestBean(String name, String userIds) {
        this.name = name;
        this.userIds = userIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }
}
