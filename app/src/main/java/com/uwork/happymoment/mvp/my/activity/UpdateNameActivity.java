package com.uwork.happymoment.mvp.my.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.circle_base_library.interfaces.adapter.TextWatcherAdapter;
import com.kw.rxbus.RxBus;
import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BaseTitleActivity;
import com.uwork.happymoment.event.RefreshUserInfoEvent;
import com.uwork.happymoment.manager.IMRongManager;
import com.uwork.happymoment.manager.UserManager;
import com.uwork.happymoment.mvp.login.bean.UserBean;
import com.uwork.happymoment.mvp.my.contract.IUpdateUserInfoContract;
import com.uwork.happymoment.mvp.my.presenter.IUpdateUserInfoPresenter;
import com.uwork.happymoment.ui.CleanEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class UpdateNameActivity extends BaseTitleActivity implements IUpdateUserInfoContract.View {

    @BindView(R.id.etName)
    CleanEditText mEtName;
    @BindView(R.id.tvModify)
    TextView mTvModify;

    private UserBean mUser;
    private String mModify;
    private IUpdateUserInfoPresenter mIUpdateUserInfoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected List createPresenter(List list) {
        if (list == null) {
            list = new ArrayList();
        }
        mIUpdateUserInfoPresenter = new IUpdateUserInfoPresenter(this);
        list.add(mIUpdateUserInfoPresenter);
        return list;
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_update_name;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setTitle("名字");
        setBackClick();
        mUser = UserManager.getInstance().getUser(this);
        mEtName.setText(mUser.getNickName());
        mEtName.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                if (mUser.getNickName().equals(s)) {
                    mTvModify.setEnabled(false);
                } else {
                    mTvModify.setEnabled(true);
                }
            }
        });
    }

    @OnClick(R.id.tvModify)
    public void onViewClicked() {
        mModify = mEtName.getText().toString();
        if (!TextUtils.isEmpty(mModify)) {
            mIUpdateUserInfoPresenter.updateUserInfo(mUser.getAvatar(), mModify, mUser.getSex());
        } else {
            showToast("请输入姓名");
        }
    }

    @Override
    public void updateUserInfoSuccess() {
        mUser.setNickName(mModify);
        UserManager.getInstance().saveUser(this, mUser);
        IMRongManager.updateUserInfo(this, mUser.getId() + "", mUser.getNickName(), mUser.getAvatar());
        RxBus.getInstance().send(new RefreshUserInfoEvent());
        mTvModify.setEnabled(false);
        mTvModify.setEnabled(false);
        showToast("修改成功");
    }
}
