package com.uwork.happymoment.mvp.main.presenter;

import android.content.Context;

import com.uwork.happymoment.mvp.main.bean.VideoBean;
import com.uwork.happymoment.mvp.main.contract.IRecommendContract;
import com.uwork.happymoment.mvp.main.model.IRecommendModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBasePresenterImpl;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

/**
 * Created by jie on 2018/5/25.
 */

public class IRecommendPresenter <T extends IRecommendContract.View & IBaseActivityContract.View> extends IBasePresenterImpl<T>
        implements IRecommendContract.Presenter {

    private IRecommendModel mModel;

    public IRecommendPresenter(Context context) {
        super(context);
        mModel = new IRecommendModel(context);
    }

    @Override
    public void getRecommend() {
        addSubscription(mModel.getRecommend(new OnModelCallBack<List<VideoBean>>() {
            @Override
            public void onStart() {
                getView().showLoading();
            }

            @Override
            public void onCancel() {
                getView().dismissLoading();
            }

            @Override
            public void onSuccess(List<VideoBean> value) {
                getView().dismissLoading();
                if (value!=null && value.size()>0){
                    getView().showRecommend(value);
                }else {
                    getView().showEmpty();
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
