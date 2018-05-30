package com.uwork.happymoment.util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.DrawableRes;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.uwork.libutil.ToastUtils;

/**
 * @author 李栋杰
 * @time 2018/1/2  下午5:18
 * @desc 第三方登录、分享
 */
public class ShareUtil {

    private static Context mContext;

    public static  void shareText(Activity activity, SHARE_MEDIA platform, String content){
        mContext = activity;
        new ShareAction(activity)
                .setPlatform(platform)//传入平台
                .withText(content)//分享内容
                .setCallback(shareListener)//回调监听器
                .share();
    }

    public static void shareUrl(Activity activity, SHARE_MEDIA platform, String url,
                                String title, String thumb, String des) {
        shareUrl(activity, platform, url,
                title, thumb, des, shareListener);
    }

    public static void shareUrl(Activity activity, SHARE_MEDIA platform, String url,
                                String title, @DrawableRes int thumbRes, String des) {
        shareUrl(activity, platform, url,
                title, thumbRes, des, shareListener);
    }


    public static void shareUrl(Activity activity, SHARE_MEDIA platform, String url,
                                String title, String thumb, String des, UMShareListener uMShareListener) {
        mContext = activity;
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        web.setThumb(new UMImage(activity, thumb));  //缩略图
        web.setDescription(des);//描述

        new ShareAction(activity)
                .setPlatform(platform)
                .withMedia(web)
                .setCallback(uMShareListener)
                .share();

    }

    public static void shareUrl(Activity activity, SHARE_MEDIA platform, String url,
                                String title, @DrawableRes int thumbRes, String des, UMShareListener uMShareListener) {
        mContext = activity;
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        web.setThumb(new UMImage(activity, thumbRes));  //缩略图
        web.setDescription(des);//描述

        new ShareAction(activity)
                .setPlatform(platform)
                .withMedia(web)
                .setCallback(uMShareListener)
                .share();

    }

    public static void shareUrl(Activity activity, SHARE_MEDIA platform, String url,
                                String title, String des) {
        shareUrl(activity, platform, url,
                title, des, shareListener);
    }

    public static void shareUrl(Activity activity, SHARE_MEDIA platform, String url,
                                String title, String des, UMShareListener uMShareListener) {
        mContext = activity;
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        web.setDescription(des);//描述
        new ShareAction(activity)
                .setPlatform(platform)
                .withMedia(web)
                .setCallback(uMShareListener)
                .share();

    }

    //默认分享回调
    private static UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            ToastUtils.show(mContext, "正在分享中，请稍等");
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtils.show(mContext, "分享成功");
            mContext = null;
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.show(mContext, "分享失败");
            mContext = null;
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.show(mContext, "取消分享");
            mContext = null;
        }
    };

}
