package com.uwork.happymoment;

import android.app.ActivityManager;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.example.circle_base_library.api.AppContext;
import com.example.circle_base_library.manager.localphoto.LocalPhotoManager;
import com.example.circle_common.common.manager.LocalHostManager;
import com.uwork.happymoment.listener.RongConnectionStatusListener;
import com.uwork.happymoment.mvp.social.circleTest.config.Define;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
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
//        initBmob();
//        initLocalHostInfo();
        LocalPhotoManager.INSTANCE.registerContentObserver(null);
        //初始化融云
        RongIM.init(this);
        //融云连接状态监听
        RongIM.setConnectionStatusListener(new RongConnectionStatusListener(getApplicationContext()));
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

    private void initBmob() {
        BmobConfig config = new BmobConfig.Builder(this)
                //设置appkey
                .setApplicationId(Define.BMOB_APPID)
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(15)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(1024 * 1024)
                //文件的过期时间(单位为秒)：默认1800s
                .setFileExpiration(1800)
                .build();
        Bmob.initialize(config);
    }

    private void initLocalHostInfo() {
        LocalHostManager.INSTANCE.init();
    }
}
