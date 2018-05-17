package com.uwork.happymoment.mvp.social.chat.bean;

/**
 * Created by jie on 2018/5/17.
 */

public class UpdateFriendInfoRequestBean {
    /**
     * friendId : 5
     * phone : 1111
     * remarksName : 222
     */

    private String friendId;
    private String phone;
    private String remarksName;

    public UpdateFriendInfoRequestBean(String friendId, String phone, String remarksName) {
        this.friendId = friendId;
        this.phone = phone;
        this.remarksName = remarksName;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRemarksName() {
        return remarksName;
    }

    public void setRemarksName(String remarksName) {
        this.remarksName = remarksName;
    }
}
