package com.uwork.happymoment.mvp.hotel.contract;

import com.uwork.happymoment.mvp.hotel.bean.RoomDetailBean;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/31.
 */

public interface IRoomDetailContract {

    interface View {
        void showRoomDetail(RoomDetailBean roomDetailBean);
    }

    interface Presenter {
        void roomDetail(Integer id);
    }

    interface Model {
        ResourceSubscriber roomDetail(Integer id , OnModelCallBack<RoomDetailBean> callBack);
    }
}
