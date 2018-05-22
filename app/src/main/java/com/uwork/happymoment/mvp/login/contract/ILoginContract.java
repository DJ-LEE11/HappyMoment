package com.uwork.happymoment.mvp.login.contract;

import com.uwork.happymoment.mvp.login.bean.UserBean;
import com.uwork.happymoment.mvp.social.chat.bean.FriendIndexBean;
import com.uwork.happymoment.mvp.social.chat.bean.GroupBean;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/9.
 */

public interface ILoginContract {

    interface View {
        void loginSuccess();
    }

    interface Presenter {
        void login(String phone, String password);

        void saveToken(UserBean userBean);

        void getFriend();

        void getGroup();

        void connectIM(UserBean userBean);
    }

    interface Model {
        //登录
        ResourceSubscriber login(String phone, String password, OnModelCallBack<UserBean> callBack);

        //获取好友列表
        ResourceSubscriber getFriendIndex(OnModelCallBack<List<FriendIndexBean>> callBack);

        //获取群组列表
        ResourceSubscriber getGroupList(OnModelCallBack<List<GroupBean>> callBack);

    }
}
