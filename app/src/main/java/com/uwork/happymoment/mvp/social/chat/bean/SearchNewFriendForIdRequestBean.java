package com.uwork.happymoment.mvp.social.chat.bean;

/**
 * Created by jie on 2018/5/18.
 */

public class SearchNewFriendForIdRequestBean {

    private String serachUserId;

    public SearchNewFriendForIdRequestBean(String serachUserId) {
        this.serachUserId = serachUserId;
    }

    public String getSerachUserId() {
        return serachUserId;
    }

    public void setSerachUserId(String serachUserId) {
        this.serachUserId = serachUserId;
    }
}
