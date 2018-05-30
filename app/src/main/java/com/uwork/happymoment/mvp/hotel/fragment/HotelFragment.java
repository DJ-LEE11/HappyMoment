package com.uwork.happymoment.mvp.hotel.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.flyco.banner.widget.Banner.BaseIndicatorBanner;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.uwork.happymoment.R;
import com.uwork.happymoment.bean.TabEntity;
import com.uwork.happymoment.mvp.hotel.activity.HotelSearchActivity;
import com.uwork.happymoment.mvp.hotel.activity.RadarMapActivity;
import com.uwork.happymoment.mvp.main.bean.BannerBean;
import com.uwork.happymoment.mvp.main.contract.IBannerContract;
import com.uwork.happymoment.mvp.main.presenter.IBannerPresenter;
import com.uwork.happymoment.ui.StickyLayout;
import com.uwork.happymoment.ui.banner.HomeTopBanner;
import com.uwork.librx.mvp.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by jie on 2018/5/9.
 */

public class HotelFragment extends BaseFragment implements IBannerContract.View {

    public static final String TAG = HotelFragment.class.getSimpleName();

    private static HotelFragment fragment;
    @BindView(R.id.homeTopBanner)
    HomeTopBanner mHomeTopBanner;
    Unbinder unbinder;
    @BindView(R.id.id_sticky_header)
    RelativeLayout mIdStickyHeader;
    @BindView(R.id.commonTabLayout)
    CommonTabLayout mCommonTabLayout;
    @BindView(R.id.id_sticky_indicator)
    LinearLayout mIdStickyIndicator;
    @BindView(R.id.id_sticky_view_pager)
    ViewPager mIdStickyViewPager;
    @BindView(R.id.stickyLayout)
    StickyLayout mStickyLayout;

    private IBannerPresenter mIBannerPresenter;
    private ArrayList<Fragment> mFragments;

    public static HotelFragment newInstance() {
        if (null == fragment) {
            fragment = new HotelFragment();
        }
        return fragment;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected List createPresenter(List list) {
        if (list == null) {
            list = new ArrayList();
        }
        mIBannerPresenter = new IBannerPresenter(getContext());
        list.add(mIBannerPresenter);
        return list;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotel, container, false);
        unbinder = ButterKnife.bind(this, view);
        initFragment();
        initTab();
        initData();
        return view;
    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(HotCityFragment.newInstance());
        mFragments.add(PreferenceForYouFragment.newInstance());
        mFragments.add(HotelStyleFragment.newInstance());
    }

    private void initTab() {
        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        String[] mTitles = getResources().getStringArray(R.array.hotel_tab);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], 0, 0));
        }
        mCommonTabLayout.setTabData(mTabEntities);
        mCommonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mIdStickyViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        //每次只加载一个fragment
        mIdStickyViewPager.setOffscreenPageLimit(2);
        mIdStickyViewPager.setAdapter(new HotelPagerAdapter(getChildFragmentManager()));
        mIdStickyViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

    private class HotelPagerAdapter extends FragmentStatePagerAdapter {
        public HotelPagerAdapter(FragmentManager fm) {
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

    private void initData() {
        mIBannerPresenter.getBanner(8);
    }

    @Override
    public void shoBanner(Integer type, List<BannerBean> bannerBeanList) {
        mHomeTopBanner.setIndicatorStyle(BaseIndicatorBanner.STYLE_DRAWABLE_RESOURCE)
                .setIndicatorSelectorRes(R.mipmap.banner_point_un_select, R.mipmap.banner_point_select)
                .setSource(bannerBeanList)
                .startScroll();
    }

    @OnClick({R.id.llSearch, R.id.tvMap})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llSearch:
                goTo(HotelSearchActivity.class);
                break;
            case R.id.tvMap:
                goTo(RadarMapActivity.class);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
