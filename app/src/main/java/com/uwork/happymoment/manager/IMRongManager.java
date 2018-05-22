package com.uwork.happymoment.manager;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.uwork.happymoment.App;
import com.uwork.happymoment.mvp.social.chat.bean.FriendBean;
import com.uwork.happymoment.mvp.social.chat.bean.FriendIndexBean;
import com.uwork.happymoment.mvp.social.chat.bean.GroupBean;
import com.uwork.happymoment.mvp.social.chat.presenter.IRefreshGroupPresenter;

import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.CSCustomServiceInfo;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;

/**
 * Created by jie on 2018/5/11.
 */

public class IMRongManager {

    //连接融云
    public static void imConnect(Context context, String imToken) {
        setUserInfo(context);
        setGroupInfo(context);
        if (context.getApplicationInfo().packageName.equals(App.getCurProcessName(context.getApplicationContext()))) {
            RongIM.connect(imToken, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 * 2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {
                }

                /**
                 * 连接融云成功
                 *
                 * @param userId 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(final String userId) {

                }

                /**
                 * 连接融云失败
                 *
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                }
            });
        }
    }

    //设置用户信息
    public static void setUserInfo(Context context) {
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String userId) {
                if (TextUtils.isEmpty(userId)) {
                    return null;
                }
                FriendBean friend = FriendManager.getInstance().getFriend(context, userId);
                if (friend != null) {
                    return new UserInfo(userId, friend.getName(), Uri.parse(friend.getAvatar()));
                }
                return null;
            }
        }, true);
    }

    //添加用户信息
    public static void addUserInfo(Context context,List<FriendIndexBean> friendList) {
        if (friendList != null && friendList.size() > 0) {
            for (FriendIndexBean bean:friendList){
                String name = TextUtils.isEmpty(bean.getRemarksName()) ? bean.getNickName() : bean.getRemarksName();
                FriendManager.getInstance().saveFriend(context,bean.getId()+"",new FriendBean(bean.getId()+"",name,bean.getAvatar()));
            }
        }
    }

    //刷新用户信息
    public static void updateUserInfo(Context context, String userId, String name, String avatarUrl) {
        FriendManager.getInstance().saveFriend(context,userId,new FriendBean(userId,name,avatarUrl));
        RongIM.getInstance().refreshUserInfoCache(new UserInfo(userId, name, Uri.parse(avatarUrl)));
    }


    //设置群组信息
    public static void setGroupInfo(Context context) {
        RongIM.setGroupInfoProvider(new RongIM.GroupInfoProvider() {
            @Override
            public Group getGroupInfo(String groupId) {
                if (TextUtils.isEmpty(groupId)) {
                    return null;
                }
                GroupBean groupBean = GroupManager.getInstance().getGroup(context, groupId);
                if (groupBean != null){
                    return  new Group(groupId, groupBean.getName(), Uri.parse(groupBean.getAvatar()));
                }else {
                    IRefreshGroupPresenter iRefreshGroupPresenter = new IRefreshGroupPresenter(context);
                    iRefreshGroupPresenter.refreshGroup(groupId);
                }
                return null;
            }
        }, true);
    }


    //添加群组信息
    public static void addGroupInfo(Context context,List<GroupBean> groupBeans) {
        if (groupBeans!=null && groupBeans.size()>0){
            for (GroupBean bean : groupBeans){
                GroupManager.getInstance().saveGroup(context,bean.getId()+"",bean);
            }
        }
    }

    //刷新群组信息
    public static void updateGroupInfo(Context context, int groupId, String name, String avatarUrl) {
        GroupBean groupBean = new GroupBean(groupId,name,avatarUrl);
        GroupManager.getInstance().saveGroup(context,groupId+"",groupBean);
        Group group = new Group(groupId+"", name, Uri.parse(avatarUrl));
        RongIM.getInstance().refreshGroupInfoCache(group);
    }


    //单人聊天
    public static void privateChat(Activity activity, String targetId, String title) {
        RongIM.getInstance().startPrivateChat(activity, targetId, title);
    }

    //群组聊天
    public static void groupChat(Activity activity, String groupId, String groupName) {
        RongIM.getInstance().startGroupChat(activity, groupId, groupName);
    }

    //客服聊天
    public static void chatCustom(Activity activity, String customName
            , String chatTitle, String customID, String targetId) {
        CSCustomServiceInfo.Builder csBuilder = new CSCustomServiceInfo.Builder();
        CSCustomServiceInfo csInfo = csBuilder.nickName(customName).build();
        RongIM.getInstance().startCustomerServiceChat(activity, customID, chatTitle, csInfo);
        RongIMClient.getInstance().switchToHumanMode(targetId);//人工模式
    }
}
