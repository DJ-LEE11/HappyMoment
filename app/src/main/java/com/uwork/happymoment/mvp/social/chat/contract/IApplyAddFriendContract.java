package com.uwork.happymoment.mvp.social.chat.contract;

import com.uwork.librx.rx.BaseResult;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/18.
 */

public interface IApplyAddFriendContract {

    interface View {
        void applySuccess();
    }

    interface Presenter {
        void applyAddFriend(String wantFriendId);
    }

    interface Model {
        ResourceSubscriber applyAddFriend(String wantFriendId, OnModelCallBack<BaseResult<String>> callBack);
    }

}
