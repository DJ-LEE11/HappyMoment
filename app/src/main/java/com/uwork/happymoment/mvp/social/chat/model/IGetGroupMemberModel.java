package com.uwork.happymoment.mvp.social.chat.model;

import android.content.Context;

import com.uwork.happymoment.BuildConfig;
import com.uwork.happymoment.api.ApiService;
import com.uwork.happymoment.mvp.social.chat.bean.GroupMemberBean;
import com.uwork.happymoment.mvp.social.chat.contract.IGetGroupMemberContract;
import com.uwork.librx.mvp.model.IBaseModelImpl;
import com.uwork.librx.rx.http.ApiServiceFactory;
import com.uwork.librx.rx.http.CustomResourceSubscriber;
import com.uwork.librx.rx.http.HttpResultFunc;
import com.uwork.librx.rx.http.ServerResultFunc;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/23.
 */

public class IGetGroupMemberModel extends IBaseModelImpl implements IGetGroupMemberContract.Model {

    public IGetGroupMemberModel(Context context) {
        super(context);
    }

    @Override
    public ResourceSubscriber getGroupMember(Integer groupId, OnModelCallBack<List<GroupMemberBean>> callBack) {
        return startObservable(ApiServiceFactory.INSTANCE
                .create(mContext, BuildConfig.API_BASE_URL, ApiService.class)
                .getGroupMember(groupId)
                .map(new ServerResultFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>()), new CustomResourceSubscriber<>(callBack));
    }
}
