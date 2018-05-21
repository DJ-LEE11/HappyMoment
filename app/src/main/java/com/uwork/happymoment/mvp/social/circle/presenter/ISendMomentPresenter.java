package com.uwork.happymoment.mvp.social.circle.presenter;

import android.content.Context;

import com.uwork.happymoment.mvp.social.circle.contract.ISendMomentContract;
import com.uwork.happymoment.mvp.social.circle.model.ISendMomentModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBasePresenterImpl;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

/**
 * Created by jie on 2018/5/20.
 */

public class ISendMomentPresenter <T extends ISendMomentContract.View & IBaseActivityContract.View> extends IBasePresenterImpl<T>
        implements ISendMomentContract.Presenter {

    private ISendMomentModel mModel;

    public ISendMomentPresenter(Context context) {
        super(context);
        mModel = new ISendMomentModel(context);
    }

    @Override
    public void sendMoment(int userId, String content, String picture2, String city, String location, String latitude, String longitude, List<String> picture) {
        addSubscription(mModel.sendMoment(userId, content, picture2, city, location, latitude, longitude,picture, new OnModelCallBack<Boolean>() {
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
                getView().sendMomentSuccess();
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
