package com.uwork.happymoment.mvp.my.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BaseTitleActivity;
import com.uwork.happymoment.bean.TabEntity;
import com.uwork.happymoment.mvp.my.fragment.ActivityFinishFragment;
import com.uwork.happymoment.mvp.my.fragment.ActivityOnGoingFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyActivityActivity extends BaseTitleActivity {

    @BindView(R.id.commonTabLayout)
    CommonTabLayout mCommonTabLayout;
    @BindView(R.id.vpActivity)
    ViewPager mVpActivity;

    private ArrayList<Fragment> mFragments;


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
        return R.layout.activity_my_activity;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setTitle("我的活动");
        setBackClick();
        initFragment();
        initTab();
    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(ActivityOnGoingFragment.newInstance());
        mFragments.add(ActivityFinishFragment.newInstance());
    }

    private void initTab() {
        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        String[] mTitles = getResources().getStringArray(R.array.activity_status);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], 0, 0));
        }
        mCommonTabLayout.setTabData(mTabEntities);
        mCommonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mVpActivity.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mVpActivity.setOffscreenPageLimit(1);
        mVpActivity.setAdapter(new ActivityPagerAdapter(getSupportFragmentManager()));
        mVpActivity.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCommonTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class ActivityPagerAdapter extends FragmentStatePagerAdapter {
        public ActivityPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
