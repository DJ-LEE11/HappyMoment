package com.uwork.happymoment.mvp.social.circle.presenter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.circle_base_ui.widget.commentwidget.CommentWidget;
import com.uwork.happymoment.mvp.social.circle.bean.MomentCommentBean;
import com.uwork.happymoment.mvp.social.circle.bean.MomentLikeBean;
import com.uwork.happymoment.mvp.social.circle.contract.ICircleHandelContract;
import com.uwork.happymoment.mvp.social.circle.model.ICircleHandelModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBasePresenterImpl;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

/**
 * Created by jie on 2018/5/22.
 */

public class ICircleHandelPresenter <T extends ICircleHandelContract.View & IBaseActivityContract.View> extends IBasePresenterImpl<T>
        implements ICircleHandelContract.Presenter {

    private ICircleHandelModel mModel;

    public ICircleHandelPresenter(Context context) {
        super(context);
        mModel = new ICircleHandelModel(context);
    }

    @Override
    public void giveLikeMoment(int itemPosition, List<MomentLikeBean> momentLikeBeanList, Integer messageId) {
        addSubscription(mModel.giveLikeMoment(messageId, new OnModelCallBack<Boolean>() {
            @Override
            public void onStart() {
                getView().showLoading();
            }

            @Override
            public void onCancel() {
                getView().dismissLoading();
            }

            @Override
            public void onSuccess(Boolean value) {
                getView().dismissLoading();
                getView().giveLikeSuccess(itemPosition, momentLikeBeanList);
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

    @Override
    public void showCommentBox(@Nullable View viewHolderRootView, int itemPos, int messageId , @Nullable CommentWidget commentWidget) {
        getView().showCommentBox(viewHolderRootView,itemPos,messageId,commentWidget);
    }

    @Override
    public void discussMoment(Integer messageId, String comment, int itemPosition, List<MomentCommentBean> momentCommentBeanList) {
        addSubscription(mModel.discussMoment(messageId, comment, new OnModelCallBack<Boolean>() {
            @Override
            public void onStart() {
                getView().showLoading();
            }

            @Override
            public void onCancel() {
                getView().dismissLoading();
            }

            @Override
            public void onSuccess(Boolean value) {
                getView().dismissLoading();
                getView().discussMomentSuccess(itemPosition,comment,momentCommentBeanList);
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

    @Override
    public void replyMoment(Integer messageId, Integer toUserId, String toUserName,String comment, int itemPosition, List<MomentCommentBean> momentCommentBeanList) {
        addSubscription(mModel.replyMoment(messageId, toUserId, comment, new OnModelCallBack<Boolean>() {
            @Override
            public void onStart() {
                getView().showLoading();
            }

            @Override
            public void onCancel() {
                getView().dismissLoading();
            }

            @Override
            public void onSuccess(Boolean value) {
                getView().dismissLoading();
                getView().replyMomentSuccess(itemPosition,toUserId,toUserName,comment,momentCommentBeanList);
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

    @Override
    public void deleteMoment(int itemPosition, Integer messageId) {
        addSubscription(mModel.deleteMoment(messageId, new OnModelCallBack<Boolean>() {
            @Override
            public void onStart() {
                getView().showLoading();
            }

            @Override
            public void onCancel() {
                getView().dismissLoading();
            }

            @Override
            public void onSuccess(Boolean value) {
                getView().dismissLoading();
                getView().deleteMomentSuccess(itemPosition);
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
