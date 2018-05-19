package com.uwork.happymoment.mvp.social.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BaseTitleActivity;
import com.uwork.happymoment.mvp.social.chat.adapter.NewFriendAdapter;
import com.uwork.happymoment.mvp.social.chat.bean.NewFriendBean;
import com.uwork.happymoment.mvp.social.chat.bean.NewFriendResponseBean;
import com.uwork.happymoment.mvp.social.chat.contract.IGetNewFriendContract;
import com.uwork.happymoment.mvp.social.chat.contract.IMakeFriendContract;
import com.uwork.happymoment.mvp.social.chat.presenter.IGetNewFriendPresenter;
import com.uwork.happymoment.mvp.social.chat.presenter.IMakeFriendPresenter;
import com.uwork.happymoment.ui.DividerItemLineDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class NewFriendActivity extends BaseTitleActivity implements IGetNewFriendContract.View,IMakeFriendContract.View{

    @BindView(R.id.rvFriend)
    RecyclerView mRvFriend;
    private NewFriendAdapter mNewFriendAdapter;
    private IGetNewFriendPresenter mIGetNewFriendPresenter;
    private IMakeFriendPresenter mIMakeFriendPresenter;
    private List<MultiItemEntity> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected List createPresenter(List list) {
        if (list==null){
            list = new ArrayList();
        }
        mIGetNewFriendPresenter = new IGetNewFriendPresenter(this);
        mIMakeFriendPresenter = new IMakeFriendPresenter(this);
        list.add(mIGetNewFriendPresenter);
        list.add(mIMakeFriendPresenter);
        return list;
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_new_friend;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initTitle();
        initList();
    }

    private void initTitle() {
        setTitle("新的朋友");
        setBackClick();
        setRightClick(0, "添加朋友", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("添加朋友");
            }
        });
    }

    private void initList() {
        mRvFriend.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mNewFriendAdapter = new NewFriendAdapter(new ArrayList<>());
        mRvFriend.setAdapter(mNewFriendAdapter);
        mRvFriend.addItemDecoration(new DividerItemDecoration(this, DividerItemLineDecoration.VERTICAL_LIST));
        mNewFriendAdapter.setEmptyView(this);

        mNewFriendAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tvLook){
                    NewFriendBean newFriendBean = (NewFriendBean) adapter.getData().get(position);
                    mIMakeFriendPresenter.makeFriend(Integer.valueOf(newFriendBean.getId()));
                }
            }
        });
    }

    @Override
    protected void initData(Intent intent, Bundle savedInstanceState) {
        mIGetNewFriendPresenter.getNewFriend();
    }

    @Override
    public void showNewFriend(NewFriendResponseBean newFriendResponseBean) {
        mData = new ArrayList<>();
        List<NewFriendResponseBean.NonFriendListResponsesBean> nonFriendListResponses = newFriendResponseBean.getNonFriendListResponses();
        List<NewFriendResponseBean.FriendPageResponseBean> friendPageResponse = newFriendResponseBean.getFriendPageResponse();
        if (nonFriendListResponses!=null && nonFriendListResponses.size()>0){
            for (NewFriendResponseBean.NonFriendListResponsesBean bean:nonFriendListResponses){
                mData.add(new NewFriendBean(bean.getId()+"",bean.getAvatar(), bean.getNickName()));
            }
        }
        if (friendPageResponse!=null && friendPageResponse.size()>0){
            for (NewFriendResponseBean.FriendPageResponseBean bean:friendPageResponse){
                mData.add(new NewFriendBean(bean.getId()+"",bean.getAvatar(), bean.getNickName(),bean.getRemarksName()));
            }
        }
        mNewFriendAdapter.setNewData(mData);
        dismissLoading();
    }

    @OnClick(R.id.llSearch)
    public void onViewClicked() {
        goTo(SearchNewFriendActivity.class);
    }

    @Override
    public void makeFriendSuccess() {
        showToast("添加成功");
        mIGetNewFriendPresenter.getNewFriend();
    }
}
