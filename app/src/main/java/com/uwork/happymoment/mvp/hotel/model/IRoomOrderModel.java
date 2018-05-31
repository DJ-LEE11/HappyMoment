package com.uwork.happymoment.mvp.hotel.model;

import android.content.Context;

import com.uwork.happymoment.BuildConfig;
import com.uwork.happymoment.api.ApiService;
import com.uwork.happymoment.mvp.hotel.bean.AddOrderRequestBean;
import com.uwork.happymoment.mvp.hotel.bean.IntegralBean;
import com.uwork.happymoment.mvp.hotel.contract.IRoomOrderContract;
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
 * Created by jie on 2018/5/31.
 */

public class IRoomOrderModel extends IBaseModelImpl implements IRoomOrderContract.Model {

    public IRoomOrderModel(Context context) {
        super(context);
    }

    @Override
    public ResourceSubscriber addOrder(String contactName, String contactPhone, String endTime, int hostelRoomId, int num, int setMealId, String startTime, int useIntegral, OnModelCallBack<BaseResult<String>> callBack) {
        return startObservable(ApiServiceFactory.INSTANCE
                .create(mContext, BuildConfig.API_BASE_URL, ApiService.class)
                .addOrder(new AddOrderRequestBean(contactName,contactPhone,endTime,hostelRoomId,num,setMealId,startTime,useIntegral))
                .map(new ServerResultWithDataFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>()), new CustomResourceSubscriber<>(callBack));
    }

    @Override
    public ResourceSubscriber getIntegral(OnModelCallBack<IntegralBean> callBack) {
        return startObservable(ApiServiceFactory.INSTANCE
                .create(mContext, BuildConfig.API_BASE_URL, ApiService.class)
                .getIntegral()
                .map(new ServerResultFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>()), new CustomResourceSubscriber<>(callBack));
    }
}
