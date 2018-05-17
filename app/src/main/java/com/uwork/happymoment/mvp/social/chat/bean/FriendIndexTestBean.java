package com.uwork.happymoment.mvp.social.chat.bean;

import com.example.libindex.IndexBar.bean.BaseIndexPinyinBean;

/**
 * Created by jie on 2018/5/14.
 */

public class FriendIndexTestBean extends BaseIndexPinyinBean {

    private String mId;
    private String mNickName;
    private String mAvatar;

    public FriendIndexTestBean() {
    }

    public FriendIndexTestBean(String id, String nickName, String avatar) {
        mId = id;
        mNickName = nickName;
        mAvatar = avatar;
    }

    @Override
    public String getTarget() {
        return mNickName;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getNickName() {
        return mNickName;
    }

    public void setNickName(String nickName) {
        mNickName = nickName;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }
}
