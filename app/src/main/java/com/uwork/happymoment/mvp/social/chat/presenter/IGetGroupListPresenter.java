package com.uwork.happymoment.mvp.social.chat.presenter;

import android.content.Context;

import com.uwork.happymoment.mvp.social.chat.bean.GroupBean;
import com.uwork.happymoment.mvp.social.chat.contract.IGetGroupListContract;
import com.uwork.happymoment.mvp.social.chat.model.IGetGroupListModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBasePresenterImpl;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

/**
 * Created by jie on 2018/5/22.
 */

public class IGetGroupListPresenter <T extends IGetGroupListContract.View & IBaseActivityContract.View> extends IBasePresenterImpl<T>
        implements IGetGroupListContract.Presenter {

    private IGetGroupListModel mModel;

    public IGetGroupListPresenter(Context context) {
        super(context);
        mModel = new IGetGroupListModel(context);
    }

    @Override
    public void getGroupList() {
        addSubscription(mModel.getGroupList(new OnModelCallBack<List<GroupBean>>() {
            @Override
            public void onStart() {
                getView().showLoading();
            }

            @Override
            public void onCancel() {
                getView().dismissLoading();
            }

            @Override
            public void onSuccess(List<GroupBean> value) {
                getView().dismissLoading();
                if (value!=null&& value.size()>0){
                    getView().showGroupList(value);
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
