package com.uwork.happymoment.mvp.social.circle.presenter;

import android.content.Context;

import com.uwork.happymoment.mvp.social.circle.bean.HomeCircleBean;
import com.uwork.happymoment.mvp.social.circle.contract.IMomentListSelfContract;
import com.uwork.happymoment.mvp.social.circle.model.IMomentListSelfModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBaseListPagePresenter;

/**
 * Created by jie on 2018/5/23.
 */

public class IMomentListSelfPresenter<T extends IMomentListSelfContract.View & IBaseActivityContract.View>
        extends IBaseListPagePresenter<T, HomeCircleBean> implements IMomentListSelfContract.Presenter {
    public IMomentListSelfPresenter(Context context) {
        super(context);
        mModel = new IMomentListSelfModel(context);
    }
}
