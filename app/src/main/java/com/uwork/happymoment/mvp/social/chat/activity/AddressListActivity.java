package com.uwork.happymoment.mvp.social.chat.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.libindex.IndexBar.widget.IndexBar;
import com.example.libindex.suspension.SuspensionDecoration;
import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BaseTitleActivity;
import com.uwork.happymoment.mvp.social.chat.adapter.FriendIndexAdapter;
import com.uwork.happymoment.mvp.social.chat.bean.FriendIndexBean;
import com.uwork.happymoment.ui.DividerItemLineDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//通讯录
public class AddressListActivity extends BaseTitleActivity {

    @BindView(R.id.rvFriend)
    RecyclerView mRvFriend;
    @BindView(R.id.indexBar)
    IndexBar mIndexBar;
    @BindView(R.id.tvSideBarHint)
    TextView mTvSideBarHint;

    private FriendIndexAdapter mFriendIndexAdapter;
    private SuspensionDecoration mDecoration;


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
        return R.layout.activity_address_list;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setTitle("通讯录");
        setBackClick();
        initList();
    }

    private void initList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRvFriend.setLayoutManager(layoutManager);
        mFriendIndexAdapter = new FriendIndexAdapter(new ArrayList<>());
        mRvFriend.setAdapter(mFriendIndexAdapter);
        mDecoration = new SuspensionDecoration(this, new ArrayList<>());
        mRvFriend.addItemDecoration(mDecoration);
        //如果add两个，那么按照先后顺序，依次渲染。
        mRvFriend.addItemDecoration(new DividerItemDecoration(this, DividerItemLineDecoration.VERTICAL_LIST));
        //点击事件
        mFriendIndexAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                FriendIndexBean data = (FriendIndexBean) adapter.getData().get(position);
                showToast(data.getNickName());
            }
        });

        //indexbar初始化
        mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(layoutManager);//设置RecyclerView的LayoutManager

        //模拟线上加载数据
        initData(getResources().getStringArray(R.array.friend_index));
    }

    private void initData(String[] stringArray) {
        List<FriendIndexBean> mData = new ArrayList<>();
        for (int i = 0; i < stringArray.length; i++) {
            FriendIndexBean bean = new FriendIndexBean();
            bean.setId(i+1+"");
            bean.setNickName(stringArray[i]);
            mData.add(bean);
        }
        //设置数据
        mFriendIndexAdapter.setNewData(mData);
        mIndexBar.setmSourceDatas(mData)
                .invalidate();
        mDecoration.setmDatas(mData);
    }
}
