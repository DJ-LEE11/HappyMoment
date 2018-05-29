package com.uwork.happymoment.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.uwork.happymoment.R;

/**
 * Created by jie on 2018/5/29.
 */

public class SelectRouteDialog {

    //确定对话框
    public static Dialog createSelectRouteDialog(Context context, View.OnClickListener walkListener, View.OnClickListener bikeListener
            , View.OnClickListener driveListener, View.OnClickListener transitListener) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_select_route, null);
        contentView.findViewById(R.id.tvWalk).setOnClickListener(walkListener);
        contentView.findViewById(R.id.tvBike).setOnClickListener(bikeListener);
        contentView.findViewById(R.id.tvDrive).setOnClickListener(driveListener);
        contentView.findViewById(R.id.tvTransit).setOnClickListener(transitListener);
        Dialog dialog = new Dialog(context, R.style.DialogTheme);
        dialog.getWindow().setContentView(contentView);
        dialog.setCancelable(true);
        return dialog;
    }
}
