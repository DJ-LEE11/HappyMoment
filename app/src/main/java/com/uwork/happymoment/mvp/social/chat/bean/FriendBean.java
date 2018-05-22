package com.uwork.happymoment.mvp.social.chat.bean;

import java.io.Serializable;

/**
 * Created by jie on 2018/5/22.
 */

public class FriendBean implements Serializable{

    private String id;
    private String name;
    private String avatar;

    public FriendBean(String id, String name, String avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
