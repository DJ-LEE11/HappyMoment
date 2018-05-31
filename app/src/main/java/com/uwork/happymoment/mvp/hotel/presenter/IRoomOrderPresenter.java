package com.uwork.happymoment.mvp.hotel.presenter;

import android.content.Context;

import com.uwork.happymoment.mvp.hotel.bean.IntegralBean;
import com.uwork.happymoment.mvp.hotel.contract.IRoomOrderContract;
import com.uwork.happymoment.mvp.hotel.model.IRoomOrderModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBasePresenterImpl;
import com.uwork.librx.rx.BaseResult;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

/**
 * Created by jie on 2018/5/31.
 */

public class IRoomOrderPresenter <T extends IRoomOrderContract.View & IBaseActivityContract.View> extends IBasePresenterImpl<T>
        implements IRoomOrderContract.Presenter {

    private IRoomOrderModel mModel;

    public IRoomOrderPresenter(Context context) {
        super(context);
        mModel = new IRoomOrderModel(context);
    }

    @Override
    public void addOrder(String contactName, String contactPhone, String endTime, int hostelRoomId, int num, int setMealId, String startTime, int useIntegral) {
        addSubscription(mModel.addOrder(contactName, contactPhone, endTime, hostelRoomId, num, setMealId, startTime, useIntegral, new OnModelCallBack<BaseResult<String>>() {
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
                getView().addOrderSuccess();
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
    public void getIntegral() {
        addSubscription(mModel.getIntegral(new OnModelCallBack<IntegralBean>() {
            @Override
            public void onStart() {
                getView().showLoading();
            }

            @Override
            public void onCancel() {
                getView().dismissLoading();
            }

            @Override
            public void onSuccess(IntegralBean value) {
                getView().dismissLoading();
                if (value!=null){
                    getView().getIntegralSuccess(value);
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
