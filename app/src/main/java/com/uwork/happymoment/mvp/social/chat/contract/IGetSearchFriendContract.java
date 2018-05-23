package com.uwork.happymoment.mvp.social.chat.contract;

import com.uwork.happymoment.mvp.social.chat.bean.SearchFriendBean;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/23.
 */

public interface IGetSearchFriendContract {

    interface View {
        void showFriend(List<SearchFriendBean> searchFriendBeanList);

        void empty();
    }

    interface Presenter {
        void searchFriend(String keyWord);
    }

    interface Model {
        ResourceSubscriber searchFriend(String keyWord, OnModelCallBack<List<SearchFriendBean>> callBack);

    }
}
