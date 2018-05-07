package com.uwork.librx.rx.http;


import com.uwork.librx.rx.interfaces.OnModelCallBack;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * @author 李栋杰
 * @time 2017/7/29  10:14
 * @desc ${TODD}
 */
public class CustomResourceSubscriber<T> extends ResourceSubscriber<T> {
    public OnModelCallBack callBack;

    public CustomResourceSubscriber() {
    }

    public CustomResourceSubscriber(OnModelCallBack<T> onModelCallBack) {
        this.callBack = onModelCallBack;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (callBack != null) {
            callBack.onStart();
        }
    }

    @Override
    public void onNext(T o) {
        if (callBack != null) {
            callBack.onSuccess(o);
        }
    }

    @Override
    public void onError(Throwable t) {
        this.dispose();
        if (callBack != null) {
            if (t instanceof ApiException) {
                callBack.onError((ApiException) t);
            } else {
                callBack.onError(new ApiException(ApiException.ERROR.UNKNOWN, t));
            }
        }
    }

    @Override
    public void onComplete() {
        this.dispose();
        if (callBack != null) {
            callBack.onComplete();
        }
    }
}
