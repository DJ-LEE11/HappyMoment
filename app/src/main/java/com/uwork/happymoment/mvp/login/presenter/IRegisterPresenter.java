package com.uwork.happymoment.mvp.login.presenter;

import android.content.Context;

import com.uwork.happymoment.mvp.login.contract.IRegisterContract;
import com.uwork.happymoment.mvp.login.model.IRegisterModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBasePresenterImpl;
import com.uwork.librx.rx.BaseResult;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

/**
 * Created by jie on 2018/5/9.
 */

public class IRegisterPresenter <T extends IRegisterContract.View & IBaseActivityContract.View> extends IBasePresenterImpl<T>
        implements IRegisterContract.Presenter {

    private IRegisterModel mModel;

    public IRegisterPresenter(Context context) {
        super(context);
        mModel = new IRegisterModel(context);
    }

    @Override
    public void register(String code, String password, String phone, String sharingCode) {
        addSubscription(mModel.register(code, password, phone, sharingCode, new OnModelCallBack<BaseResult<String>>() {
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
                getView().registerSuccess();
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
