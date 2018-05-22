package com.uwork.happymoment.mvp.social.chat.contract;

import com.uwork.happymoment.mvp.social.chat.bean.GroupBean;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/22.
 */

public interface IRefreshGroupContract {

    interface View {
    }

    interface Presenter {
        void refreshGroup(String groupId);
    }

    interface Model {
        ResourceSubscriber getGroupList(OnModelCallBack<List<GroupBean>> callBack);
    }
}
