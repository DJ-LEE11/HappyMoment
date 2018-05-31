package com.uwork.happymoment.mvp.hotel.contract;

import com.uwork.happymoment.mvp.hotel.bean.RoomItemBean;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/31.
 */

public interface IGetRoomListContract {

    interface View {
        void showRoomList(List<RoomItemBean> roomItemBeanList);

        void showEmpty();
    }

    interface Presenter {
        void getRoomList(Integer id );
    }

    interface Model {
        ResourceSubscriber getRoomList(Integer id , OnModelCallBack<List<RoomItemBean>> callBack);
    }
}
