package com.uwork.happymoment.mvp.social.chat.bean;

/**
 * Created by jie on 2018/5/18.
 */

public class SearchNewFriendForPhoneRequestBean {
    /**
     * account : string
     * phone : string
     * qrCode : string
     */

    private String phone;

    public SearchNewFriendForPhoneRequestBean(String phone) {
        this.phone = phone;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
