package com.uwork.happymoment.mvp.login.presenter;

import android.content.Context;

import com.uwork.happymoment.manager.UserManager;
import com.uwork.happymoment.mvp.login.bean.UserBean;
import com.uwork.happymoment.mvp.login.contract.ILoginContract;
import com.uwork.happymoment.mvp.login.model.ILoginModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBasePresenterImpl;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

/**
 * Created by jie on 2018/5/9.
 */

public class ILoginPresenter <T extends ILoginContract.View & IBaseActivityContract.View> extends IBasePresenterImpl<T>
        implements ILoginContract.Presenter {

    private ILoginModel mModel;

    public ILoginPresenter(Context context) {
        super(context);
        mModel = new ILoginModel(context);
    }

    @Override
    public void login(String phone, String password) {
        addSubscription(mModel.login(phone, password, new OnModelCallBack<UserBean>() {
            @Override
            public void onStart() {
                getView().showLoading();
            }

            @Override
            public void onCancel() {
                getView().dismissLoading();
            }

            @Override
            public void onSuccess(UserBean value) {
                getView().dismissLoading();
                saveToken(value);
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

    @Override
    public void saveToken(UserBean userBean) {
        UserManager.getInstance().saveUserInfo(getContext(),userBean);
        getView().showToast("登录成功");
        getView().loginSuccess();
    }
}
