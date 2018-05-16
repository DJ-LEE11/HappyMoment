package com.uwork.happymoment.mvp.social.circle.activity.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.circle_base_library.common.entity.ImageInfo;
import com.example.circle_base_ui.util.SwitchActivityTransitionUtil;
import com.example.circle_common.common.entity.PhotoBrowseInfo;
import com.example.circle_common.common.router.RouterList;
import com.example.circle_photoselect.photoselect.PhotoSelectActivity;
import com.example.circlepublish.PublishActivity;
import com.uwork.happymoment.mvp.social.circle.activity.test.gallery.PhotoBrowseActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 大灯泡 on 2017/3/1.
 * <p>
 * activity发射器~
 */

public class ActivityLauncher {

    /**
     * 发射到发布朋友圈页面
     *
     * @param act
     * @param mode
     * @param requestCode
     */
    public static void startToPublishActivityWithResult(Activity act, @RouterList.PublishActivity int mode, @Nullable List<ImageInfo> selectedPhotos, int requestCode) {
        Intent intent = new Intent(act, PublishActivity.class);
        intent.putExtra(RouterList.PublishActivity.key_mode, mode);
        if (selectedPhotos != null) {
            intent.putParcelableArrayListExtra(RouterList.PublishActivity.key_photoList, (ArrayList<? extends Parcelable>) selectedPhotos);
        }
        act.startActivityForResult(intent, requestCode);
        SwitchActivityTransitionUtil.transitionVerticalIn(act);
    }


    public static void startToPhotoBrosweActivity(Activity act, @NonNull PhotoBrowseInfo info) {
        if (info == null) return;
        PhotoBrowseActivity.startToPhotoBrowseActivity(act, info);
    }

    /**
     * 发射到选择图片页面
     *
     * @param act
     */
    public static void startToPhotoSelectActivity(Activity act, int requestCode) {
        Intent intent = new Intent(act, PhotoSelectActivity.class);
        act.startActivityForResult(intent, requestCode);
        SwitchActivityTransitionUtil.transitionVerticalIn(act);
    }


}
