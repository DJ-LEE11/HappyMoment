package com.uwork.happymoment.mvp.main.presenter;

import android.content.Context;

import com.uwork.happymoment.mvp.main.bean.BannerBean;
import com.uwork.happymoment.mvp.main.contract.IBannerContract;
import com.uwork.happymoment.mvp.main.model.IBannerModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBasePresenterImpl;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

/**
 * Created by jie on 2018/5/25.
 */

public class IBannerPresenter <T extends IBannerContract.View & IBaseActivityContract.View> extends IBasePresenterImpl<T>
        implements IBannerContract.Presenter {

    private IBannerModel mModel;

    public IBannerPresenter(Context context) {
        super(context);
        mModel = new IBannerModel(context);
    }

    @Override
    public void getBanner(Integer type) {
        addSubscription(mModel.getBanner(type, new OnModelCallBack<List<BannerBean>>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onSuccess(List<BannerBean> value) {
                if (value!=null && value.size()>0){
                    getView().shoBanner(type,value);
                }
            }

            @Override
            public void onError(ApiException e) {
                getView().handleException(e);
            }

            @Override
            public void onComplete() {
            }
        }));
    }
}
