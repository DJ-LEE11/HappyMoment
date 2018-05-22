package com.uwork.happymoment.manager;

import android.content.Context;

import com.uwork.happymoment.mvp.social.chat.bean.FriendBean;
import com.uwork.libutil.SharedFileUtils;

/**
 * Created by jie on 2018/5/22.
 */

public class FriendManager {

    private static FriendManager mInstance;

    private FriendManager() {
    }

    public static FriendManager getInstance() {
        if (mInstance == null) {
            synchronized (FriendManager.class) {
                if (mInstance == null) {
                    mInstance = new FriendManager();
                }
            }
        }
        return mInstance;
    }

    private SharedFileUtils mSharedFileUtils;
    public static final String FRIEND_ID = "FRIEND_ID";

    /**
     * 保存朋友
     */
    public void saveFriend(Context context,String friendId ,FriendBean friendBean){
        if (mSharedFileUtils == null){
            mSharedFileUtils = new SharedFileUtils(context);
        }
        mSharedFileUtils.putBean(FRIEND_ID+friendId,friendBean);
    }

    /**
     * 获取朋友
     */
    public FriendBean getFriend(Context context,String friendId){
        if (mSharedFileUtils == null){
            mSharedFileUtils = new SharedFileUtils(context);
        }
        return (FriendBean) mSharedFileUtils.getBean(FRIEND_ID+friendId);
    }
}
