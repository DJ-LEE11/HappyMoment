package com.uwork.happymoment.mvp.social.chat.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.uwork.happymoment.R;
import com.uwork.happymoment.mvp.social.chat.adapter.SearchFriendListAdapter;
import com.uwork.happymoment.mvp.social.chat.bean.SearchFriendBean;
import com.uwork.happymoment.ui.DividerItemLineDecoration;
import com.uwork.librx.mvp.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchFriendActivity extends BaseActivity {

    @BindView(R.id.etSearchFriend)
    EditText mEtSearchFriend;
    @BindView(R.id.rvSearchFriend)
    RecyclerView mRvSearchFriend;

    private SearchFriendListAdapter mSearchFriendListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search_friend;
    }

    @Override
    protected List createPresenter(List list) {
        return null;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        mRvSearchFriend.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mSearchFriendListAdapter = new SearchFriendListAdapter(new ArrayList<>());
        mRvSearchFriend.setAdapter(mSearchFriendListAdapter);

        mSearchFriendListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showToast("第"+position);
            }
        });
        mRvSearchFriend.addItemDecoration(new DividerItemDecoration(this, DividerItemLineDecoration.VERTICAL_LIST));
        mSearchFriendListAdapter.setEmptyView(this,"搜不到当前好友");
    }

    @OnClick({R.id.ivBack, R.id.tvSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvSearch:
                initData();
                break;
        }
    }

    private void initData() {
        List<SearchFriendBean> data = new ArrayList<>();
        data.add(new SearchFriendBean());
        data.add(new SearchFriendBean());
        data.add(new SearchFriendBean());
        mSearchFriendListAdapter.setNewData(data);
    }
}
