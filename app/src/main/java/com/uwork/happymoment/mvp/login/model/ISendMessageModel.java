package com.uwork.happymoment.mvp.login.model;

import android.content.Context;

import com.uwork.happymoment.BuildConfig;
import com.uwork.happymoment.api.ApiService;
import com.uwork.happymoment.mvp.login.contract.ISendMessageContract;
import com.uwork.librx.mvp.model.IBaseModelImpl;
import com.uwork.librx.rx.BaseResult;
import com.uwork.librx.rx.http.ApiServiceFactory;
import com.uwork.librx.rx.http.CustomResourceSubscriber;
import com.uwork.librx.rx.http.HttpResultFunc;
import com.uwork.librx.rx.http.ServerResultWithDataFunc;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/9.
 */

public class ISendMessageModel extends IBaseModelImpl implements ISendMessageContract.Model {

    public ISendMessageModel(Context context) {
        super(context);
    }

    @Override
    public ResourceSubscriber sendMessage(String phone, OnModelCallBack<BaseResult<String>> callBack) {
        return startObservable(ApiServiceFactory.INSTANCE
                .create(mContext, BuildConfig.API_BASE_URL, ApiService.class)
                .sendMessage(phone)
                .map(new ServerResultWithDataFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>()), new CustomResourceSubscriber<>(callBack));
    }
}
