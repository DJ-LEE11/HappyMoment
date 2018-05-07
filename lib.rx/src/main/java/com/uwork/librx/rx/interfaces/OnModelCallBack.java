package com.uwork.librx.rx.interfaces;


import com.uwork.librx.rx.http.ApiException;

/**
 * @author 李栋杰
 * @time 2017/7/29  10:15
 * @desc 回调
 */
public interface OnModelCallBack<T> {

    void onStart();

    void onCancel();

    void onSuccess(T value);

    void onError(ApiException e);

    void onComplete();
}
