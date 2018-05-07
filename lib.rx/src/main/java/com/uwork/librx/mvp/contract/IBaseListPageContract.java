package com.uwork.librx.mvp.contract;


import com.uwork.librx.bean.PageResponseBean;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * @author 李栋杰
 * @time 2018/3/15  上午11:42
 * @desc ${TODD}
 */
public interface IBaseListPageContract {

    interface View<T> {
        void stopLoading();
        void addList(PageResponseBean<T> data);
        void showEmptyView();
        void showErrorView();
    }

    interface Presenter {
        void loadFirst();

        void loadMore();
    }

    interface Model<TWD> {
        ResourceSubscriber loadFirst(OnModelCallBack<PageResponseBean<TWD>> callBack);

        ResourceSubscriber loadNext(OnModelCallBack<PageResponseBean<TWD>> callBack);
    }
}
