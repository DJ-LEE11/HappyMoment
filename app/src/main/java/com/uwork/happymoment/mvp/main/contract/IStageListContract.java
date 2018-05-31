package com.uwork.happymoment.mvp.main.contract;

import com.uwork.happymoment.mvp.main.bean.StageItemBean;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/28.
 */

public interface IStageListContract {

    interface View {
        void showStageList(List<StageItemBean> stageItemBeanList);

        void showEmpty();
    }

    interface Presenter {
        void getStageList(String location, String name);
    }

    interface Model {
        ResourceSubscriber getStageList(String location, String name,OnModelCallBack<List<StageItemBean>> callBack);
    }
}
