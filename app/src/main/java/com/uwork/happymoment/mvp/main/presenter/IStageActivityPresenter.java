package com.uwork.happymoment.mvp.main.presenter;

import android.content.Context;

import com.uwork.happymoment.mvp.main.bean.StageActivityDetailBean;
import com.uwork.happymoment.mvp.main.contract.IStageActivityContract;
import com.uwork.happymoment.mvp.main.model.IStageActivityModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBasePresenterImpl;
import com.uwork.librx.rx.BaseResult;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

/**
 * Created by jie on 2018/5/29.
 */

public class IStageActivityPresenter<T extends IStageActivityContract.View & IBaseActivityContract.View> extends IBasePresenterImpl<T>
        implements IStageActivityContract.Presenter {

    private IStageActivityModel mModel;

    public IStageActivityPresenter(Context context) {
        super(context);
        mModel = new IStageActivityModel(context);
    }

    @Override
    public void getStageActivityDetail(Integer id) {
        addSubscription(mModel.getStageActivityDetail(id, new OnModelCallBack<StageActivityDetailBean>() {
            @Override
            public void onStart() {
                getView().showLoading();
            }

            @Override
            public void onCancel() {
                getView().dismissLoading();
            }

            @Override
            public void onSuccess(StageActivityDetailBean value) {
                getView().dismissLoading();
                if (value != null) {
                    getView().showDetail(value);
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
    public void joinStageActivity(int activityId, String address, String contactName, String contactPhone, String startTime, String endTime) {
        addSubscription(mModel.joinStageActivity(activityId, address, contactName,contactPhone,startTime, endTime, new OnModelCallBack<BaseResult<String>>() {
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
                getView().joinSuccess();
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
