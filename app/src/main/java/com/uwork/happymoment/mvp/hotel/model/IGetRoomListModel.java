package com.uwork.happymoment.mvp.hotel.model;

import android.content.Context;

import com.uwork.happymoment.BuildConfig;
import com.uwork.happymoment.api.ApiService;
import com.uwork.happymoment.mvp.hotel.bean.RoomItemBean;
import com.uwork.happymoment.mvp.hotel.contract.IGetRoomListContract;
import com.uwork.librx.mvp.model.IBaseModelImpl;
import com.uwork.librx.rx.http.ApiServiceFactory;
import com.uwork.librx.rx.http.CustomResourceSubscriber;
import com.uwork.librx.rx.http.HttpResultFunc;
import com.uwork.librx.rx.http.ServerResultFunc;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/31.
 */

public class IGetRoomListModel extends IBaseModelImpl implements IGetRoomListContract.Model {

    public IGetRoomListModel(Context context) {
        super(context);
    }

    @Override
    public ResourceSubscriber getRoomList(Integer id, OnModelCallBack<List<RoomItemBean>> callBack) {
        return startObservable(ApiServiceFactory.INSTANCE
                .create(mContext, BuildConfig.API_BASE_URL, ApiService.class)
                .getRoomList(id)
                .map(new ServerResultFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>()), new CustomResourceSubscriber<>(callBack));
    }
}