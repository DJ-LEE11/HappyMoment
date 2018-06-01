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
import com.uwork.happymoment.mvp.my.fragment.order.AllOrderFragment;
import com.uwork.happymoment.mvp.my.fragment.order.CancelOrderFragment;
import com.uwork.happymoment.mvp.my.fragment.order.CompleteOrderFragment;
import com.uwork.happymoment.mvp.my.fragment.order.HadPayOrderFragment;
import com.uwork.happymoment.mvp.my.fragment.order.NoPayOrderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyOrderActivity extends BaseTitleActivity {

    @BindView(R.id.commonTabLayout)
    CommonTabLayout mCommonTabLayout;
    @BindView(R.id.vpOrder)
    ViewPager mVpOrder;

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
        return R.layout.activity_my_order;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setTitle("我的订单");
        setBackClick();
        initFragment();
        initTab();
    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(AllOrderFragment.newInstance());
        mFragments.add(NoPayOrderFragment.newInstance());
        mFragments.add(HadPayOrderFragment.newInstance());
        mFragments.add(CancelOrderFragment.newInstance());
        mFragments.add(CompleteOrderFragment.newInstance());
    }

    private void initTab() {
        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        String[] mTitles = getResources().getStringArray(R.array.order_status);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], 0, 0));
        }
        mCommonTabLayout.setTabData(mTabEntities);
        mCommonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mVpOrder.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mVpOrder.setOffscreenPageLimit(4);
        mVpOrder.setAdapter(new OrderPagerAdapter(getSupportFragmentManager()));
        mVpOrder.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

    private class OrderPagerAdapter extends FragmentStatePagerAdapter {
        public OrderPagerAdapter(FragmentManager fm) {
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
