package com.uwork.happymoment.mvp.social.cirle.presenter;

import android.content.Context;

import com.uwork.happymoment.mvp.social.cirle.bean.MomentsItemResponseBean;
import com.uwork.happymoment.mvp.social.cirle.contract.ICircleContract;
import com.uwork.happymoment.mvp.social.cirle.model.ICircleModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBaseListPagePresenter;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

/**
 * Created by jie on 2018/5/19.
 */

public class ICirclePresenter<T extends ICircleContract.View & IBaseActivityContract.View>
        extends IBaseListPagePresenter<T, MomentsItemResponseBean> implements ICircleContract.Presenter {

    private ICircleModel mICircleModel;

    public ICirclePresenter(Context context) {
        super(context);
        mModel = new ICircleModel(context);
    }

    @Override
    public void giveLikeMoment(Integer messageId) {
        addSubscription(((ICircleModel) mModel).giveLikeMoment(messageId, new OnModelCallBack<Boolean>() {
            @Override
            public void onStart() {
                getView().showLoading();
            }

            @Override
            public void onCancel() {
                getView().dismissLoading();
            }

            @Override
            public void onSuccess(Boolean value) {
                getView().dismissLoading();
                getView().giveLikeSuccess();
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
    public void deleteMoment(Integer messageId) {
        addSubscription(((ICircleModel) mModel).deleteMoment(messageId, new OnModelCallBack<Boolean>() {
            @Override
            public void onStart() {
                getView().showLoading();
            }

            @Override
            public void onCancel() {
                getView().dismissLoading();
            }

            @Override
            public void onSuccess(Boolean value) {
                getView().dismissLoading();
                getView().deleteMomentSuccess();
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
