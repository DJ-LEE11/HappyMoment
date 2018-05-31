package com.uwork.happymoment.mvp.my.bean;

import java.io.Serializable;

/**
 * Created by jie on 2018/5/31.
 */

public class UpdateUserInfoRequestBean implements Serializable{
    /**
     * avatar : string
     * nickName : string
     * sex : 0
     * sex (integer, optional): 性别 1为男 2为女
     */

    private String avatar;
    private String nickName;
    private int sex;

    public UpdateUserInfoRequestBean(String avatar, String nickName, int sex) {
        this.avatar = avatar;
        this.nickName = nickName;
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
