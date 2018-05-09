package com.uwork.happymoment.mvp.login.contract;

import com.uwork.librx.rx.BaseResult;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/9.
 */

public interface ISendMessageContract {
    interface View {
        void startRegisterCountDown();
    }

    interface Presenter {
        void sendMessage(String phone);
    }

    interface Model {
        //发送验证码
        ResourceSubscriber sendMessage(String phone, OnModelCallBack<BaseResult<String>> callBack);
    }
}
