package com.uwork.happymoment.mvp.social.chat.presenter;

import android.content.Context;

import com.uwork.happymoment.mvp.social.chat.contract.IMakeFriendContract;
import com.uwork.happymoment.mvp.social.chat.model.IMakeFriendModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBasePresenterImpl;
import com.uwork.librx.rx.BaseResult;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

/**
 * Created by jie on 2018/5/18.
 */

public class IMakeFriendPresenter<T extends IMakeFriendContract.View & IBaseActivityContract.View> extends IBasePresenterImpl<T>
        implements IMakeFriendContract.Presenter {

    private IMakeFriendModel mModel;

    public IMakeFriendPresenter(Context context) {
        super(context);
        mModel = new IMakeFriendModel(context);
    }

    @Override
    public void makeFriend(Integer friendId) {
        addSubscription(mModel.makeFriend(friendId, new OnModelCallBack<BaseResult<String>>() {
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
                getView().makeFriendSuccess();
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
