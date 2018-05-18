package com.uwork.happymoment.mvp.social.chat.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.uwork.happymoment.R;
import com.uwork.happymoment.mvp.social.chat.adapter.SearchNewFriendAdapter;
import com.uwork.happymoment.mvp.social.chat.bean.SearchNewFriendBean;
import com.uwork.happymoment.mvp.social.chat.contract.ISearchNewFriendContract;
import com.uwork.happymoment.mvp.social.chat.presenter.ISearchNewFriendPresenter;
import com.uwork.happymoment.ui.DividerItemLineDecoration;
import com.uwork.librx.mvp.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchNewFriendActivity extends BaseActivity implements ISearchNewFriendContract.View{

    @BindView(R.id.etSearchFriend)
    EditText mEtSearchFriend;
    @BindView(R.id.rvSearchFriend)
    RecyclerView mRvSearchFriend;

    private SearchNewFriendAdapter mSearchNewFriendAdapter;
    private ISearchNewFriendPresenter mISearchNewFriendPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search_new_friend;
    }

    @Override
    protected List createPresenter(List list) {
        if (list == null){
            list = new ArrayList();
        }
        mISearchNewFriendPresenter = new ISearchNewFriendPresenter(this);
        list.add(mISearchNewFriendPresenter);
        return list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        mRvSearchFriend.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mSearchNewFriendAdapter = new SearchNewFriendAdapter(new ArrayList<>());
        mRvSearchFriend.setAdapter(mSearchNewFriendAdapter);

        mSearchNewFriendAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showToast("第"+position);
            }
        });
        mRvSearchFriend.addItemDecoration(new DividerItemDecoration(this, DividerItemLineDecoration.VERTICAL_LIST));
    }


    @OnClick({R.id.ivBack, R.id.tvSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvSearch:
                String phone = mEtSearchFriend.getText().toString();
                if (!TextUtils.isEmpty(phone)){
                    mISearchNewFriendPresenter.searchNewFriendForPhone(phone);
                }else {
                    showToast("请输入电话号码");
                }
                break;
        }
    }

    @Override
    public void showNewFriend(SearchNewFriendBean searchNewFriendBean) {
        List<SearchNewFriendBean> data = new ArrayList<>();
        data.add(searchNewFriendBean);
        mSearchNewFriendAdapter.setNewData(data);
    }

    @Override
    public void empty() {
        mSearchNewFriendAdapter.setEmptyView(this,"暂无此用户");
    }
}
