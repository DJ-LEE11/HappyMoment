package com.uwork.happymoment.mvp.my.presenter;

import android.content.Context;

import com.uwork.happymoment.mvp.my.contract.IUpdateUserInfoContract;
import com.uwork.happymoment.mvp.my.model.IUpdateUserInfoModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBasePresenterImpl;
import com.uwork.librx.rx.BaseResult;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

/**
 * Created by jie on 2018/5/31.
 */

public class IUpdateUserInfoPresenter<T extends IUpdateUserInfoContract.View & IBaseActivityContract.View> extends IBasePresenterImpl<T>
        implements IUpdateUserInfoContract.Presenter {

    private IUpdateUserInfoModel mModel;

    public IUpdateUserInfoPresenter(Context context) {
        super(context);
        mModel = new IUpdateUserInfoModel(context);
    }

    @Override
    public void updateUserInfo(String avatar, String nickName, int sex) {
        addSubscription(mModel.updateUserInfo(avatar, nickName, sex, new OnModelCallBack<BaseResult<String>>() {
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
                getView().updateUserInfoSuccess();
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
