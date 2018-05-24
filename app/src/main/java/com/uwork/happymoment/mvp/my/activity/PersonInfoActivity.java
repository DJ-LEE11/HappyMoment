package com.uwork.happymoment.mvp.my.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import com.kw.rxbus.RxBus;
import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BaseTitleActivity;
import com.uwork.happymoment.mvp.my.contract.ILogoutContract;
import com.uwork.happymoment.mvp.my.presenter.ILogoutPresenter;
import com.uwork.happymoment.ui.dialog.SureCancelDialog;
import com.uwork.librx.bean.LoginEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

public class PersonInfoActivity extends BaseTitleActivity implements ILogoutContract.View {

    private ILogoutPresenter mILogoutPresenter;
    private Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected List createPresenter(List list) {
        if (list == null) {
            list = new ArrayList();
        }
        mILogoutPresenter = new ILogoutPresenter(this);
        list.add(mILogoutPresenter);
        return list;
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_person_info;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setTitle("个人信息");
        setBackClick();
    }

    @OnClick({R.id.tvCode, R.id.tvQuit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvCode:
                goTo(MyCodeActivity.class);
                break;
            case R.id.tvQuit:
                showDialog();
                break;
        }
    }

    @Override
    public void logoutSuccess() {
        mDialog.dismiss();
        RxBus.getInstance().send(new LoginEvent(true));
        finish();
    }

    private void showDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        mDialog = SureCancelDialog.createSureCancelDialog(this, "提示",
                "确定退出？", false, "取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog.dismiss();
                    }
                }, "退出", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mILogoutPresenter.logout();
                    }
                });
        mDialog.show();
    }

}
