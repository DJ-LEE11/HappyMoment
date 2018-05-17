package com.uwork.happymoment.mvp.social.chat.contract;

import com.uwork.happymoment.mvp.social.chat.bean.FriendDetailBean;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/17.
 */

public interface IGetFriendDetailContract {

    interface View {
        void showDetail(FriendDetailBean friendDetailBean);

        void getDetailFail();
    }

    interface Presenter {
        void getFriendDetail(String friendId);
    }

    interface Model {
        ResourceSubscriber getFriendDetail(String friendId,OnModelCallBack<FriendDetailBean> callBack);
    }
}
