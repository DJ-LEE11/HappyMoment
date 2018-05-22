package com.uwork.happymoment.listener;


import android.content.Context;

import io.rong.push.RongPushClient;
import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

/**
 * @author 李栋杰
 * @time 2017/8/27  19:27
 * @desc 融云广播接收器
 */
public class SealNotificationReceiver extends PushMessageReceiver {
    @Override
    public boolean onNotificationMessageArrived(Context context, PushNotificationMessage message) {
        if (message.getConversationType() == RongPushClient.ConversationType.PRIVATE){

        }
        return false; // 返回 false, 会弹出融云 SDK 默认通知; 返回 true, 融云 SDK 不会弹通知, 通知需要由您自定义。
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushNotificationMessage message) {
        if (message.getConversationType()== RongPushClient.ConversationType.PRIVATE || message.getConversationType()== RongPushClient.ConversationType.DISCUSSION ){
            return false; // 返回 false, 会走融云 SDK 默认处理逻辑, 即点击该通知会打开会话列表或会话界面; 返回 true, 则由您自定义处理逻辑。
        }
        return false;
    }
}
