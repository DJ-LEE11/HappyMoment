package com.uwork.happymoment.mvp.main.contract;

import com.uwork.happymoment.mvp.main.bean.BannerBean;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/25.
 */

public interface IBannerContract {

    interface View {
        void shoBanner(Integer type ,List<BannerBean> bannerBeanList);
    }

    interface Presenter {
        void getBanner(Integer type);
    }

    interface Model {
        ResourceSubscriber getBanner(Integer type, OnModelCallBack<List<BannerBean>> callBack);
    }
}
