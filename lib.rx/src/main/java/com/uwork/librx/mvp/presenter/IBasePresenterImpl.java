package com.uwork.librx.mvp.presenter;

import android.content.Context;

import com.uwork.librx.mvp.contract.IBaseContract;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author 李栋杰
 * @time 2017/7/29  0:18
 * @desc ${TODD}
 */
public class IBasePresenterImpl <V> implements IBaseContract.Presenter<V>{

    protected WeakReference<V> mViewReference;
    private CompositeDisposable mDisposables;
    protected Context mContext;

    public IBasePresenterImpl(Context context) {
        this.mContext = context;
    }

    /**
     * Presenter与View关联
     * @param view
     */
    public void attachView(V view){
        mViewReference = new WeakReference<V>(view);
    }

    /**
     * Presenter与View解除关联
     */
    public void detachView(){
        if(mViewReference != null){
            mViewReference.clear();
            mViewReference = null;
        }
    }

    public V getView(){
        if(mViewReference != null){
            return mViewReference.get();
        }
        return null;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    /**
     * Presenter与View是否已关联
     * @return
     */
    public boolean isViewAttached(){
        return mViewReference != null && mViewReference.get() != null;
    }

    public void dispose(Disposable disposable){
        if(mDisposables!=null){
            mDisposables.delete(disposable);
        }
    }
    //取消所有的订阅
    public void dispose(){
        if(mDisposables!=null){
            mDisposables.clear();
        }
    }

    public void addSubscription(Disposable disposable) {
        if (disposable == null) return;
        if (mDisposables == null) {
            mDisposables = new CompositeDisposable();
        }
        mDisposables.add(disposable);
    }
}
