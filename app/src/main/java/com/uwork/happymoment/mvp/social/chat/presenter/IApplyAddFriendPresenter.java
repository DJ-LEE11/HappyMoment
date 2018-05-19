package com.uwork.happymoment.mvp.social.chat.presenter;

import android.content.Context;

import com.uwork.happymoment.mvp.social.chat.contract.IApplyAddFriendContract;
import com.uwork.happymoment.mvp.social.chat.model.IApplyAddFriendModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBasePresenterImpl;
import com.uwork.librx.rx.BaseResult;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

/**
 * Created by jie on 2018/5/18.
 */

public class IApplyAddFriendPresenter<T extends IApplyAddFriendContract.View & IBaseActivityContract.View> extends IBasePresenterImpl<T>
        implements IApplyAddFriendContract.Presenter {

    private IApplyAddFriendModel mModel;

    public IApplyAddFriendPresenter(Context context) {
        super(context);
        mModel = new IApplyAddFriendModel(context);
    }

    @Override
    public void applyAddFriend(String wantFriendId) {
        addSubscription(mModel.applyAddFriend(wantFriendId, new OnModelCallBack<BaseResult<String>>() {
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
                getView().applySuccess();
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
