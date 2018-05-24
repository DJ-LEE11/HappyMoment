package com.uwork.happymoment.mvp.social.circle.model;

import android.content.Context;

import com.uwork.happymoment.BuildConfig;
import com.uwork.happymoment.api.ApiService;
import com.uwork.happymoment.manager.ConstantManager;
import com.uwork.happymoment.mvp.social.circle.bean.HomeCircleBean;
import com.uwork.happymoment.mvp.social.circle.contract.IMomentListSelfContract;
import com.uwork.librx.bean.PageResponseBean;
import com.uwork.librx.mvp.model.IBaseListPageModel;
import com.uwork.librx.rx.http.ApiServiceFactory;
import com.uwork.librx.rx.http.CustomResourceSubscriber;
import com.uwork.librx.rx.http.HttpResultFunc;
import com.uwork.librx.rx.http.ServerResultFunc;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/23.
 */

public class IMomentListSelfModel extends IBaseListPageModel<HomeCircleBean> implements IMomentListSelfContract.Model<HomeCircleBean> {

    public IMomentListSelfModel(Context context) {
        super(context);
    }

    @Override
    public ResourceSubscriber loadNext(OnModelCallBack<PageResponseBean<HomeCircleBean>> callBack) {
        return startObservable(ApiServiceFactory.INSTANCE
                        .create(getContext(), BuildConfig.API_BASE_URL, ApiService.class)
                        .getMomentsListSelf(mPager == null ? ConstantManager.DEFAULT_THE_FIRST_PAGE_INDEX : (mPager.getPageNum() + 1)
                                , ConstantManager.DEFAULT_PAGE_SIZE)
                        .map(new ServerResultFunc<>())
                        .onErrorResumeNext(new HttpResultFunc<>())
                , new CustomResourceSubscriber<PageResponseBean<HomeCircleBean>>(callBack) {
                    @Override
                    public void onNext(PageResponseBean<HomeCircleBean> pageResponseBean) {
                        if (pageResponseBean != null) {
                            mPager = pageResponseBean;
                        }
                        super.onNext(pageResponseBean);
                    }
                });
    }
}
