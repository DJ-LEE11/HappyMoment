package com.uwork.happymoment.mvp.social.circle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BasePageActivity;
import com.uwork.happymoment.mvp.social.circle.adapter.HomeCircleAdapter;
import com.uwork.happymoment.mvp.social.circle.bean.HomeCircleBean;
import com.uwork.happymoment.mvp.social.circle.contract.IMomentListOtherContract;
import com.uwork.happymoment.mvp.social.circle.presenter.IMomentListOtherPresenter;

import java.util.ArrayList;
import java.util.List;

public class OtherHomeActivity extends BasePageActivity<HomeCircleBean> implements IMomentListOtherContract.View<HomeCircleBean>  {

    public static final String USER_ID = "USER_ID";
    public static final String FRIEND_NAME = "NAME";
    private int mUserId;
    private String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected List createPresenter(List list) {
        if (list == null){
            list = new ArrayList();
        }
        mIListPagePresenter = new IMomentListOtherPresenter(this);
        list.add(mIListPagePresenter);
        return list;
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_other_home;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initList();
    }

    private void intiTitle(String title) {
        setTitle(title);
        setTitleTextColor(R.color.white_color);
        setToolbarBackgroundColor(R.color.circle_title);
        setBackClick(R.mipmap.ic_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initList() {
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRvData = findViewById(R.id.rvCircle);

        mRvData.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new HomeCircleAdapter(new ArrayList<>());
        mRvData.setAdapter(mAdapter);
        //下拉刷新
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mIsRefresh = true;
                mIListPagePresenter.loadFirst();
            }
        });

        //上拉加载
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mIsRefresh = false;
                mIListPagePresenter.loadMore();
            }
        });
    }

    @Override
    protected void initData(Intent intent, Bundle savedInstanceState) {
        mUserId = intent.getIntExtra(USER_ID,0);
        mName = intent.getStringExtra(FRIEND_NAME);
        if (mUserId == 0){
            showToast("获取信息失败");
            finish();
        }
        intiTitle(mName);
        autoRefresh();
    }

    @Override
    public Integer getUserId() {
        return mUserId;
    }
}
