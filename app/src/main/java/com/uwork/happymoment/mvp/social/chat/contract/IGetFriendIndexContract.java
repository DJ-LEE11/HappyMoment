package com.uwork.happymoment.mvp.social.chat.contract;

import com.uwork.happymoment.mvp.social.chat.bean.FriendIndexBean;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/17.
 */

public interface IGetFriendIndexContract {

    interface View {
        void showFriendIndex(List<FriendIndexBean> friendIndexBeanList);
    }

    interface Presenter {
        void getFriendIndex();
    }

    interface Model {
        ResourceSubscriber getFriendIndex(OnModelCallBack<List<FriendIndexBean>> callBack);
    }
}
