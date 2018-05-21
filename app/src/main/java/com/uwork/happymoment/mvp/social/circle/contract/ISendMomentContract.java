package com.uwork.happymoment.mvp.social.circle.contract;

import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/20.
 */

public interface ISendMomentContract {

    interface View {
        void sendMomentSuccess();
    }

    interface Presenter {
        void sendMoment(int userId, String content, String picture2, String city, String location, String latitude, String longitude, List<String> picture);
    }

    interface Model {
        ResourceSubscriber sendMoment(int userId, String content, String picture2, String city, String location, String latitude, String longitude, List<String> picture,OnModelCallBack<Boolean> callBack);
    }
}
