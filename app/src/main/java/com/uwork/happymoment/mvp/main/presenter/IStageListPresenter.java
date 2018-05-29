package com.uwork.happymoment.mvp.main.presenter;

import android.content.Context;

import com.uwork.happymoment.mvp.main.bean.StageItemBean;
import com.uwork.happymoment.mvp.main.contract.IStageListContract;
import com.uwork.happymoment.mvp.main.model.IStageListModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBasePresenterImpl;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

/**
 * Created by jie on 2018/5/28.
 */

public class IStageListPresenter<T extends IStageListContract.View & IBaseActivityContract.View> extends IBasePresenterImpl<T>
        implements IStageListContract.Presenter {

    private IStageListModel mModel;

    public IStageListPresenter(Context context) {
        super(context);
        mModel = new IStageListModel(context);
    }

    @Override
    public void getStageList(String name) {
        mModel.getStageList(name, new OnModelCallBack<List<StageItemBean>>() {
            @Override
            public void onStart() {
                getView().showLoading();
            }

            @Override
            public void onCancel() {
                getView().dismissLoading();
            }

            @Override
            public void onSuccess(List<StageItemBean> value) {
                getView().dismissLoading();
                if (value!=null && value.size()>0){
                    getView().showStageList(value);
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
        });
    }
}
