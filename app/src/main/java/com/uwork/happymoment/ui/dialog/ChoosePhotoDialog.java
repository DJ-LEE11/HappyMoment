package com.uwork.happymoment.ui.dialog;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.uwork.happymoment.R;
import com.uwork.happymoment.util.PhotoUtil;


/**
 * 封装了从相册/相机 获取 图片 的Dialog.
 */
public class ChoosePhotoDialog extends BaseDialog {


    public ChoosePhotoDialog(Activity context, View.OnClickListener savePhotoListener) {
        super(context);
        initView(context,savePhotoListener);
    }

    private void initView(final Activity activity,View.OnClickListener savePhotoListener) {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_choose_photo, null);
        TextView tvTakePhoto = dialogView.findViewById(R.id.tvTakePhoto);
        TextView tvAlbum = dialogView.findViewById(R.id.tvAlbum);
        TextView tvSavePhoto = dialogView.findViewById(R.id.tvSavePhoto);
        TextView tvCancel = dialogView.findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                cancel();
            }
        });
        tvTakePhoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                PhotoUtil.openCameraImage(activity);
                cancel();
            }
        });
        tvAlbum.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                PhotoUtil.openLocalImage(activity);
                cancel();
            }
        });
        tvSavePhoto.setOnClickListener(savePhotoListener);
        setContentView(dialogView);
        mLayoutParams.gravity = Gravity.BOTTOM;
    }
}
