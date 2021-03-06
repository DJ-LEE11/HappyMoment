package com.example.circle_common.common.entity;

import com.example.circle_base_library.utils.EncryUtil;

import cn.bmob.v3.BmobObject;

/**
 * Created by 大灯泡 on 2016/10/27.
 * <p>
 * 用户
 */

public class UserInfo extends BmobObject {

    private String username;
    private String password;
    private String nick;
    private String avatar;
    private String cover;

    public UserInfo() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return getObjectId();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = EncryUtil.MD5(password);
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nick='" + nick + '\'' +
                ", avatar='" + avatar + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }
}
