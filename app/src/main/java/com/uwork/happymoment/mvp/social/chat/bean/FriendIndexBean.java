package com.uwork.happymoment.mvp.social.chat.bean;

import android.text.TextUtils;

import com.example.libindex.IndexBar.bean.BaseIndexPinyinBean;

import java.io.Serializable;

/**
 * Created by jie on 2018/5/17.
 */

public class FriendIndexBean extends BaseIndexPinyinBean implements Serializable{

    /**
     * avatar : string
     * id : 0
     * nickName : string
     * remarksName : string
     */

    private String avatar;
    private int id;
    private String nickName;
    private String remarksName;

    @Override
    public String getTarget() {
        return TextUtils.isEmpty(remarksName)?nickName:remarksName;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRemarksName() {
        return remarksName;
    }

    public void setRemarksName(String remarksName) {
        this.remarksName = remarksName;
    }
}
