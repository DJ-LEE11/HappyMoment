package com.uwork.happymoment.mvp.social.chat.presenter;

import android.content.Context;

import com.uwork.happymoment.mvp.social.chat.bean.NewFriendResponseBean;
import com.uwork.happymoment.mvp.social.chat.contract.IGetNewFriendContract;
import com.uwork.happymoment.mvp.social.chat.model.IGetNewFriendModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBasePresenterImpl;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

/**
 * Created by jie on 2018/5/18.
 */

public class IGetNewFriendPresenter <T extends IGetNewFriendContract.View & IBaseActivityContract.View> extends IBasePresenterImpl<T>
        implements IGetNewFriendContract.Presenter {

    private IGetNewFriendModel mModel;

    public IGetNewFriendPresenter(Context context) {
        super(context);
        mModel = new IGetNewFriendModel(context);
    }

    @Override
    public void getNewFriend() {
        addSubscription(mModel.getNewFriend(new OnModelCallBack<NewFriendResponseBean>() {
            @Override
            public void onStart() {
                getView().showLoading();
            }

            @Override
            public void onCancel() {
                getView().dismissLoading();
            }

            @Override
            public void onSuccess(NewFriendResponseBean value) {
                getView().dismissLoading();
                if (value!=null){
                    getView().showNewFriend(value);
                }
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
