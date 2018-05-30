package com.uwork.happymoment.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.uwork.happymoment.R;
import com.uwork.happymoment.manager.UserManager;
import com.uwork.happymoment.mvp.login.bean.UserBean;
import com.uwork.libutil.ToastUtils;

/**
 * Created by jie on 2018/5/18.
 */

public class JoinActivityDialog {

    //对外获取输入框接口
    private onGetInputListener mOnGetInputListener;

    public interface onGetInputListener {
        void onGetInput(String name, String phone);
    }

    public void onGetInputListener(onGetInputListener listener) {
        mOnGetInputListener = listener;
    }

    //输入对话框
    public Dialog createInputDialog(Context context, boolean canCancel) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_join_activity, null);


        //输入姓名
        EditText etName = contentView.findViewById(R.id.etName);
        //输入手机
        EditText etPhone = contentView.findViewById(R.id.etPhone);
        //确定
        TextView tvSure = contentView.findViewById(R.id.tvSure);
        Dialog dialog = new Dialog(context, R.style.DialogTheme);
        dialog.getWindow().setContentView(contentView);
        dialog.setCancelable(canCancel);

        UserBean user = UserManager.getInstance().getUser(context);
        etName.setText(user.getNickName());
        etPhone.setText(user.getPhone());

        contentView.findViewById(R.id.ivClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etName.getText().toString()) && !TextUtils.isEmpty(etPhone.getText().toString()) && mOnGetInputListener != null) {
                    mOnGetInputListener.onGetInput(etName.getText().toString(),etPhone.getText().toString());
                } else {
                    ToastUtils.show(context, "请完善信息");
                }
            }
        });
        return dialog;
    }

}
