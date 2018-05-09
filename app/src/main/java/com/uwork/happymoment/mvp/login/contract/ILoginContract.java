package com.uwork.happymoment.mvp.login.contract;

import com.uwork.happymoment.mvp.login.bean.UserBean;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

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
    }

    interface Model {
        //登录
        ResourceSubscriber login(String phone, String password, OnModelCallBack<UserBean> callBack);
    }
}
