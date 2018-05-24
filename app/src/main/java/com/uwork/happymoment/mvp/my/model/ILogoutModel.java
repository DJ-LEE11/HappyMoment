package com.uwork.happymoment.mvp.my.model;

import android.content.Context;

import com.uwork.happymoment.BuildConfig;
import com.uwork.happymoment.api.ApiService;
import com.uwork.happymoment.mvp.my.contract.ILogoutContract;
import com.uwork.librx.mvp.model.IBaseModelImpl;
import com.uwork.librx.rx.BaseResult;
import com.uwork.librx.rx.http.ApiServiceFactory;
import com.uwork.librx.rx.http.CustomResourceSubscriber;
import com.uwork.librx.rx.http.HttpResultFunc;
import com.uwork.librx.rx.http.ServerResultWithDataFunc;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/24.
 */

public class ILogoutModel extends IBaseModelImpl implements ILogoutContract.Model {
    public ILogoutModel(Context context) {
        super(context);
    }

    @Override
    public ResourceSubscriber logout(OnModelCallBack<BaseResult<String>> callBack) {
        return startObservable(ApiServiceFactory.INSTANCE
                .create(mContext, BuildConfig.API_BASE_URL, ApiService.class)
                .logout()
                .map(new ServerResultWithDataFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>()), new CustomResourceSubscriber<>(callBack));
    }
}
