package com.uwork.happymoment.mvp.hotel.contract;

import com.uwork.happymoment.mvp.hotel.bean.HotelItemBean;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/31.
 */

public interface IPreferenceForYouContract {

    interface View {
        void showHotelList(List<HotelItemBean> hotelItemBeanList);

        void showEmpty();
    }

    interface Presenter {
        void getHotelList();
    }

    interface Model {
        ResourceSubscriber getHotelList( OnModelCallBack<List<HotelItemBean>> callBack);
    }
}
