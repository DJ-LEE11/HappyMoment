package com.uwork.happymoment.mvp.my.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kw.rxbus.RxBus;
import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BaseTitleActivity;
import com.uwork.happymoment.event.RefreshUserInfoEvent;
import com.uwork.happymoment.manager.UserManager;
import com.uwork.happymoment.mvp.login.bean.UserBean;
import com.uwork.happymoment.mvp.my.contract.IUpdateUserInfoContract;
import com.uwork.happymoment.mvp.my.presenter.IUpdateUserInfoPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class UpdateSexActivity extends BaseTitleActivity implements IUpdateUserInfoContract.View{

    @BindView(R.id.ivMan)
    ImageView mIvMan;
    @BindView(R.id.ivWoman)
    ImageView mIvWoman;
    @BindView(R.id.tvModify)
    TextView mTvModify;

    private UserBean mUser;
    private int mSex;
    private IUpdateUserInfoPresenter mIUpdateUserInfoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected List createPresenter(List list) {
        if (list == null){
            list = new ArrayList();
        }
        mIUpdateUserInfoPresenter = new IUpdateUserInfoPresenter(this);
        list.add(mIUpdateUserInfoPresenter);
        return list;
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_update_sex;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setTitle("性别");
        setBackClick();
        mUser = UserManager.getInstance().getUser(this);
        mSex = mUser.getSex();
        refreshInfo();
    }

    private void refreshInfo() {
        mIvMan.setVisibility(View.GONE);
        mIvWoman.setVisibility(View.GONE);
        if (mSex == 1){
            mIvMan.setVisibility(View.VISIBLE);
        }else if (mSex == 2){
            mIvWoman.setVisibility(View.VISIBLE);
        }else {//默认选择男
            mSex = 1;
            mIvMan.setVisibility(View.VISIBLE);
        }
        if (mSex == mUser.getSex()){
            mTvModify.setEnabled(false);
        }else {
            mTvModify.setEnabled(true);
        }
    }

    @OnClick({R.id.rlMan, R.id.rlWoman, R.id.tvModify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlMan:
                mSex = 1;
                refreshInfo();
                break;
            case R.id.rlWoman:
                mSex = 2;
                refreshInfo();
                break;
            case R.id.tvModify:
                mIUpdateUserInfoPresenter.updateUserInfo(mUser.getAvatar(),mUser.getNickName(),mSex);
                break;
        }
    }

    @Override
    public void updateUserInfoSuccess() {
        mUser.setSex(mSex);
        UserManager.getInstance().saveUser(this,mUser);
        mTvModify.setEnabled(false);
        RxBus.getInstance().send(new RefreshUserInfoEvent());
        showToast("修改成功");
    }
}
