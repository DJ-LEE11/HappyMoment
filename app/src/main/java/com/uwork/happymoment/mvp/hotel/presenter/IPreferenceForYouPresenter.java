package com.uwork.happymoment.mvp.hotel.presenter;

import android.content.Context;

import com.uwork.happymoment.mvp.hotel.bean.HotelItemBean;
import com.uwork.happymoment.mvp.hotel.contract.IPreferenceForYouContract;
import com.uwork.happymoment.mvp.hotel.model.IPreferenceForYouModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBasePresenterImpl;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

/**
 * Created by jie on 2018/5/31.
 */

public class IPreferenceForYouPresenter<T extends IPreferenceForYouContract.View & IBaseActivityContract.View> extends IBasePresenterImpl<T>
        implements IPreferenceForYouContract.Presenter {

    private IPreferenceForYouModel mModel;

    public IPreferenceForYouPresenter(Context context) {
        super(context);
        mModel = new IPreferenceForYouModel(context);
    }

    @Override
    public void getHotelList() {
        addSubscription(mModel.getHotelList(new OnModelCallBack<List<HotelItemBean>>() {
            @Override
            public void onStart() {
                getView().showLoading();
            }

            @Override
            public void onCancel() {
                getView().dismissLoading();
            }

            @Override
            public void onSuccess(List<HotelItemBean> value) {
                getView().dismissLoading();
                if (value!=null && value.size()>0){
                    getView().showHotelList(value);
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
