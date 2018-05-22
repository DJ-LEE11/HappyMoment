package com.uwork.happymoment.mvp.social.chat.bean;

import java.io.Serializable;

/**
 * Created by jie on 2018/5/22.
 */

public class GroupBean implements Serializable{

    /**
     * avatar : string
     * id : 0
     * name : string
     */

    private int id;
    private String name;
    private String avatar;

    public GroupBean(){}

    public GroupBean(int id, String name, String avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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
}
