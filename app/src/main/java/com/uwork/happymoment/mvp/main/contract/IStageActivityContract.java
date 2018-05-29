package com.uwork.happymoment.mvp.main.contract;

import com.uwork.happymoment.mvp.main.bean.StageActivityDetailBean;
import com.uwork.librx.rx.BaseResult;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/29.
 */

public interface IStageActivityContract {

    interface View {
        void showDetail(StageActivityDetailBean stageActivityDetailBean);

        void joinSuccess();
    }

    interface Presenter {
        void getStageActivityDetail(Integer id);

        void joinStageActivity(int activityId, String address, String startTime, String endTime);
    }

    interface Model {
        ResourceSubscriber getStageActivityDetail(Integer id,OnModelCallBack<StageActivityDetailBean> callBack);

        ResourceSubscriber joinStageActivity(int activityId, String address, String startTime, String endTime, OnModelCallBack<BaseResult<String>> callBack);
    }
}
