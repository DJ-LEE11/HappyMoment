package com.uwork.happymoment.mvp.social.chat.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.uwork.happymoment.adapter.BaseMultipleAdapter;

import java.io.Serializable;

/**
 * Created by jie on 2018/5/18.
 */

public class NewFriendBean implements Serializable, MultiItemEntity {
    /**
     * id : 3
     * avatar :
     * nickName : 栋杰
     * remarksName : 栋杰8
     */

    private String id;
    private String avatar;
    private String nickName;
    private String remarksName;
    private boolean mIsAdd;

    //还没添加
    public NewFriendBean(String id, String avatar, String nickName) {
        this.id = id;
        this.avatar = avatar;
        this.nickName = nickName;
        mIsAdd = false;
    }

    //已添加
    public NewFriendBean(String id, String avatar, String nickName, String remarksName) {
        this.id = id;
        this.avatar = avatar;
        this.nickName = nickName;
        this.remarksName = remarksName;
        mIsAdd = true;
    }

    @Override
    public int getItemType() {
        return mIsAdd? BaseMultipleAdapter.TYPE_ITEM2:BaseMultipleAdapter.TYPE_ITEM1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRemarksName() {
        return remarksName;
    }

    public void setRemarksName(String remarksName) {
        this.remarksName = remarksName;
    }
}
