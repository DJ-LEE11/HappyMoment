package com.uwork.happymoment.mvp.social.chat.bean;

/**
 * Created by jie on 2018/5/18.
 */

public class ApplyAddFriendRequestBean {
    /**
     * wantFriendId : 3
     */

    private String wantFriendId;

    public ApplyAddFriendRequestBean(String wantFriendId) {
        this.wantFriendId = wantFriendId;
    }

    public String getWantFriendId() {
        return wantFriendId;
    }

    public void setWantFriendId(String wantFriendId) {
        this.wantFriendId = wantFriendId;
    }
}
