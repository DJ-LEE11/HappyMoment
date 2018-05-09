package com.uwork.happymoment.mvp.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BaseTitleActivity;
import com.uwork.happymoment.bean.TabEntity;
import com.uwork.happymoment.mvp.login.fragment.LoginFragment;
import com.uwork.happymoment.mvp.login.fragment.RegisterFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LoginRegisterActivity extends BaseTitleActivity {

    @BindView(R.id.commonTabLayout)
    CommonTabLayout mCommonTabLayout;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected List createPresenter(List list) {
        return null;
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_login_register;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        mFragmentManager = getSupportFragmentManager();
        initTab();
    }

    private void initTab() {
        ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
        String[] mTitles = getResources().getStringArray(R.array.login_register_tab);
        for (int i = 0; i < mTitles.length; i++) {
            tabEntities.add(new TabEntity(mTitles[i], 0, 0));
        }
        mCommonTabLayout.setTabData(tabEntities);
        mCommonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                goToFragment(position);
            }

            @Override
            public void onTabReselect(int position) {
                goToFragment(position);
            }
        });
        goToFragment(0);
    }

    private void goToFragment(int position) {
        switch (position) {
            case 0:
                switchLogin();
                setTitle("登录");
                setBackClick();
                break;
            case 1:
                switchRegister();
                setTitle("注册");
                setBackClick();
                break;
        }
        mCommonTabLayout.setCurrentTab(position);
    }


    private void switchLogin() {
        Fragment fragment = mFragmentManager.findFragmentByTag(LoginFragment.TAG);
        if (fragment == null) {
            fragment = LoginFragment.newInstance();
        }
        switchContent(mFragment, fragment, LoginFragment.TAG);
    }

    private void switchRegister() {
        Fragment fragment = mFragmentManager.findFragmentByTag(RegisterFragment.TAG);
        if (fragment == null) {
            fragment = RegisterFragment.newInstance();
        }
        switchContent(mFragment, fragment, RegisterFragment.TAG);
    }

    //切换Fragment
    private Fragment mFragment;

    public void switchContent(Fragment from, Fragment to, String tag) {
        if (mFragment != to) {
            mFragment = to;
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            if (from != null && from.isAdded()) {
                //隐藏当前的fragment
                transaction.hide(from);
            }
            if (!to.isAdded()) {// 先判断是否被add过
                //add下一个到Activity中
                transaction.add(R.id.frameLayout, to, tag);
                transaction.commit();
            } else {
                //显示到Activity中并回复状态
                transaction.show(to).commitAllowingStateLoss();
            }
        }
    }

    public static final int RESET_PASSWORD = 0x001;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESET_PASSWORD:
                    finish();
                    break;
            }
        }
    }
}
