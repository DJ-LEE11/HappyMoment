package com.uwork.happymoment.mvp.my.contract;

import com.uwork.librx.rx.BaseResult;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/24.
 */

public interface ILogoutContract {

    interface View {
        void logoutSuccess();
    }

    interface Presenter {
        void logout();
    }

    interface Model {
        ResourceSubscriber logout(OnModelCallBack<BaseResult<String>> callBack);
    }
}
