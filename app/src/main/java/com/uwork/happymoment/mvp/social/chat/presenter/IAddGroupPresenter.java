package com.uwork.happymoment.mvp.social.chat.presenter;

import android.content.Context;

import com.uwork.happymoment.mvp.social.chat.bean.AddGroupBean;
import com.uwork.happymoment.mvp.social.chat.contract.IAddGroupContract;
import com.uwork.happymoment.mvp.social.chat.model.IAddGroupModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBasePresenterImpl;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

/**
 * Created by jie on 2018/5/18.
 */

public class IAddGroupPresenter<T extends IAddGroupContract.View & IBaseActivityContract.View> extends IBasePresenterImpl<T>
        implements IAddGroupContract.Presenter {

    private IAddGroupModel mModel;

    public IAddGroupPresenter(Context context) {
        super(context);
        mModel = new IAddGroupModel(context);
    }

    @Override
    public void addGroup(String name, List<String> userIds) {
        addSubscription(mModel.addGroup(name, userIds, new OnModelCallBack<AddGroupBean>() {
            @Override
            public void onStart() {
                getView().showLoading();
            }

            @Override
            public void onCancel() {
                getView().dismissLoading();
            }

            @Override
            public void onSuccess(AddGroupBean value) {
                getView().dismissLoading();
                if (value != null) {
                    getView().addCreateGroup(value);
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
