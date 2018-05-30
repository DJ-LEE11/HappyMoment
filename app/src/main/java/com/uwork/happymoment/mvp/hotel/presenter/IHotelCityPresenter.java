package com.uwork.happymoment.mvp.hotel.presenter;

import android.content.Context;

import com.uwork.happymoment.mvp.hotel.bean.HotCityLabelBean;
import com.uwork.happymoment.mvp.hotel.bean.HotelItemBean;
import com.uwork.happymoment.mvp.hotel.contract.IHotelCityContract;
import com.uwork.happymoment.mvp.hotel.model.IHotelCityModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBasePresenterImpl;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

/**
 * Created by jie on 2018/5/30.
 */

public class IHotelCityPresenter<T extends IHotelCityContract.View & IBaseActivityContract.View> extends IBasePresenterImpl<T>
        implements IHotelCityContract.Presenter {

    private IHotelCityModel mModel;

    public IHotelCityPresenter(Context context) {
        super(context);
        mModel = new IHotelCityModel(context);
    }

    @Override
    public void hotCityLabel() {
        addSubscription(mModel.hotCityLabel(new OnModelCallBack<List<HotCityLabelBean>>() {
            @Override
            public void onStart() {
                getView().showLoading();
            }

            @Override
            public void onCancel() {
                getView().dismissLoading();
            }

            @Override
            public void onSuccess(List<HotCityLabelBean> value) {
                getView().dismissLoading();
                if (value!=null && value.size()>0){
                    getView().showLabel(value);
                }else {
                    getView().showLabelEmpty();
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
    public void hotelList(Integer classifyId) {
        addSubscription(mModel.hotelList(classifyId, new OnModelCallBack<List<HotelItemBean>>() {
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
                    getView().showListEmpty();
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
