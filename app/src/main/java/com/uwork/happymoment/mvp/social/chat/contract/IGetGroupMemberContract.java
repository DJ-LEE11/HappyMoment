package com.uwork.happymoment.mvp.social.chat.contract;

import com.uwork.happymoment.mvp.social.chat.bean.GroupMemberBean;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/23.
 */

public interface IGetGroupMemberContract {

    interface View {
        void showGroupMember(List<GroupMemberBean> groupMemberBeanList);

        void showEmpty();
    }

    interface Presenter {
        void getGroupMember(Integer groupId);
    }

    interface Model {
        ResourceSubscriber getGroupMember(Integer groupId,OnModelCallBack<List<GroupMemberBean>> callBack);
    }
}
