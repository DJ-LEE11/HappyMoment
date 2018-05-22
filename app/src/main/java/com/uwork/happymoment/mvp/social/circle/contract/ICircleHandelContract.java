package com.uwork.happymoment.mvp.social.circle.contract;

import android.support.annotation.Nullable;

import com.example.circle_base_ui.widget.commentwidget.CommentWidget;
import com.uwork.happymoment.mvp.social.circle.bean.MomentCommentBean;
import com.uwork.happymoment.mvp.social.circle.bean.MomentLikeBean;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/22.
 */

public interface ICircleHandelContract {

    interface View {
        void giveLikeSuccess(int itemPosition, List<MomentLikeBean> momentLikeBeanList);

        void  showCommentBox(@Nullable android.view.View viewHolderRootView, int itemPos, int messageId, @Nullable CommentWidget commentWidget);

        void discussMomentSuccess(int itemPosition, String comment,List<MomentCommentBean> momentCommentBeanList);

        void replyMomentSuccess(int itemPosition , Integer toUserId, String toUserName,String comment,List<MomentCommentBean> momentCommentBeanList);

        void deleteMomentSuccess(int itemPosition);
    }

    interface Presenter {
        void giveLikeMoment(int itemPosition, List<MomentLikeBean> momentLikeBeanList, Integer messageId);

        void  showCommentBox(@Nullable android.view.View viewHolderRootView, int itemPos, int messageId, @Nullable CommentWidget commentWidget);

        void discussMoment(Integer messageId, String comment,int itemPosition, List<MomentCommentBean> momentCommentBeanList);

        void replyMoment(Integer messageId, Integer toUserId, String toUserName,String comment,int itemPosition, List<MomentCommentBean> momentCommentBeanList);

        void deleteMoment(int itemPosition, Integer messageId);
    }

    interface Model {
        ResourceSubscriber giveLikeMoment(Integer messageId, OnModelCallBack<Boolean> callBack);

        ResourceSubscriber discussMoment(Integer messageId,
                                         String comment,
                                         OnModelCallBack<Boolean> callBack);

        ResourceSubscriber replyMoment(Integer messageId,
                                       Integer toUserId,
                                       String comment,
                                       OnModelCallBack<Boolean> callBack);

        ResourceSubscriber deleteMoment(Integer messageId, OnModelCallBack<Boolean> callBack);
    }
}
