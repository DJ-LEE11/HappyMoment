package com.uwork.happymoment.mvp.hotel.model;

import android.content.Context;

import com.uwork.happymoment.BuildConfig;
import com.uwork.happymoment.api.ApiService;
import com.uwork.happymoment.mvp.hotel.bean.HotCityLabelBean;
import com.uwork.happymoment.mvp.hotel.bean.HotelItemBean;
import com.uwork.happymoment.mvp.hotel.contract.IHotelCityContract;
import com.uwork.librx.mvp.model.IBaseModelImpl;
import com.uwork.librx.rx.http.ApiServiceFactory;
import com.uwork.librx.rx.http.CustomResourceSubscriber;
import com.uwork.librx.rx.http.HttpResultFunc;
import com.uwork.librx.rx.http.ServerResultFunc;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/30.
 */

public class IHotelCityModel extends IBaseModelImpl implements IHotelCityContract.Model {

    public IHotelCityModel(Context context) {
        super(context);
    }

    @Override
    public ResourceSubscriber hotCityLabel(OnModelCallBack<List<HotCityLabelBean>> callBack) {
        return startObservable(ApiServiceFactory.INSTANCE
                .create(mContext, BuildConfig.API_BASE_URL, ApiService.class)
                .getHotCityLabel()
                .map(new ServerResultFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>()), new CustomResourceSubscriber<>(callBack));
    }

    @Override
    public ResourceSubscriber hotelList(Integer classifyId, OnModelCallBack<List<HotelItemBean>> callBack) {
        return startObservable(ApiServiceFactory.INSTANCE
                .create(mContext, BuildConfig.API_BASE_URL, ApiService.class)
                .hotelList(classifyId)
                .map(new ServerResultFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>()), new CustomResourceSubscriber<>(callBack));
    }
}
