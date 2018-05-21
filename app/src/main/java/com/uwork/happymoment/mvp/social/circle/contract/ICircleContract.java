package com.uwork.happymoment.mvp.social.circle.contract;

import com.uwork.happymoment.mvp.social.circle.bean.MomentLikeBean;
import com.uwork.librx.mvp.contract.IBaseListPageContract;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/19.
 */

public interface ICircleContract {

    interface View<T> extends IBaseListPageContract.View<T> {
        void giveLikeSuccess(int itemPosition,List<MomentLikeBean> momentLikeBeanList);

//        void discussMomentSuccess();

        void deleteMomentSuccess(int itemPosition);
    }

    interface Presenter extends IBaseListPageContract.Presenter {

        void giveLikeMoment(int itemPosition, List<MomentLikeBean> momentLikeBeanList, Integer messageId);

//        void discussMoment(Integer toUserId, Integer messageId, String comment);

        void deleteMoment(int itemPosition,Integer messageId);

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
