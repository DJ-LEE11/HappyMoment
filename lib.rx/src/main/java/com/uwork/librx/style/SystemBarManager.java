package com.uwork.librx.style;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by lenovo on 2017-01-19.
 */

public class SystemBarManager {

    /**
     * Apply KitKat specific translucency.
     */
    public   static  void applyKitKatTranslucency(Activity act, int colorResource) {
        // KitKat translucent navigation/status bar.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(act,true);
            SystemBarTintManager mTintManager = new SystemBarTintManager(act);

            mTintManager.setStatusBarTintEnabled(true);

            mTintManager.setStatusBarTintResource(colorResource);//通知栏所需颜色

        }
    }

    @TargetApi(19)
    public static void setTranslucentStatus(Activity act, boolean on) {
        Window win = act.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
