package com.uwork.happymoment.mvp.login.presenter;

import android.content.Context;

import com.uwork.happymoment.mvp.login.contract.IResetPasswordContract;
import com.uwork.happymoment.mvp.login.model.IResetPasswordModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBasePresenterImpl;
import com.uwork.librx.rx.BaseResult;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

/**
 * Created by jie on 2018/5/9.
 */

public class IResetPasswordPresenter <T extends IResetPasswordContract.View & IBaseActivityContract.View> extends IBasePresenterImpl<T>
        implements IResetPasswordContract.Presenter {

    private IResetPasswordModel mModel;

    public IResetPasswordPresenter(Context context) {
        super(context);
        mModel = new IResetPasswordModel(context);
    }

    @Override
    public void resetPassword(String phone, String code, String password) {
        addSubscription(mModel.resetPassword(phone, code, password, new OnModelCallBack<BaseResult<String>>() {
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
                getView().resetPasswordSuccess();

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
