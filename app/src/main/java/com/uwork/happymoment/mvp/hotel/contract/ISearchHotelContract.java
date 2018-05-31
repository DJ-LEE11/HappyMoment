package com.uwork.happymoment.mvp.hotel.contract;

import com.uwork.happymoment.mvp.hotel.bean.HotelItemBean;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/31.
 */

public interface ISearchHotelContract {

    interface View {
        void showHotelList(List<HotelItemBean> hotelItemBeanList);

        void showEmpty();
    }

    interface Presenter {
        void searchHotelList(String hotelName);
    }

    interface Model {
        ResourceSubscriber searchHotelList(String hotelName, OnModelCallBack<List<HotelItemBean>> callBack);
    }
}
