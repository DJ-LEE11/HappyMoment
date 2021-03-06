package com.uwork.happymoment.mvp.social.chat.presenter;

import android.content.Context;

import com.uwork.happymoment.mvp.social.chat.contract.IUpdateFriendInfoContract;
import com.uwork.happymoment.mvp.social.chat.model.IUpdateFriendInfoModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBasePresenterImpl;
import com.uwork.librx.rx.BaseResult;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

/**
 * Created by jie on 2018/5/17.
 */

public class IUpdateFriendInfoPresenter <T extends IUpdateFriendInfoContract.View & IBaseActivityContract.View> extends IBasePresenterImpl<T>
        implements IUpdateFriendInfoContract.Presenter {

    private IUpdateFriendInfoModel mModel;

    public IUpdateFriendInfoPresenter(Context context) {
        super(context);
        mModel = new IUpdateFriendInfoModel(context);
    }

    @Override
    public void updateFriendInfo(String friendId, String phone, String remarksName) {
        addSubscription(mModel.updateFriendInfo(friendId, phone, remarksName, new OnModelCallBack<BaseResult<String>>() {
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
                getView().updateFriendInfoSuccess();
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
