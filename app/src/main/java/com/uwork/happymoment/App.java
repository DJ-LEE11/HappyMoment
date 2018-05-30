package com.uwork.happymoment;

import android.app.ActivityManager;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.example.circle_base_library.api.AppContext;
import com.example.circle_base_library.manager.localphoto.LocalPhotoManager;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
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
        //友盟Debug调试
        Config.DEBUG = BuildConfig.isDebug;
        UMShareAPI.get(this);
    }

    //友盟分享各个平台的配置
    {
        PlatformConfig.setWeixin(BuildConfig.WechatId,BuildConfig.WechatSecret);
        PlatformConfig.setQQZone(BuildConfig.QQId,BuildConfig.QQKey);
        PlatformConfig.setSinaWeibo(BuildConfig.SinaKey,BuildConfig.SinaSecret,"http://sns.whalecloud.com/sina2/callback");
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
