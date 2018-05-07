package com.uwork.libutil;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;

public class AppUtils {

    /**
     * 获取app版本名
     *
     * @return 返回当前版本名
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            return info.versionName;
        } catch (NameNotFoundException e) {
            return "1.0";
        }
    }

    /**
     * 获取app版本号
     *
     * @return 返回当前版本号
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            return info.versionCode;
        } catch (NameNotFoundException e) {
            return 1;
        }
    }

    /**
     * 获取app包名
     *
     * @return 返回包名
     */
    public static String getPackageName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            return info.packageName;
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    /**
     * @param context
     * @return Drawable對象
     * @Title getAppIcon
     * @Description 获取应用图标
     * @author 陈国宏
     * @date 2014年1月3日 下午8:45:09
     */
    public static Drawable getAppIcon(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            Drawable drawable = info.applicationInfo.loadIcon(manager);
            return drawable;
        } catch (NameNotFoundException e) {
            return null;
        }
    }



    private String getSign(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(
                    AppUtils.getPackageName(context), PackageManager.GET_SIGNATURES);
            android.content.pm.Signature[] sigs = info.signatures;
            // XLog.i("ANDROID_LAB", "sigs.len=" + sigs.length);
            // XLog.i("ANDROID_LAB", sigs[0].toCharsString());
            // XLog.i("ANDROID_LAB",SHA1Util.sum(sigs[0].toCharsString()));
            return sigs[0].toCharsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String getMetaData(Context context, String key) {
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            String msg = appInfo.metaData.getString(key);
            return msg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}