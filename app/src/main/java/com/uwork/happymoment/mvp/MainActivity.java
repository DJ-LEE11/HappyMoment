package com.uwork.happymoment.mvp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.kw.rxbus.RxBus;
import com.uwork.happymoment.R;
import com.uwork.happymoment.bean.TabEntity;
import com.uwork.happymoment.manager.UserManager;
import com.uwork.happymoment.mvp.hotel.fragment.HotelFragment;
import com.uwork.happymoment.mvp.login.activity.LoginRegisterActivity;
import com.uwork.happymoment.mvp.main.fragment.HomePageFragment;
import com.uwork.happymoment.mvp.my.fragment.MyFragment;
import com.uwork.happymoment.mvp.social.fragment.SocialFragment;
import com.uwork.librx.bean.LoginEvent;
import com.uwork.librx.mvp.BaseActivity;
import com.uwork.libutil.IntentUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bottomTabLayout)
    CommonTabLayout mBottomTabLayout;
    private FragmentManager mFragmentManager;

    private CompositeDisposable mDisposables;


    private int mCurrentBottom = 0;

    @Override
    protected List createPresenter(List list) {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        mFragmentManager = getSupportFragmentManager();
        initBottom();
        initEvent();
    }

    private void initEvent() {
        if (mDisposables == null) {
            mDisposables = new CompositeDisposable();
        }
        //出现权限错误跳出主页，返回到引导页
        mDisposables.add(RxBus.getInstance().register(LoginEvent.class, new Consumer<LoginEvent>() {
            @Override
            public void accept(LoginEvent event) throws Exception {
                new IntentUtils.Builder(MainActivity.this)
                        .to(SplashActivity.class)
                        .putExtra(SplashActivity.CLEAN_TOKEN, true)
                        .finish(event.isFinish())
                        .build()
                        .start();
            }
        }));
    }


    @OnClick(R.id.ivSpeech)
    public void onViewClicked() {
        if (!UserManager.getInstance().isLogin(this)){
            goTo(LoginRegisterActivity.class);
        }else {
            showToast("语音");
        }
    }

    private void initBottom() {
        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

        String[] mTitles = getResources().getStringArray(R.array.bottom_menu);
        int[] mIconUnSelectIds = {
                R.mipmap.ic_home_default, R.mipmap.ic_social_default, 0,
                R.mipmap.ic_hotel_default, R.mipmap.ic_my_default};
        int[] mIconSelectIds = {
                R.mipmap.ic_home_select, R.mipmap.ic_social_select, 0,
                R.mipmap.ic_hotel_select, R.mipmap.ic_my_select};

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnSelectIds[i]));
        }
        mBottomTabLayout.setTabData(mTabEntities);
        mBottomTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
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

    public void goToFragment(int position) {
        switch (position) {
            case 0:
                switchHomePage();
                mCurrentBottom = 0;
                break;
            case 1:
                if (UserManager.getInstance().isLogin(this)){
                    switchSocial();
                    mCurrentBottom = 1;
                }else {
                    goTo(LoginRegisterActivity.class);
                }
                break;
            case 2:

                break;
            case 3:
                if (UserManager.getInstance().isLogin(this)){
                    switchOrder();
                    mCurrentBottom = 3;
                }else {
                    goTo(LoginRegisterActivity.class);
                }
                break;
            case 4:
                if (UserManager.getInstance().isLogin(this)){
                    switchMy();
                    mCurrentBottom = 4;
                }else {
                    goTo(LoginRegisterActivity.class);
                }
                break;
        }
        if (mBottomTabLayout != null) {
            mBottomTabLayout.setCurrentTab(mCurrentBottom);
        }
    }

    //主页
    private void switchHomePage() {
        Fragment fragment = mFragmentManager.findFragmentByTag(HomePageFragment.TAG);
        if (fragment == null) {
            fragment = HomePageFragment.newInstance();
        }
        switchContent(mFragment, fragment, HomePageFragment.TAG);
    }

    //幸福时刻
    private void switchSocial() {
        Fragment fragment = mFragmentManager.findFragmentByTag(SocialFragment.TAG);
        if (fragment == null) {
            fragment = SocialFragment.newInstance();
        }
        switchContent(mFragment, fragment, SocialFragment.TAG);
    }

    //桃园客栈
    private void switchOrder() {
        Fragment fragment = mFragmentManager.findFragmentByTag(HotelFragment.TAG);
        if (fragment == null) {
            fragment = HotelFragment.newInstance();
        }
        switchContent(mFragment, fragment, HotelFragment.TAG);
    }

    //我的
    private void switchMy() {
        Fragment fragment = mFragmentManager.findFragmentByTag(MyFragment.TAG);
        if (fragment == null) {
            fragment = MyFragment.newInstance();
        }
        switchContent(mFragment, fragment, MyFragment.TAG);
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
                transaction.commitAllowingStateLoss();
            } else {
                //显示到Activity中并回复状态
                transaction.show(to).commitAllowingStateLoss();
            }
        }
    }
}
