package com.uwork.happymoment.mvp.social.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.circle_base_ui.helper.PhotoHelper;
import com.example.circle_base_ui.widget.popup.SelectPhotoMenuPopup;
import com.example.circle_common.common.router.RouterList;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.uwork.happymoment.R;
import com.uwork.happymoment.bean.TabEntity;
import com.uwork.happymoment.mvp.social.chat.fragment.ChatListFragment;
import com.uwork.happymoment.mvp.social.circleTest.activity.test.ActivityLauncher;
import com.uwork.happymoment.mvp.social.circleTest.fragment.CircleListFragment;
import com.uwork.happymoment.mvp.social.cirle.fragment.CircleFragment;
import com.uwork.happymoment.ui.StickyNavLayout;
import com.uwork.librx.mvp.BaseFragment;
import com.uwork.libtoolbar.ToolbarTitle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jie on 2018/5/9.
 */

public class SocialFragment extends BaseFragment {

    public static final String TAG = SocialFragment.class.getSimpleName();

    private static SocialFragment fragment;
    @BindView(R.id.bottomTabLayout)
    CommonTabLayout mBottomTabLayout;
    @BindView(R.id.id_sticky_view_pager)
    ViewPager mStickyViewPager;
    Unbinder unbinder;
    @BindView(R.id.stickyNavLayout)
    StickyNavLayout mStickyNavLayout;


    private ArrayList<Fragment> mFragments;

    public static SocialFragment newInstance() {
        if (null == fragment) {
            fragment = new SocialFragment();
        }
        return fragment;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected List createPresenter(List list) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_social, container, false);
        unbinder = ButterKnife.bind(this, view);
        initTitle(view);
        initFragment();
        initTab();
        initPager();
        initStickyLayout();
        return view;
    }

    private void initTitle(View view) {
        ToolbarTitle toolbarTitle = new ToolbarTitle(getContext(), view.findViewById(R.id.headerBtnLayout));
        toolbarTitle.initTitle("幸福时刻");
        toolbarTitle.initMenuClick(0, "", null, null
                , R.mipmap.ic_send_circle, "", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {//点击发图片
                        sendCirclePhoto();
                    }
                }, new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {//长按发文字
                        sendCircleText();
                        return false;
                    }
                });
    }

    //发图片
    private void sendCirclePhoto() {
        new SelectPhotoMenuPopup(getActivity()).setOnSelectPhotoMenuClickListener(new SelectPhotoMenuPopup.OnSelectPhotoMenuClickListener() {
            @Override
            public void onShootClick() {
                PhotoHelper.fromCamera(getActivity(), false);
            }

            @Override
            public void onAlbumClick() {
                ActivityLauncher.startToPhotoSelectActivity(getActivity(), RouterList.PhotoSelectActivity.requestCode);
            }
        }).showPopupWindow();
    }

    //发文字
    private void sendCircleText() {
        ActivityLauncher.startToPublishActivityWithResult(getActivity(), RouterList.PublishActivity.MODE_TEXT, null, RouterList.PublishActivity.requestCode);
    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(CircleFragment.newInstance());
        mFragments.add(ChatListFragment.newInstance());
        mFragments.add(CircleListFragment.newInstance());
//        mFragments.add(TrackFragment.newInstance());
    }

    private void initTab() {
        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

        String[] mTitles = getResources().getStringArray(R.array.social_tab);
        int[] mIconUnSelectIds = {
                R.mipmap.ic_home_default, R.mipmap.ic_social_default, R.mipmap.ic_my_default};
        int[] mIconSelectIds = {
                R.mipmap.ic_home_select, R.mipmap.ic_social_select, R.mipmap.ic_my_select};

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnSelectIds[i]));
        }
        mBottomTabLayout.setTabData(mTabEntities);
        mBottomTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mStickyViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    private void initPager() {
        mStickyViewPager.setOffscreenPageLimit(2);
        mStickyViewPager.setAdapter(new SocialPagerAdapter(getChildFragmentManager()));
        mStickyViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mBottomTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class SocialPagerAdapter extends FragmentPagerAdapter {
        public SocialPagerAdapter(FragmentManager fm) {
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initStickyLayout() {
        mStickyNavLayout.setNestedScrollingEnabled(true);
        mStickyNavLayout.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //当滑到顶部的时候下面的子fragment中的列表才可以下拉刷新
                if (scrollY == 0){
//                    TrackFragment.newInstance().setRefresh(true);
                    CircleListFragment.newInstance().setRefresh(true);
                }else {
//                    TrackFragment.newInstance().setRefresh(false);
                    CircleListFragment.newInstance().setRefresh(false);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
