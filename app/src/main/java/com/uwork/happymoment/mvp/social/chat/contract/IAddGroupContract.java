package com.uwork.happymoment.mvp.social.chat.contract;

import com.uwork.happymoment.mvp.social.chat.bean.AddGroupBean;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/18.
 */

public interface IAddGroupContract {

    interface View {
        void addCreateGroup(AddGroupBean addGroupBean);
    }

    interface Presenter {
        void addGroup(String name, List<String> userIds);
    }

    interface Model {
        ResourceSubscriber addGroup(String name, List<String> userIds, OnModelCallBack<AddGroupBean> callBack);
    }
}
