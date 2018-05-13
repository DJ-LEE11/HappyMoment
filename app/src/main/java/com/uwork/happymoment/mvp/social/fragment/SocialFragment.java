package com.uwork.happymoment.mvp.social.fragment;

import android.net.Uri;
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

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.uwork.happymoment.R;
import com.uwork.happymoment.bean.TabEntity;
import com.uwork.happymoment.mvp.social.chat.fragment.ChatListFragment;
import com.uwork.happymoment.mvp.social.circle.fragment.CircleListFragment;
import com.uwork.happymoment.mvp.social.track.fragment.TrackFragment;
import com.uwork.happymoment.ui.StickyNavLayout;
import com.uwork.librx.mvp.BaseFragment;
import com.uwork.libtoolbar.ToolbarTitle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

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
        toolbarTitle.initMenuClick(0, "", null
                , R.mipmap.ic_send_circle, "", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showToast("发朋友圈");
                    }
                });
    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(CircleListFragment.newInstance());
        mFragments.add(ChatListFragment.newInstance());
        mFragments.add(TrackFragment.newInstance());
    }

    //会话列表
    private ConversationListFragment mConversationListFragment = null;
    //会话列表
    private Fragment initConversationList() {
        if (mConversationListFragment == null) {
            ConversationListFragment listFragment = new ConversationListFragment();
            Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationlist")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//群组
                    .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")
                    .appendQueryParameter(Conversation.ConversationType.CUSTOMER_SERVICE.getName(), "false")//客服
                    .build();

            listFragment.setUri(uri);
            return listFragment;
        } else {
            return mConversationListFragment;
        }
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
                    TrackFragment.newInstance().setRefresh(true);
                }else {
                    TrackFragment.newInstance().setRefresh(false);
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
