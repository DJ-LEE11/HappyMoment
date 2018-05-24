package com.uwork.happymoment;

import android.app.ActivityManager;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.example.circle_base_library.api.AppContext;
import com.example.circle_base_library.manager.localphoto.LocalPhotoManager;
import com.uwork.happymoment.listener.RongConnectionStatusListener;
import com.uwork.happymoment.listener.RongReceiveMessageListener;

import io.rong.imkit.RongIM;

/**
 * @author 李栋杰
 * @time 2018/5/7  上午11:36
 * @desc ${TODD}
 */
public class App extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        //朋友圈
        AppContext.initARouter();
        LocalPhotoManager.INSTANCE.registerContentObserver(null);
        //初始化融云
        RongIM.init(this);
        //融云连接状态监听
        RongIM.setConnectionStatusListener(new RongConnectionStatusListener(getApplicationContext()));
        //融云接受消息监听
        RongIM.setOnReceiveMessageListener(new RongReceiveMessageListener(getApplicationContext()));
        //初始化百度地图
        SDKInitializer.initialize(this);
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
