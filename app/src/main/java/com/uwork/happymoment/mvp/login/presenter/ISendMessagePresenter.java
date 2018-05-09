package com.uwork.happymoment.mvp.login.presenter;

import android.content.Context;

import com.uwork.happymoment.mvp.login.contract.ISendMessageContract;
import com.uwork.happymoment.mvp.login.model.ISendMessageModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBasePresenterImpl;
import com.uwork.librx.rx.BaseResult;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

/**
 * Created by jie on 2018/5/9.
 */

public class ISendMessagePresenter<T extends ISendMessageContract.View & IBaseActivityContract.View> extends IBasePresenterImpl<T>
        implements ISendMessageContract.Presenter {

    private ISendMessageModel mModel;

    public ISendMessagePresenter(Context context) {
        super(context);
        mModel = new ISendMessageModel(context);
    }

    @Override
    public void sendMessage(String phone) {
        addSubscription(mModel.sendMessage(phone, new OnModelCallBack<BaseResult<String>>() {
            @Override
            public void onStart() {
                getView().showLoading();
            }

            @Override
            public void onCancel() {
                getView().dismissLoading();
            }

            @Override
            public void onSuccess(BaseResult<String> value) {
                getView().dismissLoading();
                getView().startRegisterCountDown();
            }

            @Override
            public void onError(ApiException e) {
                getView().dismissLoading();
                getView().handleException(e);
            }

            @Override
            public void onComplete() {
                getView().dismissLoading();
            }
        }));

    }
}
