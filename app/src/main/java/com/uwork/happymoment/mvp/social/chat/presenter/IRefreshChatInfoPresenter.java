package com.uwork.happymoment.mvp.social.chat.presenter;

import android.content.Context;

import com.uwork.happymoment.manager.IMRongManager;
import com.uwork.happymoment.mvp.social.chat.bean.GroupBean;
import com.uwork.happymoment.mvp.social.chat.bean.SearchNewFriendBean;
import com.uwork.happymoment.mvp.social.chat.contract.IRefreshChatContract;
import com.uwork.happymoment.mvp.social.chat.model.IRefreshChatModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBasePresenterImpl;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

/**
 * Created by jie on 2018/5/22.
 */

public class IRefreshChatInfoPresenter<T extends IRefreshChatContract.View & IBaseActivityContract.View> extends IBasePresenterImpl<T>
        implements IRefreshChatContract.Presenter {

    private IRefreshChatModel mModel;

    public IRefreshChatInfoPresenter(Context context) {
        super(context);
        mModel = new IRefreshChatModel(context);
    }

    @Override
    public void refreshGroup(String groupId) {
        addSubscription(mModel.getGroupList(new OnModelCallBack<List<GroupBean>>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onSuccess(List<GroupBean> value) {
                if (value!=null && value.size()>0){
                    for (GroupBean groupBean:value){
                        if (groupBean.getId() == Integer.valueOf(groupId)){
                            IMRongManager.updateGroupInfo(mContext,groupBean.getId(),groupBean.getName(),groupBean.getAvatar());
                        }
                    }
                }
            }

            @Override
            public void onError(ApiException e) {
                getView().handleException(e);
            }

            @Override
            public void onComplete() {

            }
        }));
    }

    @Override
    public void refreshUser(String userId) {
        addSubscription(mModel.getUserInfo(userId, new OnModelCallBack<SearchNewFriendBean>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onSuccess(SearchNewFriendBean value) {
                if (value!=null){
                    IMRongManager.updateUserInfo(getContext(),value.getId()+"",value.getNickName(),value.getAvatar());
                }
            }

            @Override
            public void onError(ApiException e) {
                getView().handleException(e);
            }

            @Override
            public void onComplete() {

            }
        }));
    }
}
