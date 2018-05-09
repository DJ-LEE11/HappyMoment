package com.uwork.happymoment.mvp.login.model;

import android.content.Context;

import com.uwork.happymoment.BuildConfig;
import com.uwork.happymoment.api.ApiService;
import com.uwork.happymoment.mvp.login.bean.RegisterRequestBean;
import com.uwork.happymoment.mvp.login.contract.IRegisterContract;
import com.uwork.librx.mvp.model.IBaseModelImpl;
import com.uwork.librx.rx.BaseResult;
import com.uwork.librx.rx.http.ApiServiceFactory;
import com.uwork.librx.rx.http.CustomResourceSubscriber;
import com.uwork.librx.rx.http.HttpResultFunc;
import com.uwork.librx.rx.http.ServerResultFunc;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/9.
 */

public class IRegisterModel extends IBaseModelImpl implements IRegisterContract.Model {

    public IRegisterModel(Context context) {
        super(context);
    }

    @Override
    public ResourceSubscriber register(String code, String password, String phone, String sharingCode, OnModelCallBack<BaseResult<String>> callBack) {
        return startObservable(ApiServiceFactory.INSTANCE
                .create(mContext, BuildConfig.API_BASE_URL, ApiService.class)
                .register(new RegisterRequestBean(code,password,phone,sharingCode))
                .map(new ServerResultFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>()),new CustomResourceSubscriber<>(callBack));
    }
}
