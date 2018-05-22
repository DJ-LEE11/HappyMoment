package com.uwork.happymoment.mvp.social.circle.model;

import android.content.Context;

import com.uwork.happymoment.BuildConfig;
import com.uwork.happymoment.api.ApiService;
import com.uwork.happymoment.mvp.social.circle.contract.ICircleHandelContract;
import com.uwork.librx.mvp.model.IBaseModelImpl;
import com.uwork.librx.rx.http.ApiServiceFactory;
import com.uwork.librx.rx.http.CustomResourceSubscriber;
import com.uwork.librx.rx.http.HttpResultFunc;
import com.uwork.librx.rx.http.ServerResultFunc;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/22.
 */

public class ICircleHandelModel extends IBaseModelImpl implements ICircleHandelContract.Model {

    public ICircleHandelModel(Context context) {
        super(context);
    }

    @Override
    public ResourceSubscriber giveLikeMoment(Integer messageId, OnModelCallBack<Boolean> callBack) {
        return startObservable(ApiServiceFactory.INSTANCE
                .create(mContext, BuildConfig.API_BASE_URL, ApiService.class)
                .giveLikeMoment(messageId)
                .map(new ServerResultFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>()), new CustomResourceSubscriber<>(callBack));
    }

    @Override
    public ResourceSubscriber discussMoment(Integer messageId, String comment, OnModelCallBack<Boolean> callBack) {
        return startObservable(ApiServiceFactory.INSTANCE
                .create(mContext, BuildConfig.API_BASE_URL, ApiService.class)
                .discussMoment(messageId,comment)
                .map(new ServerResultFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>()), new CustomResourceSubscriber<>(callBack));
    }

    @Override
    public ResourceSubscriber replyMoment(Integer messageId, Integer toUserId, String comment, OnModelCallBack<Boolean> callBack) {
        return startObservable(ApiServiceFactory.INSTANCE
                .create(mContext, BuildConfig.API_BASE_URL, ApiService.class)
                .replyMoment(messageId,toUserId,comment)
                .map(new ServerResultFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>()), new CustomResourceSubscriber<>(callBack));
    }


    @Override
    public ResourceSubscriber deleteMoment(Integer messageId, OnModelCallBack<Boolean> callBack) {
        return startObservable(ApiServiceFactory.INSTANCE
                .create(mContext, BuildConfig.API_BASE_URL, ApiService.class)
                .deleteMoment(messageId)
                .map(new ServerResultFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>()), new CustomResourceSubscriber<>(callBack));
    }
}
