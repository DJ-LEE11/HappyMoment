package com.uwork.happymoment.mvp.social.chat.bean;

/**
 * Created by jie on 2018/5/18.
 */

public class SearchNewFriendForCodeRequestBean {

    private String qrCode;

    public SearchNewFriendForCodeRequestBean(String qrCode) {
        this.qrCode = qrCode;
    }


    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
