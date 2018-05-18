package com.uwork.happymoment.mvp.social.chat.contract;

import com.uwork.happymoment.mvp.social.chat.bean.NewFriendResponseBean;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/18.
 */

public interface IGetNewFriendContract {

    interface View {
        void showNewFriend(NewFriendResponseBean newFriendResponseBean);
    }

    interface Presenter {
        void getNewFriend();
    }

    interface Model {
        ResourceSubscriber getNewFriend(OnModelCallBack<NewFriendResponseBean> callBack);
    }
}
