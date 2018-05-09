package com.uwork.happymoment.mvp.login.model;

import android.content.Context;

import com.uwork.happymoment.BuildConfig;
import com.uwork.happymoment.api.ApiService;
import com.uwork.happymoment.mvp.login.bean.ResetPasswordRequestBean;
import com.uwork.happymoment.mvp.login.contract.IResetPasswordContract;
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

public class IResetPasswordModel extends IBaseModelImpl implements IResetPasswordContract.Model {

    public IResetPasswordModel(Context context) {
        super(context);
    }

    @Override
    public ResourceSubscriber resetPassword(String phone, String code, String password, OnModelCallBack<BaseResult<String>> callBack) {
        return startObservable(ApiServiceFactory.INSTANCE
                .create(mContext, BuildConfig.API_BASE_URL, ApiService.class)
                .resetPassword(new ResetPasswordRequestBean(phone,code,password))
                .map(new ServerResultFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>()),new CustomResourceSubscriber<>(callBack));
    }
}
