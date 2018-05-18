package com.uwork.happymoment.mvp.social.chat.bean;

import java.util.List;

/**
 * Created by jie on 2018/5/18.
 */

public class AddCreateGroupRequestBean {
    /**
     * name : hahah
     * userIds : [1,3,6]
     */

    private String name;
    private List<String> userIds;

    public AddCreateGroupRequestBean(String name, List<String> userIds) {
        this.name = name;
        this.userIds = userIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }
}
