package com.uwork.happymoment.manager;

import android.content.Context;

import com.uwork.happymoment.mvp.social.chat.bean.GroupBean;
import com.uwork.libutil.SharedFileUtils;

/**
 * Created by jie on 2018/5/22.
 */

public class GroupManager {
    private static GroupManager mInstance;

    private GroupManager() {
    }

    public static GroupManager getInstance() {
        if (mInstance == null) {
            synchronized (GroupManager.class) {
                if (mInstance == null) {
                    mInstance = new GroupManager();
                }
            }
        }
        return mInstance;
    }

    private SharedFileUtils mSharedFileUtils;
    public static final String GROUP_ID = "GROUP_ID";

    /**
     * 保存群组
     */
    public void saveGroup(Context context, String groupId , GroupBean groupBean){
        if (mSharedFileUtils == null){
            mSharedFileUtils = new SharedFileUtils(context);
        }
        mSharedFileUtils.putBean(GROUP_ID+groupId,groupBean);
    }

    /**
     * 获取群组
     */
    public GroupBean getGroup(Context context,String groupId){
        if (mSharedFileUtils == null){
            mSharedFileUtils = new SharedFileUtils(context);
        }
        return (GroupBean) mSharedFileUtils.getBean(GROUP_ID+groupId);
    }
}
