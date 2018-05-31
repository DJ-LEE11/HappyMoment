package com.uwork.happymoment.mvp.my.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.circle_base_ui.imageloader.ImageLoadMnanger;
import com.kw.rxbus.RxBus;
import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BaseTitleActivity;
import com.uwork.happymoment.event.RefreshUserInfoEvent;
import com.uwork.happymoment.manager.UserManager;
import com.uwork.happymoment.mvp.login.bean.UserBean;
import com.uwork.happymoment.mvp.my.contract.ILogoutContract;
import com.uwork.happymoment.mvp.my.presenter.ILogoutPresenter;
import com.uwork.happymoment.ui.dialog.SureCancelDialog;
import com.uwork.librx.bean.LoginEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class PersonInfoActivity extends BaseTitleActivity implements ILogoutContract.View {

    @BindView(R.id.ivAvatar)
    ImageView mIvAvatar;
    @BindView(R.id.tvName)
    TextView mTvName;
    @BindView(R.id.tvSex)
    TextView mTvSex;
    @BindView(R.id.tvPhone)
    TextView mTvPhone;
    private ILogoutPresenter mILogoutPresenter;
    private Dialog mDialog;
    private CompositeDisposable mDisposables;

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
        refreshInfo();
        initEvent();
    }

    //修改完信息后刷新
    private void initEvent() {
        if (mDisposables == null) {
            mDisposables = new CompositeDisposable();
        }
        mDisposables.add(RxBus.getInstance().register(RefreshUserInfoEvent.class, new Consumer<RefreshUserInfoEvent>() {
            @Override
            public void accept(RefreshUserInfoEvent refreshUserInfoEvent) throws Exception {
                refreshInfo();
            }
        }));
    }

    private void refreshInfo() {
        UserBean user = UserManager.getInstance().getUser(this);
        ImageLoadMnanger.INSTANCE.loadImage(mIvAvatar, user.getAvatar());
        mTvName.setText(user.getNickName());
        mTvPhone.setText(user.getPhone());
        if (user.getSex() == 1){
            mTvSex.setText("男");
        }else if (user.getSex() == 2){
            mTvSex.setText("女");
        }else {
            mTvSex.setText("未设置");
        }

    }

    @OnClick({R.id.llAvatar, R.id.llName, R.id.llSex, R.id.llScanCode, R.id.tvQuit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llAvatar:
                goTo(UpdateAvatarActivity.class);
                break;
            case R.id.llName:
                goTo(UpdateNameActivity.class);
                break;
            case R.id.llSex:
                goTo(UpdateSexActivity.class);
                break;
            case R.id.llScanCode:
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposables != null) {
            mDisposables.clear();
        }
    }
}
