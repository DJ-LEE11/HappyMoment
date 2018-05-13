package com.uwork.happymoment.listener;

import android.content.Context;

import com.kw.rxbus.RxBus;
import com.uwork.librx.bean.LoginEvent;
import com.uwork.libutil.ToastUtils;

import io.rong.imlib.RongIMClient;

/**
 * @author 李栋杰
 * @time 2017/9/22  下午5:45
 * @desc 融云连接状态监听
 */
public class RongConnectionStatusListener implements RongIMClient.ConnectionStatusListener {

    private Context mContext;

    public RongConnectionStatusListener(Context context) {
        mContext = context;
    }

    @Override
    public void onChanged(ConnectionStatus connectionStatus) {

        switch (connectionStatus){

            case CONNECTED://连接成功。
                break;
            case DISCONNECTED://断开连接。

                break;
            case CONNECTING://连接中。

                break;
            case NETWORK_UNAVAILABLE://网络不可用。
                ToastUtils.show(mContext,"网络不可用");
                break;
            case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机被踢掉线
                ToastUtils.show(mContext,"该账号已在其他设备中登录");
                RxBus.getInstance().send(new LoginEvent(true));
                break;
        }
    }
}