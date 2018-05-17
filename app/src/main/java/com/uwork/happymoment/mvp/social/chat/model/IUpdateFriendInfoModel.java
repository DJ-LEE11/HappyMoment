package com.uwork.happymoment.mvp.social.chat.model;

import android.content.Context;

import com.uwork.happymoment.BuildConfig;
import com.uwork.happymoment.api.ApiService;
import com.uwork.happymoment.mvp.social.chat.bean.UpdateFriendInfoRequestBean;
import com.uwork.happymoment.mvp.social.chat.contract.IUpdateFriendInfoContract;
import com.uwork.librx.mvp.model.IBaseModelImpl;
import com.uwork.librx.rx.BaseResult;
import com.uwork.librx.rx.http.ApiServiceFactory;
import com.uwork.librx.rx.http.CustomResourceSubscriber;
import com.uwork.librx.rx.http.HttpResultFunc;
import com.uwork.librx.rx.http.ServerResultWithDataFunc;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/17.
 */

public class IUpdateFriendInfoModel extends IBaseModelImpl implements IUpdateFriendInfoContract.Model {

    public IUpdateFriendInfoModel(Context context) {
        super(context);
    }

    @Override
    public ResourceSubscriber updateFriendInfo(String friendId, String phone, String remarksName, OnModelCallBack<BaseResult<String>> callBack) {
        return startObservable(ApiServiceFactory.INSTANCE
                .create(mContext, BuildConfig.API_BASE_URL, ApiService.class)
                .updateFriendInfo(new UpdateFriendInfoRequestBean(friendId,phone,remarksName))
                .map(new ServerResultWithDataFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>()), new CustomResourceSubscriber<>(callBack));
    }
}
