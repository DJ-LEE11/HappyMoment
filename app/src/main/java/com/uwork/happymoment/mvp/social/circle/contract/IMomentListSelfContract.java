package com.uwork.happymoment.mvp.social.circle.contract;

import com.uwork.librx.mvp.contract.IBaseListPageContract;

/**
 * Created by jie on 2018/5/23.
 */

public interface IMomentListSelfContract {

    interface View <T> extends IBaseListPageContract.View<T>{
    }

    interface Presenter extends IBaseListPageContract.Presenter{
    }

    interface Model <T> extends IBaseListPageContract.Model<T>{
    }
}
