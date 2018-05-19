package com.uwork.happymoment.mvp.social.chat.model;

import android.content.Context;

import com.uwork.happymoment.BuildConfig;
import com.uwork.happymoment.api.ApiService;
import com.uwork.happymoment.mvp.social.chat.bean.ApplyAddFriendRequestBean;
import com.uwork.happymoment.mvp.social.chat.contract.IApplyAddFriendContract;
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

public class IApplyAddFriendModel extends IBaseModelImpl implements IApplyAddFriendContract.Model {

    public IApplyAddFriendModel(Context context) {
        super(context);
    }

    @Override
    public ResourceSubscriber applyAddFriend(String wantFriendId, OnModelCallBack<BaseResult<String>> callBack) {
        return startObservable(ApiServiceFactory.INSTANCE
                .create(mContext, BuildConfig.API_BASE_URL, ApiService.class)
                .applyAddFriend(new ApplyAddFriendRequestBean(wantFriendId))
                .map(new ServerResultWithDataFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>()), new CustomResourceSubscriber<>(callBack));
    }
}
