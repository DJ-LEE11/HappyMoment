package com.uwork.happymoment.mvp.social.cirle.contract;

import com.uwork.librx.mvp.contract.IBaseListPageContract;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/19.
 */

public interface ICircleContract {

    interface View<T> extends IBaseListPageContract.View<T> {
        void giveLikeSuccess();

//        void discussMomentSuccess();

        void deleteMomentSuccess();
    }

    interface Presenter extends IBaseListPageContract.Presenter {

        void giveLikeMoment(Integer messageId);

//        void discussMoment(Integer toUserId, Integer messageId, String comment);

        void deleteMoment(Integer messageId);

    }

    interface Model<T> extends IBaseListPageContract.Model<T> {
        ResourceSubscriber giveLikeMoment(Integer messageId, OnModelCallBack<Boolean> callBack);

//        ResourceSubscriber discussMoment(Integer toUserId,
//                                         Integer messageId,
//                                         String comment,
//                                         OnModelCallBack<Boolean> callBack);

        ResourceSubscriber deleteMoment(Integer messageId, OnModelCallBack<Boolean> callBack);

    }
}
