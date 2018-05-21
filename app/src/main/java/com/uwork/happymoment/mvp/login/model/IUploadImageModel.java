package com.uwork.happymoment.mvp.login.model;

import android.content.Context;

import com.uwork.happymoment.BuildConfig;
import com.uwork.happymoment.api.ApiService;
import com.uwork.happymoment.mvp.login.bean.UploadBean;
import com.uwork.happymoment.mvp.login.contract.IUploadImageContract;
import com.uwork.librx.mvp.model.IBaseModelImpl;
import com.uwork.librx.rx.BaseResult;
import com.uwork.librx.rx.http.ApiServiceFactory;
import com.uwork.librx.rx.http.CustomResourceSubscriber;
import com.uwork.librx.rx.http.HttpResultFunc;
import com.uwork.librx.rx.http.ServerResultWithDataFunc;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import io.reactivex.subscribers.ResourceSubscriber;
import okhttp3.MultipartBody;

/**
 * Created by jie on 2018/5/20.
 */

public class IUploadImageModel extends IBaseModelImpl implements IUploadImageContract.Model {

    public IUploadImageModel(Context context) {
        super(context);
    }

    @Override
    public ResourceSubscriber uploadImage(MultipartBody.Part image, OnModelCallBack<BaseResult<UploadBean>> callBack) {
        return startObservable(ApiServiceFactory.INSTANCE
                .create(mContext, BuildConfig.API_BASE_URL, ApiService.class)
                .uploadImage(image)
                .map(new ServerResultWithDataFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>()), new CustomResourceSubscriber<>(callBack));
    }
}
