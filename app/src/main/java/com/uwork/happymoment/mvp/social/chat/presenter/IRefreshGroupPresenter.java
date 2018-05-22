package com.uwork.happymoment.mvp.social.chat.presenter;

import android.content.Context;

import com.uwork.happymoment.manager.IMRongManager;
import com.uwork.happymoment.mvp.social.chat.bean.GroupBean;
import com.uwork.happymoment.mvp.social.chat.contract.IRefreshGroupContract;
import com.uwork.happymoment.mvp.social.chat.model.IRefreshGroupModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBasePresenterImpl;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

/**
 * Created by jie on 2018/5/22.
 */

public class IRefreshGroupPresenter <T extends IRefreshGroupContract.View & IBaseActivityContract.View> extends IBasePresenterImpl<T>
        implements IRefreshGroupContract.Presenter {

    private IRefreshGroupModel mModel;

    public IRefreshGroupPresenter(Context context) {
        super(context);
        mModel = new IRefreshGroupModel(context);
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
}
