package com.uwork.happymoment.mvp.login.contract;

import com.uwork.librx.rx.BaseResult;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/9.
 */

public interface IRegisterContract {

    interface View {
        void registerSuccess();
    }

    interface Presenter {
        void register(String code, String password, String phone, String sharingCode);
    }

    interface Model {
        ResourceSubscriber register(String code, String password, String phone, String sharingCode, OnModelCallBack<BaseResult<String>> callBack);
    }
}
