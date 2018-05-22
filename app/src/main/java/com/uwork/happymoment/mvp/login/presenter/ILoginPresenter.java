package com.uwork.happymoment.mvp.login.presenter;

import android.content.Context;

import com.uwork.happymoment.manager.IMRongManager;
import com.uwork.happymoment.manager.UserManager;
import com.uwork.happymoment.mvp.login.bean.UserBean;
import com.uwork.happymoment.mvp.login.contract.ILoginContract;
import com.uwork.happymoment.mvp.login.model.ILoginModel;
import com.uwork.happymoment.mvp.social.chat.bean.FriendIndexBean;
import com.uwork.happymoment.mvp.social.chat.bean.GroupBean;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBasePresenterImpl;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

/**
 * Created by jie on 2018/5/9.
 */

public class ILoginPresenter <T extends ILoginContract.View & IBaseActivityContract.View> extends IBasePresenterImpl<T>
        implements ILoginContract.Presenter {

    private ILoginModel mModel;

    public ILoginPresenter(Context context) {
        super(context);
        mModel = new ILoginModel(context);
    }

    @Override
    public void login(String phone, String password) {
        addSubscription(mModel.login(phone, password, new OnModelCallBack<UserBean>() {
            @Override
            public void onStart() {
                getView().showLoading();
            }

            @Override
            public void onCancel() {
                getView().dismissLoading();
            }

            @Override
            public void onSuccess(UserBean value) {
                getView().dismissLoading();
                saveToken(value);
                getFriend();
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
    public void saveToken(UserBean userBean) {
        UserManager.getInstance().saveUserInfo(getContext(),userBean);
    }

    @Override
    public void getFriend() {
        addSubscription(mModel.getFriendIndex(new OnModelCallBack<List<FriendIndexBean>>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onSuccess(List<FriendIndexBean> value) {
                IMRongManager.addUserInfo(mContext,value);
                getGroup();
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
    public void getGroup() {
        addSubscription(mModel.getGroupList(new OnModelCallBack<List<GroupBean>>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onSuccess(List<GroupBean> value) {
                IMRongManager.addGroupInfo(mContext,value);
                connectIM(UserManager.getInstance().getUser(mContext));
                getView().loginSuccess();
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

    //连接融云
    @Override
    public void connectIM(UserBean userBean) {
        IMRongManager.imConnect(mContext,userBean.getImtoken());
    }
}
