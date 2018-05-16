package com.uwork.happymoment.mvp.social.track.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.uwork.happymoment.R;
import com.uwork.happymoment.mvp.social.track.adapter.TradeAdapter;
import com.uwork.happymoment.mvp.social.track.bean.TradeListBean;
import com.uwork.librx.mvp.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jie on 2018/5/9.
 */

public class TrackFragment extends BaseFragment {

    public static final String TAG = TrackFragment.class.getSimpleName();

    private static TrackFragment fragment;
    @BindView(R.id.id_sticky_scrollview)
    RecyclerView mRvTrade;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;

    private TradeAdapter mTradeAdapter;
    private int mIndex = 1;
    private List<TradeListBean> mData;

    public static TrackFragment newInstance() {
        if (null == fragment) {
            fragment = new TrackFragment();
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track, container, false);
        unbinder = ButterKnife.bind(this, view);
        initList();
        return view;
    }

    private void initList() {
        mRvTrade.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mTradeAdapter = new TradeAdapter(new ArrayList<>());
        mRvTrade.setAdapter(mTradeAdapter);

        mTradeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showToast("第"+position);
            }
        });

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
        mRefreshLayout.setEnableLoadmoreWhenContentNotFull(true);
        mTradeAdapter.setNewData(initData());
    }

    public void setRefresh(boolean isRefresh){
        mRefreshLayout.setEnableRefresh(isRefresh);
    }

    private List<TradeListBean> initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mData.add(new TradeListBean(i));
        }
        return mData;
    }

    private void refreshData() {
        mIndex = 1;
        mTradeAdapter.setNewData(initData());
        mRefreshLayout.finishRefresh(1200);
        mRefreshLayout.setLoadmoreFinished(false);
    }

    private void loadMoreData() {
        mIndex++;
        mRefreshLayout.finishLoadmore(1200);
        mTradeAdapter.addData(initData());
        mTradeAdapter.notifyDataSetChanged();
        if (mTradeAdapter.getItemCount() > 35) {
            showToast("数据加载完毕");
            mRefreshLayout.setLoadmoreFinished(true);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
