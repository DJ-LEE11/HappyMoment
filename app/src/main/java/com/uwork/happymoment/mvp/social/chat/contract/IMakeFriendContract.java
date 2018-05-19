package com.uwork.happymoment.mvp.social.chat.contract;

import com.uwork.librx.rx.BaseResult;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/18.
 */

public interface IMakeFriendContract {

    interface View {
        void makeFriendSuccess();
    }

    interface Presenter {
        void makeFriend(Integer friendId);
    }

    interface Model {
        ResourceSubscriber makeFriend(Integer friendId, OnModelCallBack<BaseResult<String>> callBack);
    }
}
