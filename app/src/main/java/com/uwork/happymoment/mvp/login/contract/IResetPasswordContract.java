package com.uwork.happymoment.mvp.login.contract;

import com.uwork.librx.rx.BaseResult;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/9.
 */

public interface IResetPasswordContract {

    interface View {
        void resetPasswordSuccess();
    }

    interface Presenter {
        void resetPassword(String phone, String code, String password);
    }

    interface Model {
        ResourceSubscriber resetPassword(String phone, String code, String password, OnModelCallBack<BaseResult<String>> callBack);
    }
}
