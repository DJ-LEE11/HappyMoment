package com.uwork.happymoment.mvp.hotel.presenter;

import android.content.Context;

import com.uwork.happymoment.mvp.hotel.bean.HotelItemBean;
import com.uwork.happymoment.mvp.hotel.contract.ISearchHotelContract;
import com.uwork.happymoment.mvp.hotel.model.ISearchHotelModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBasePresenterImpl;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

/**
 * Created by jie on 2018/5/31.
 */

public class ISearchHotelPresenter<T extends ISearchHotelContract.View & IBaseActivityContract.View> extends IBasePresenterImpl<T>
        implements ISearchHotelContract.Presenter {

    private ISearchHotelModel mModel;

    public ISearchHotelPresenter(Context context) {
        super(context);
        mModel = new ISearchHotelModel(context);
    }

    @Override
    public void searchHotelList(String hotelName) {
        addSubscription(mModel.searchHotelList(hotelName, new OnModelCallBack<List<HotelItemBean>>() {
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
