package com.uwork.happymoment.manager;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import com.uwork.happymoment.App;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.CSCustomServiceInfo;
import io.rong.imlib.model.UserInfo;

/**
 * Created by jie on 2018/5/11.
 */

public class IMRongManager {

    //连接融云
    public static void imConnect(Context context, String imToken, final String userName, final String userHead) {
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
                    //设置用户信息
                    RongIM.getInstance().setCurrentUserInfo(new UserInfo(userId, userName, Uri.parse(userHead)));
                    //设置消息体内携带用户信息
                    RongIM.getInstance().setMessageAttachedUserInfo(true);
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

    //单人聊天
    public static void privateChat(Activity activity,String targetId,String name){
        RongIM.getInstance().startPrivateChat(activity, targetId, name);
    }

    //群组聊天
    public static void groupChat(Activity activity,String groupId,String groupName){
        RongIM.getInstance().startGroupChat(activity, groupId, groupName);
    }

    //客服聊天
    public static void chatCustom(Activity activity,String customName
            ,String chatTitle,String customID,String targetId){
        CSCustomServiceInfo.Builder csBuilder = new CSCustomServiceInfo.Builder();
        CSCustomServiceInfo csInfo = csBuilder.nickName(customName).build();
        RongIM.getInstance().startCustomerServiceChat(activity, customID, chatTitle,csInfo);
        RongIMClient.getInstance().switchToHumanMode(targetId);//人工模式
    }

}