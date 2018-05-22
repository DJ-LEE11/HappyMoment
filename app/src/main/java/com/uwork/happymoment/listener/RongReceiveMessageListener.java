package com.uwork.happymoment.listener;

import android.content.Context;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

/**
 * Created by jie on 2018/5/22.
 * 融云接受信息接听器
 */

public class RongReceiveMessageListener implements RongIMClient.OnReceiveMessageListener {

    private Context mContext;

    public RongReceiveMessageListener(Context context) {
        mContext = context;
    }
    @Override
    public boolean onReceived(Message message, int i) {
        if(message.getConversationType() == Conversation.ConversationType.GROUP){

        }
        return false;
    }
}
