package com.uwork.happymoment.mvp.social.chat.presenter;

import android.content.Context;

import com.uwork.happymoment.mvp.social.chat.bean.FriendDetailBean;
import com.uwork.happymoment.mvp.social.chat.contract.IGetFriendDetailContract;
import com.uwork.happymoment.mvp.social.chat.model.IGetFriendDetailModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBasePresenterImpl;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

/**
 * Created by jie on 2018/5/17.
 */

public class IGetFriendDetailPresenter<T extends IGetFriendDetailContract.View & IBaseActivityContract.View> extends IBasePresenterImpl<T>
        implements IGetFriendDetailContract.Presenter {

    private IGetFriendDetailModel mModel;

    public IGetFriendDetailPresenter(Context context) {
        super(context);
        mModel = new IGetFriendDetailModel(context);
    }

    @Override
    public void getFriendDetail(String friendId) {
        addSubscription(mModel.getFriendDetail(friendId, new OnModelCallBack<FriendDetailBean>() {
            @Override
            public void onStart() {
                getView().showLoading();
            }

            @Override
            public void onCancel() {
                getView().dismissLoading();
            }

            @Override
            public void onSuccess(FriendDetailBean value) {
                getView().dismissLoading();
                if (value!=null){
                    getView().showDetail(value);
                }else {
                    getView().getDetailFail();
                }
            }

            @Override
            public void onError(ApiException e) {
                getView().dismissLoading();
                getView().handleException(e);
                getView().getDetailFail();
            }

            @Override
            public void onComplete() {
                getView().dismissLoading();
            }
        }));
    }
}
