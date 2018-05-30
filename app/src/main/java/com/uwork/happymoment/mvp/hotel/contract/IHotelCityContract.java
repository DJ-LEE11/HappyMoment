package com.uwork.happymoment.mvp.hotel.contract;

import com.uwork.happymoment.mvp.hotel.bean.HotCityLabelBean;
import com.uwork.happymoment.mvp.hotel.bean.HotelItemBean;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/30.
 */

public interface IHotelCityContract {

    interface View {
        void showLabel(List<HotCityLabelBean> labelBeanList);

        void showLabelEmpty();

        void showHotelList(List<HotelItemBean> hotelItemBeanList);

        void showListEmpty();
    }

    interface Presenter {
        void hotCityLabel();

        void hotelList(Integer classifyId);
    }

    interface Model {
        ResourceSubscriber hotCityLabel(OnModelCallBack<List<HotCityLabelBean>> callBack);

        ResourceSubscriber hotelList(Integer classifyId, OnModelCallBack<List<HotelItemBean>> callBack);

    }
}
