package com.uwork.librx.mvp.presenter;

import android.content.Context;

import com.uwork.librx.bean.PageResponseBean;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.contract.IBaseListPageContract;
import com.uwork.librx.mvp.model.IBaseListPageModel;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;


/**
 * @author 李栋杰
 * @time 2018/3/20  上午10:59
 * @desc ${TODD}
 */
public class IBaseListPagePresenter<T extends IBaseListPageContract.View & IBaseActivityContract.View, D>
        extends IBasePresenterImpl<T> implements IBaseListPageContract.Presenter{

    protected boolean mIsRefreshing;
    protected OnModelCallBack<PageResponseBean<D>> mOnModelCallBack;
    protected IBaseListPageModel mModel;

    public IBaseListPagePresenter(Context context) {
        super(context);
        mOnModelCallBack = new OnModelCallBack<PageResponseBean<D>>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCancel() {
                getView().stopLoading();
            }

            @Override
            public void onSuccess(PageResponseBean<D> value) {
                if (value == null) {
                    getView().showEmptyView();
                    getView().stopLoading();
                    return;
                }
                getView().addList(value);
            }

            @Override
            public void onError(ApiException e) {
                getView().stopLoading();
                getView().handleException(e);
            }

            @Override
            public void onComplete() {
                getView().stopLoading();
            }
        };
    }

    @Override
    public void loadFirst() {
        mIsRefreshing = true;
        addSubscription(mModel.loadFirst(mOnModelCallBack));
    }

    @Override
    public void loadMore() {
        mIsRefreshing = false;
        addSubscription(mModel.loadNext(mOnModelCallBack));
    }
}
