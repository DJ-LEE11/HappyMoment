package com.uwork.happymoment.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.uwork.happymoment.R;


/**
 * @author 李栋杰
 * @time 2017/8/22  13:45
 * @desc 确定、取消对话框
 */
public class SureCancelDialog {

    //确定对话框
    public static Dialog createTipDialog(Context context, String content, boolean canCancel,
                                         String sureStr, View.OnClickListener sureListener) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_tip, null);
        //内容
        TextView tvContent = contentView.findViewById(R.id.tvContent);
        tvContent.setText(content);
        //确定
        TextView tvSure =  contentView.findViewById(R.id.tvSure);
        tvSure.setText(sureStr);
        tvSure.setOnClickListener(sureListener);
        Dialog dialog = new Dialog(context,R.style.DialogTheme);
        dialog.getWindow().setContentView(contentView);
        dialog.setCancelable(canCancel);
        return dialog;
    }

    //确定对话框
    public static Dialog createSureDialog(Context context, String title, String content, boolean canCancel,
                                          String sureStr, View.OnClickListener sureListener) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_sure, null);
        //标题
        TextView tvTitle = contentView.findViewById(R.id.tvTitle);
        tvTitle.setText(title);
        //内容
        TextView tvContent = contentView.findViewById(R.id.tvContent);
        tvContent.setText(content);
        //确定
        TextView tvSure =  contentView.findViewById(R.id.tvSure);
        tvSure.setText(sureStr);
        tvSure.setOnClickListener(sureListener);
        Dialog dialog = new Dialog(context,R.style.DialogTheme);
        dialog.getWindow().setContentView(contentView);
        dialog.setCancelable(canCancel);
        return dialog;
    }

    //确定取消对话框
    public static Dialog createSureCancelDialog(Context context, String title, String content, boolean canCancel,
                                                String cancelStr, View.OnClickListener cancelListener,
                                                String sureStr, View.OnClickListener sureListener) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_sure_cancel, null);
        //标题
        TextView tvTitle = contentView.findViewById(R.id.tvTitle);
        tvTitle.setText(title);
        //内容
        TextView tvContent = contentView.findViewById(R.id.tvContent);
        tvContent.setText(content);
        //取消
        TextView tvCancel = contentView.findViewById(R.id.tvCancel);
        tvCancel.setText(cancelStr);
        tvCancel.setOnClickListener(cancelListener);
        //确定
        TextView tvSure =  contentView.findViewById(R.id.tvSure);
        tvSure.setText(sureStr);
        tvSure.setOnClickListener(sureListener);
        Dialog dialog = new Dialog(context,R.style.DialogTheme);
        dialog.getWindow().setContentView(contentView);
        dialog.setCancelable(canCancel);
        return dialog;
    }

}
