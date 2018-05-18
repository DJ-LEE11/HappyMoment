package com.uwork.happymoment.mvp.social.chat.presenter;

import android.content.Context;

import com.uwork.happymoment.mvp.social.chat.bean.SearchNewFriendBean;
import com.uwork.happymoment.mvp.social.chat.contract.ISearchNewFriendContract;
import com.uwork.happymoment.mvp.social.chat.model.ISearchNewFriendModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBasePresenterImpl;
import com.uwork.librx.rx.BaseResult;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

/**
 * Created by jie on 2018/5/18.
 */

public class ISearchNewFriendPresenter<T extends ISearchNewFriendContract.View & IBaseActivityContract.View> extends IBasePresenterImpl<T>
        implements ISearchNewFriendContract.Presenter {

    private ISearchNewFriendModel mModel;

    public ISearchNewFriendPresenter(Context context) {
        super(context);
        mModel = new ISearchNewFriendModel(context);
    }

    @Override
    public void searchNewFriendForPhone(String phone) {
        addSubscription(mModel.searchNewFriendForPhone(phone, new OnModelCallBack<BaseResult<SearchNewFriendBean>>() {
            @Override
            public void onStart() {
                getView().showLoading();
            }

            @Override
            public void onCancel() {
                getView().dismissLoading();
            }

            @Override
            public void onSuccess(BaseResult<SearchNewFriendBean> value) {
                getView().dismissLoading();
                if (value.getData()!=null){
                    getView().showNewFriend(value.getData());
                }else {
                    getView().empty();
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

    @Override
    public void searchNewFriendForCode(String qrCode) {
        addSubscription(mModel.searchNewFriendForCode(qrCode, new OnModelCallBack<BaseResult<SearchNewFriendBean>>() {
            @Override
            public void onStart() {
                getView().showLoading();
            }

            @Override
            public void onCancel() {
                getView().dismissLoading();
            }

            @Override
            public void onSuccess(BaseResult<SearchNewFriendBean> value) {
                getView().dismissLoading();
                if (value.getData()!=null){
                    getView().showNewFriend(value.getData());
                }else {
                    getView().empty();
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
