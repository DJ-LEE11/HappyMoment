package com.uwork.happymoment.mvp.my.fragment.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.uwork.happymoment.R;
import com.uwork.happymoment.mvp.my.activity.EvaluateActivity;
import com.uwork.happymoment.mvp.my.adapter.OrderAdapter;
import com.uwork.happymoment.mvp.my.bean.OrderBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jie on 2018/5/30.
 */

public class AllOrderFragment extends BaseOrderFragment {
    public static final String TAG = AllOrderFragment.class.getSimpleName();

    private static AllOrderFragment fragment;
    @BindView(R.id.rvOrder)
    RecyclerView mRvOrder;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;

    private OrderAdapter mOrderAdapter;

    public static AllOrderFragment newInstance() {
        if (null == fragment) {
            fragment = new AllOrderFragment();
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
        View view = inflater.inflate(R.layout.fragment_all_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        initList();
        return view;
    }

    private void initList() {
        mRvOrder.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mOrderAdapter = new OrderAdapter(new ArrayList<>());
        mRvOrder.setAdapter(mOrderAdapter);

        mOrderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showToast("第"+position);
            }
        });

        mOrderAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.tvPay:
                        showToast("支付");
                        break;
                    case R.id.tvDelete:
                        mOrderAdapter.remove(position);
                        break;
                    case R.id.tvEvaluate:
                        goTo(EvaluateActivity.class);
                        break;
                }
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
        mOrderAdapter.setNewData(initData());
    }

    private List<MultiItemEntity> mData;

    private List<MultiItemEntity> initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mData.add(new OrderBean(new Random().nextInt(4)));
        }
        return mData;
    }

    private void refreshData() {
        mOrderAdapter.setNewData(initData());
        mRefreshLayout.finishRefresh(1200);
        mRefreshLayout.setLoadmoreFinished(false);
    }

    private void loadMoreData() {
        mRefreshLayout.finishLoadmore(1200);
        mOrderAdapter.addData(initData());
        mOrderAdapter.notifyDataSetChanged();
        if (mOrderAdapter.getItemCount() > 35) {
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
