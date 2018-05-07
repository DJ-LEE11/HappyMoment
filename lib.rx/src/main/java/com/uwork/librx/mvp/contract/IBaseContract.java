package com.uwork.librx.mvp.contract;

import android.content.Context;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * @author 李栋杰
 * @time 2017/7/29  0:09
 * @desc ${TODD}
 */
public class IBaseContract {
    public interface View {

    }

    public interface Presenter<V> {

        //Presenter与View关联
        public void attachView(V view);

        //Presenter与View解除关联
        public void detachView();

        public V getView();

        public Context getContext();

        //Presenter与View是否已关联
        public boolean isViewAttached();

        public void dispose(Disposable disposable);

        //取消所有的订阅
        public void dispose();

        public void addSubscription(Disposable disposable);
    }

    public interface Model {

        public Context getContext();

        //初始化通用的观察者
        public ResourceSubscriber startObservable(Flowable observable, ResourceSubscriber subscriber);

    }
}
