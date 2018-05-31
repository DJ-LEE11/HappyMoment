package com.uwork.happymoment.mvp.hotel.model;

import android.content.Context;

import com.uwork.happymoment.BuildConfig;
import com.uwork.happymoment.api.ApiService;
import com.uwork.happymoment.mvp.hotel.bean.RoomDetailBean;
import com.uwork.happymoment.mvp.hotel.contract.IRoomDetailContract;
import com.uwork.librx.mvp.model.IBaseModelImpl;
import com.uwork.librx.rx.http.ApiServiceFactory;
import com.uwork.librx.rx.http.CustomResourceSubscriber;
import com.uwork.librx.rx.http.HttpResultFunc;
import com.uwork.librx.rx.http.ServerResultFunc;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/31.
 */

public class IRoomDetailModel extends IBaseModelImpl implements IRoomDetailContract.Model {

    public IRoomDetailModel(Context context) {
        super(context);
    }

    @Override
    public ResourceSubscriber roomDetail(Integer id, OnModelCallBack<RoomDetailBean> callBack) {
        return startObservable(ApiServiceFactory.INSTANCE
                .create(mContext, BuildConfig.API_BASE_URL, ApiService.class)
                .getRoomDetail(id)
                .map(new ServerResultFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>()), new CustomResourceSubscriber<>(callBack));
    }
}
