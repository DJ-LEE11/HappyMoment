package com.uwork.happymoment.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.uwork.happymoment.R;
import com.uwork.libutil.ToastUtils;

/**
 * Created by jie on 2018/5/18.
 */

public class InputDialog {

    //对外获取输入框接口
    private onGetInputListener mOnGetInputListener;

    public interface onGetInputListener {
        void onGetInput(String input);
    }

    public void onGetInputListener(onGetInputListener listener) {
        mOnGetInputListener = listener;
    }

    //输入对话框
    public Dialog createInputDialog(Context context, boolean canCancel,boolean isShowTitle,
                                           String title,String sureStr, String cancelStr) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_input, null);
        //标题
        TextView tvTitle = contentView.findViewById(R.id.tvTitle);
        tvTitle.setVisibility(isShowTitle?View.VISIBLE:View.GONE);
        tvTitle.setText(title);
        //输入内容
        EditText etContent =  contentView.findViewById(R.id.etContent);

        //取消
        TextView tvCancel =  contentView.findViewById(R.id.tvCancel);
        tvCancel.setText(cancelStr);
        //确定
        TextView tvSure =  contentView.findViewById(R.id.tvSure);
        tvSure.setText(sureStr);
        Dialog dialog = new Dialog(context,R.style.DialogTheme);
        dialog.getWindow().setContentView(contentView);
        dialog.setCancelable(canCancel);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog!=null){
                    dialog.dismiss();
                }
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etContent.getText().toString()) && mOnGetInputListener != null){
                    mOnGetInputListener.onGetInput(etContent.getText().toString());
                } else {
                    ToastUtils.show(context,"请输入内容");
                }
            }
        });
        return dialog;
    }

    public Dialog createInputDialog(Context context, String title) {
        return createInputDialog(context,true,true,title,"确定","取消");
    }

}
