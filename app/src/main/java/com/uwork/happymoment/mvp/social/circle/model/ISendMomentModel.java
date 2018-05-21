package com.uwork.happymoment.mvp.social.circle.model;

import android.content.Context;

import com.uwork.happymoment.BuildConfig;
import com.uwork.happymoment.api.ApiService;
import com.uwork.happymoment.mvp.social.circle.bean.SendCircleRequestBean;
import com.uwork.happymoment.mvp.social.circle.contract.ISendMomentContract;
import com.uwork.librx.mvp.model.IBaseModelImpl;
import com.uwork.librx.rx.http.ApiServiceFactory;
import com.uwork.librx.rx.http.CustomResourceSubscriber;
import com.uwork.librx.rx.http.HttpResultFunc;
import com.uwork.librx.rx.http.ServerResultFunc;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/20.
 */

public class ISendMomentModel extends IBaseModelImpl implements ISendMomentContract.Model {

    public ISendMomentModel(Context context) {
        super(context);
    }

    @Override
    public ResourceSubscriber sendMoment(int userId, String content, String picture2, String city, String location, String latitude, String longitude, List<String> picture, OnModelCallBack<Boolean> callBack) {
        return startObservable(ApiServiceFactory.INSTANCE
                .create(mContext, BuildConfig.API_BASE_URL, ApiService.class)
                .sendMoment(new SendCircleRequestBean(userId,content,picture2,city,location,latitude,longitude,picture))
                .map(new ServerResultFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>()), new CustomResourceSubscriber<>(callBack));
    }
}
