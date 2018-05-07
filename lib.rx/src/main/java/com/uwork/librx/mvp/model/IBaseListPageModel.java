package com.uwork.librx.mvp.model;

import android.content.Context;

import com.uwork.librx.bean.PageResponseBean;
import com.uwork.librx.mvp.contract.IBaseListPageContract;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * @author 李栋杰
 * @time 2018/3/15  上午11:42
 * @desc ${TODD}
 */
public abstract class IBaseListPageModel<T> extends IBaseModelImpl implements IBaseListPageContract.Model<T> {

    protected PageResponseBean<T> mPager;

    public IBaseListPageModel(Context context) {
        super(context);
    }

    @Override
    public ResourceSubscriber loadFirst(OnModelCallBack<PageResponseBean<T>> callBack) {
        this.mPager = null;
        return loadNext(callBack);
    }
}
