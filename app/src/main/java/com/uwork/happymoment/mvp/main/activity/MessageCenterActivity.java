package com.uwork.happymoment.mvp.main.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BaseTitleActivity;
import com.uwork.happymoment.mvp.main.adapter.MessageAdapter;
import com.uwork.happymoment.mvp.main.bean.MessageBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MessageCenterActivity extends BaseTitleActivity {

    @BindView(R.id.rvMessage)
    RecyclerView mRvMessage;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private MessageAdapter mMessageAdapter;
    private int mIndex = 1;


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
        return R.layout.activity_message_center;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initTitle();
        initList();
    }

    private void initTitle() {
        setTitle("消息中心");
        setLeftClick(R.mipmap.ic_back_black, "返回", R.color.text_color_01, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initList() {
        mRvMessage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mMessageAdapter = new MessageAdapter(new ArrayList<>());
        mRvMessage.setAdapter(mMessageAdapter);

        mMessageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
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
        mMessageAdapter.setNewData(initData());
    }

    private List<MessageBean> mData;

    private List<MessageBean> initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mData.add(new MessageBean());
        }
        return mData;
    }

    private void refreshData() {
        mIndex = 1;
        mMessageAdapter.setNewData(initData());
        mRefreshLayout.finishRefresh(1200);
        mRefreshLayout.setLoadmoreFinished(false);
    }

    private void loadMoreData() {
        mIndex++;
        mRefreshLayout.finishLoadmore(1200);
        mMessageAdapter.addData(initData());
        mMessageAdapter.notifyDataSetChanged();
        if (mMessageAdapter.getItemCount() > 35) {
            showToast("数据加载完毕");
            mRefreshLayout.setLoadmoreFinished(true);
        }
    }
}
