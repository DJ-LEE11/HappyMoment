package com.uwork.happymoment.mvp.social.chat.presenter;

import android.content.Context;

import com.uwork.happymoment.mvp.social.chat.bean.FriendIndexBean;
import com.uwork.happymoment.mvp.social.chat.contract.IGetFriendIndexContract;
import com.uwork.happymoment.mvp.social.chat.model.IGetFriendIndexModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBasePresenterImpl;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

/**
 * Created by jie on 2018/5/17.
 */

public class IGetFriendIndexPresenter <T extends IGetFriendIndexContract.View & IBaseActivityContract.View> extends IBasePresenterImpl<T>
        implements IGetFriendIndexContract.Presenter {

    private IGetFriendIndexModel mModel;

    public IGetFriendIndexPresenter(Context context) {
        super(context);
        mModel = new IGetFriendIndexModel(context);
    }

    @Override
    public void getFriendIndex() {
        addSubscription(mModel.getFriendIndex(new OnModelCallBack<List<FriendIndexBean>>() {
            @Override
            public void onStart() {
                getView().showLoading();
            }

            @Override
            public void onCancel() {
                getView().dismissLoading();
            }

            @Override
            public void onSuccess(List<FriendIndexBean> value) {
                getView().dismissLoading();
                getView().showFriendIndex(value);
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
