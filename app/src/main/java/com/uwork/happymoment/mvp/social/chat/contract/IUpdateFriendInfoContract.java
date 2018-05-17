package com.uwork.happymoment.mvp.social.chat.contract;

import com.uwork.librx.rx.BaseResult;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/17.
 */

public interface IUpdateFriendInfoContract {

    interface View {
        void updateFriendInfoSuccess();
    }

    interface Presenter {
        void updateFriendInfo(String friendId, String phone, String remarksName);
    }

    interface Model {
        ResourceSubscriber updateFriendInfo(String friendId, String phone, String remarksName, OnModelCallBack<BaseResult<String>> callBack);
    }
}
