package com.uwork.librx.mvp.model;

import android.content.Context;
import android.util.Log;

import com.uwork.librx.mvp.contract.IBaseContract;

import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.LongConsumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * @author 李栋杰
 * @time 2017/7/29  0:17
 * @desc ${TODD}
 */
public class IBaseModelImpl implements IBaseContract.Model {

    protected Context mContext;

    public IBaseModelImpl(Context context) {
        this.mContext = context;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    /**
     * 初始化通用的观察者
     * @param observable 观察者
     */
    public ResourceSubscriber startObservable(Flowable observable, ResourceSubscriber subscriber) {
        return (ResourceSubscriber) observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnLifecycle(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        Log.d("doOnLifecycle", " OnSubscribe");
                    }
                }, new LongConsumer() {
                    @Override
                    public void accept(long t) throws Exception {
                        Log.d("doOnLifecycle", " OnRequest");
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d("doOnLifecycle", " OnCancel");
                    }
                })
                .subscribeWith(subscriber);
    }
}