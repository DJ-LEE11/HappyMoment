package com.uwork.happymoment.mvp.social.chat.model;

import android.content.Context;

import com.uwork.happymoment.BuildConfig;
import com.uwork.happymoment.api.ApiService;
import com.uwork.happymoment.mvp.social.chat.bean.SearchNewFriendBean;
import com.uwork.happymoment.mvp.social.chat.bean.SearchNewFriendForCodeRequestBean;
import com.uwork.happymoment.mvp.social.chat.bean.SearchNewFriendForPhoneRequestBean;
import com.uwork.happymoment.mvp.social.chat.contract.ISearchNewFriendContract;
import com.uwork.librx.mvp.model.IBaseModelImpl;
import com.uwork.librx.rx.BaseResult;
import com.uwork.librx.rx.http.ApiServiceFactory;
import com.uwork.librx.rx.http.CustomResourceSubscriber;
import com.uwork.librx.rx.http.HttpResultFunc;
import com.uwork.librx.rx.http.ServerResultWithDataFunc;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/18.
 */

public class ISearchNewFriendModel extends IBaseModelImpl implements ISearchNewFriendContract.Model {

    public ISearchNewFriendModel(Context context) {
        super(context);
    }

    @Override
    public ResourceSubscriber searchNewFriendForPhone(String phone, OnModelCallBack<BaseResult<SearchNewFriendBean>> callBack) {
        return startObservable(ApiServiceFactory.INSTANCE
                .create(mContext, BuildConfig.API_BASE_URL, ApiService.class)
                .searchNewFriendForPhone(new SearchNewFriendForPhoneRequestBean(phone))
                .map(new ServerResultWithDataFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>()), new CustomResourceSubscriber<>(callBack));
    }

    @Override
    public ResourceSubscriber searchNewFriendForCode(String qrCode, OnModelCallBack<BaseResult<SearchNewFriendBean>> callBack) {
        return startObservable(ApiServiceFactory.INSTANCE
                .create(mContext, BuildConfig.API_BASE_URL, ApiService.class)
                .searchNewFriendForCode(new SearchNewFriendForCodeRequestBean(qrCode))
                .map(new ServerResultWithDataFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>()), new CustomResourceSubscriber<>(callBack));
    }
}
