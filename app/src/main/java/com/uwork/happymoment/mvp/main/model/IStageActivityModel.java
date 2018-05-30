package com.uwork.happymoment.mvp.main.model;

import android.content.Context;

import com.uwork.happymoment.BuildConfig;
import com.uwork.happymoment.api.ApiService;
import com.uwork.happymoment.mvp.main.bean.JoinStageActivityRequestBean;
import com.uwork.happymoment.mvp.main.bean.StageActivityDetailBean;
import com.uwork.happymoment.mvp.main.contract.IStageActivityContract;
import com.uwork.librx.mvp.model.IBaseModelImpl;
import com.uwork.librx.rx.BaseResult;
import com.uwork.librx.rx.http.ApiServiceFactory;
import com.uwork.librx.rx.http.CustomResourceSubscriber;
import com.uwork.librx.rx.http.HttpResultFunc;
import com.uwork.librx.rx.http.ServerResultFunc;
import com.uwork.librx.rx.http.ServerResultWithDataFunc;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/29.
 */

public class IStageActivityModel extends IBaseModelImpl implements IStageActivityContract.Model {

    public IStageActivityModel(Context context) {
        super(context);
    }

    @Override
    public ResourceSubscriber getStageActivityDetail(Integer id, OnModelCallBack<StageActivityDetailBean> callBack) {
        return startObservable(ApiServiceFactory.INSTANCE
                .create(mContext, BuildConfig.API_BASE_URL, ApiService.class)
                .getStageActivityDetail(id)
                .map(new ServerResultFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>()), new CustomResourceSubscriber<>(callBack));
    }

    @Override
    public ResourceSubscriber joinStageActivity(int activityId, String address, String contactName, String contactPhone, String startTime, String endTime, OnModelCallBack<BaseResult<String>> callBack) {
        return startObservable(ApiServiceFactory.INSTANCE
                .create(mContext, BuildConfig.API_BASE_URL, ApiService.class)
                .joinStageActivity(new JoinStageActivityRequestBean(activityId,address,contactName,contactPhone,startTime,endTime))
                .map(new ServerResultWithDataFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>()), new CustomResourceSubscriber<>(callBack));
    }
}
