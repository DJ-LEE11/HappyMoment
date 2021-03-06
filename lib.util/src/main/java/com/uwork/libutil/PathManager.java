package com.uwork.libutil;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class PathManager {

    /**
     * 存放图片
     *
     * @param context
     * @return
     * @Title getDiskFileDir
     * @Description
     * @author 李庆育
     * @date 2015-12-21 下午5:27:09
     */
    public static String getDiskFileDir(Context context) {
        String picDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            File externalDir = context
                    .getExternalFilesDir(Environment.DIRECTORY_DCIM);
            if (externalDir != null && externalDir.exists()) {
                picDir = externalDir.getPath();
            }
        }
        if (picDir == null) {
            File externalDir = context.getFilesDir();
            if (externalDir != null && externalDir.exists()) {
                picDir = externalDir.getPath();
            }
        }
        return picDir;
    }

    /**
     * @param context
     * @return app_cache_path/dirName
     */
    public static String getDiskCacheDir(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            File externalCacheDir = context.getExternalCacheDir();
            if (externalCacheDir != null && externalCacheDir.exists()) {
                cachePath = externalCacheDir.getPath();
            }
        }
        if (cachePath == null) {
            File cacheDir = context.getCacheDir();
            if (cacheDir != null && cacheDir.exists()) {
                cachePath = cacheDir.getPath();
            }
        }
        return cachePath;
    }

    /**
     * 获取sd卡路径
     *
     * @return
     * @Title getSdcardDir
     * @Description
     * @author 李庆育
     * @date 2015-12-21 下午5:27:43
     */
    public static String getSdcardDir() {
        String sdPath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            File externalCacheDir = Environment.getExternalStorageDirectory();
            if (externalCacheDir != null && externalCacheDir.exists()) {
                sdPath = externalCacheDir.getPath();
                if (!sdPath.endsWith(File.separator)) {
                    sdPath += File.separator;
                }
            }
        }
        if (sdPath == null) {
            File cacheDir = Environment.getDownloadCacheDirectory();
            if (cacheDir != null && cacheDir.exists()) {
                sdPath = cacheDir.getPath();
                if (!sdPath.endsWith(File.separator)) {
                    sdPath += File.separator;
                }
            }
        }
        return sdPath;
    }

    public static String getDownLoadDir() {
        String dir = "";
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            File downloadFile = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            if (downloadFile != null && downloadFile.exists()) {
                dir = downloadFile.getPath();
                if (!dir.endsWith(File.separator)) {
                    dir += File.separator;
                }
                return dir;
            } else {
                downloadFile = Environment.getExternalStorageDirectory();
                if (downloadFile != null && downloadFile.exists()) {
                    dir = downloadFile.getPath();
                    if (!dir.endsWith(File.separator)) {
                        dir += File.separator;
                    }
                    return dir;
                }
            }
        }
        return null;
    }

}
