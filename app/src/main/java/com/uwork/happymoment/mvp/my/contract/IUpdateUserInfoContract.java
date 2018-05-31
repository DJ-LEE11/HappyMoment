package com.uwork.happymoment.mvp.my.contract;

import com.uwork.librx.rx.BaseResult;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/31.
 */

public interface IUpdateUserInfoContract {

    interface View {
        void updateUserInfoSuccess();
    }

    interface Presenter {
        void updateUserInfo(String avatar, String nickName, int sex);
    }

    interface Model {
        ResourceSubscriber updateUserInfo(String avatar, String nickName, int sex,OnModelCallBack<BaseResult<String>> callBack);
    }
}
