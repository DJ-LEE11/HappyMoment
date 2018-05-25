package com.uwork.happymoment.mvp.main.contract;

import com.uwork.happymoment.mvp.main.bean.RecommendBean;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/25.
 */

public interface IRecommendContract {

    interface View {
        void showRecommend(List<RecommendBean> recommendBeanList);

        void showEmpty();
    }

    interface Presenter {
        void getRecommend();
    }

    interface Model {
        ResourceSubscriber getRecommend(OnModelCallBack<List<RecommendBean>> callBack);
    }
}
