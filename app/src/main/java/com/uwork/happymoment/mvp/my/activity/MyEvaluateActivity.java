package com.uwork.happymoment.mvp.my.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BaseTitleActivity;
import com.uwork.happymoment.mvp.my.adapter.EvaluateAdapter;
import com.uwork.happymoment.mvp.my.bean.EvaluateBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyEvaluateActivity extends BaseTitleActivity {

    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private EvaluateAdapter mEvaluateAdapter;

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
        return R.layout.activity_my_evaluate;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setTitle("我的评价");
        setBackClick();
        initList();
    }

    private void initList() {
        mRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mEvaluateAdapter = new EvaluateAdapter(new ArrayList<>());
        mRv.setAdapter(mEvaluateAdapter);

        //下拉刷新
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshData();
            }
        });

        //上拉加载
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                loadMoreData();
            }
        });
        mEvaluateAdapter.setNewData(initData());
    }

    private List<EvaluateBean> mData;

    private List<EvaluateBean> initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mData.add(new EvaluateBean());
        }
        return mData;
    }

    private void refreshData() {
        mEvaluateAdapter.setNewData(initData());
        mRefreshLayout.finishRefresh(1200);
        mRefreshLayout.setLoadmoreFinished(false);
    }

    private void loadMoreData() {
        mRefreshLayout.finishLoadmore(1200);
        mEvaluateAdapter.addData(initData());
        mEvaluateAdapter.notifyDataSetChanged();
        if (mEvaluateAdapter.getItemCount() > 35) {
            showToast("数据加载完毕");
            mRefreshLayout.setLoadmoreFinished(true);
        }
    }
}
