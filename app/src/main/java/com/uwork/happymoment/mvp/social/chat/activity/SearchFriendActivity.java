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
import com.uwork.happymoment.mvp.social.chat.adapter.SearchFriendListAdapter;
import com.uwork.happymoment.mvp.social.chat.bean.SearchFriendBean;
import com.uwork.happymoment.mvp.social.chat.contract.IGetSearchFriendContract;
import com.uwork.happymoment.mvp.social.chat.presenter.IGetSearchFriendPresenter;
import com.uwork.happymoment.ui.DividerItemLineDecoration;
import com.uwork.librx.mvp.BaseActivity;
import com.uwork.libutil.IntentUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.uwork.happymoment.mvp.social.chat.activity.FriendDetailActivity.AVATAR;
import static com.uwork.happymoment.mvp.social.chat.activity.FriendDetailActivity.FRIEND_ID;

public class SearchFriendActivity extends BaseActivity implements IGetSearchFriendContract.View{

    @BindView(R.id.etSearchFriend)
    EditText mEtSearchFriend;
    @BindView(R.id.rvSearchFriend)
    RecyclerView mRvSearchFriend;

    private IGetSearchFriendPresenter mIGetSearchFriendPresenter;

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
        if (list == null){
            list = new ArrayList();
        }
        mIGetSearchFriendPresenter = new IGetSearchFriendPresenter(this);
        list.add(mIGetSearchFriendPresenter);
        return list;
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
                SearchFriendBean data = (SearchFriendBean) adapter.getData().get(position);
                new IntentUtils.Builder(SearchFriendActivity.this)
                        .to(FriendDetailActivity.class)
                        .putExtra(FRIEND_ID, data.getId() + "")
                        .putExtra(AVATAR, data.getAvatar() + "")
                        .build()
                        .start();
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
                String searchText = mEtSearchFriend.getText().toString();
                if (!TextUtils.isEmpty(searchText)){
                    mIGetSearchFriendPresenter.searchFriend(searchText);
                }else {
                    showToast("请输入搜索内容");
                }
                break;
        }
    }

    @Override
    public void showFriend(List<SearchFriendBean> searchFriendBeanList) {
        mSearchFriendListAdapter.setNewData(searchFriendBeanList);
    }

    @Override
    public void empty() {
        mSearchFriendListAdapter.setEmptyView(this,"搜不到当前好友");
    }
}
