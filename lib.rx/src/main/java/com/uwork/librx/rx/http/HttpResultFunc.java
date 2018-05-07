package com.uwork.librx.rx.http;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * @author 李栋杰
 * @time 2017/7/29  16:18
 * @desc ${TODD}
 */
public class HttpResultFunc <T> implements Function<Throwable, Flowable<T>> {

    @Override
    public Flowable<T> apply(Throwable throwable) throws Exception {
        return Flowable.error(ExceptionManager.handle(throwable));
    }
}
