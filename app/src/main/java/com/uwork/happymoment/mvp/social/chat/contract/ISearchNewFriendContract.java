package com.uwork.happymoment.mvp.social.chat.contract;

import com.uwork.happymoment.mvp.social.chat.bean.SearchNewFriendBean;
import com.uwork.librx.rx.BaseResult;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/18.
 */

public interface ISearchNewFriendContract {

    interface View {
        void showNewFriend(SearchNewFriendBean searchNewFriendBean);

        void empty();
    }

    interface Presenter {
        void searchNewFriendForPhone(String phone);

        void searchNewFriendForCode(String qrCode);
    }

    interface Model {
        ResourceSubscriber searchNewFriendForPhone(String phone, OnModelCallBack<BaseResult<SearchNewFriendBean>> callBack);

        ResourceSubscriber searchNewFriendForCode(String qrCode, OnModelCallBack<BaseResult<SearchNewFriendBean>> callBack);
    }
}
