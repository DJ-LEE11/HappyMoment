package com.uwork.happymoment.mvp.my.presenter;

import android.content.Context;

import com.uwork.happymoment.mvp.my.contract.ILogoutContract;
import com.uwork.happymoment.mvp.my.model.ILogoutModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBasePresenterImpl;
import com.uwork.librx.rx.BaseResult;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

/**
 * Created by jie on 2018/5/24.
 */

public class ILogoutPresenter<T extends ILogoutContract.View & IBaseActivityContract.View> extends IBasePresenterImpl<T>
        implements ILogoutContract.Presenter {

    private ILogoutModel mModel;

    public ILogoutPresenter(Context context) {
        super(context);
        mModel = new ILogoutModel(context);
    }

    @Override
    public void logout() {
        addSubscription(mModel.logout(new OnModelCallBack<BaseResult<String>>() {
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
                getView().logoutSuccess();
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
