package com.uwork.happymoment.mvp.social.chat.presenter;

import android.content.Context;

import com.uwork.happymoment.mvp.social.chat.bean.GroupMemberBean;
import com.uwork.happymoment.mvp.social.chat.contract.IGetGroupMemberContract;
import com.uwork.happymoment.mvp.social.chat.model.IGetGroupMemberModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBasePresenterImpl;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

/**
 * Created by jie on 2018/5/23.
 */

public class IGetGroupMemberPresenter <T extends IGetGroupMemberContract.View & IBaseActivityContract.View> extends IBasePresenterImpl<T>
        implements IGetGroupMemberContract.Presenter {

    private IGetGroupMemberModel mModel;

    public IGetGroupMemberPresenter(Context context) {
        super(context);
        mModel = new IGetGroupMemberModel(context);
    }

    @Override
    public void getGroupMember(Integer groupId) {
        addSubscription(mModel.getGroupMember(groupId, new OnModelCallBack<List<GroupMemberBean>>() {
            @Override
            public void onStart() {
                getView().showLoading();
            }

            @Override
            public void onCancel() {
                getView().dismissLoading();
            }

            @Override
            public void onSuccess(List<GroupMemberBean> value) {
                getView().dismissLoading();
                if (value!=null && value.size()>0){
                    getView().showGroupMember(value);
                }else {
                    getView().showEmpty();
                }
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
