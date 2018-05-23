package com.uwork.happymoment.mvp.social.chat.bean;

/**
 * Created by jie on 2018/5/23.
 */

public class GroupMemberBean {
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
