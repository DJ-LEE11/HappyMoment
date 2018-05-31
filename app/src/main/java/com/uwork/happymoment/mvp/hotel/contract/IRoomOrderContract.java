package com.uwork.happymoment.mvp.hotel.contract;

import com.uwork.happymoment.mvp.hotel.bean.IntegralBean;
import com.uwork.librx.rx.BaseResult;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/31.
 */

public interface IRoomOrderContract {

    interface View {
        void addOrderSuccess();

        void getIntegralSuccess(IntegralBean integralBean);
    }

    interface Presenter {
        void addOrder(String contactName, String contactPhone, String endTime, int hostelRoomId, int num, int setMealId, String startTime, int useIntegral);

        void getIntegral();
    }

    interface Model {
        ResourceSubscriber addOrder(String contactName, String contactPhone, String endTime, int hostelRoomId, int num, int setMealId, String startTime, int useIntegral, OnModelCallBack<BaseResult<String>> callBack);

        ResourceSubscriber getIntegral(OnModelCallBack<IntegralBean> callBack);
    }
}
