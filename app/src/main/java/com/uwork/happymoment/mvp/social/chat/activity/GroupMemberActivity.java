package com.uwork.happymoment.mvp.social.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BaseTitleActivity;
import com.uwork.happymoment.mvp.social.chat.adapter.GroupMemberAdapter;
import com.uwork.happymoment.mvp.social.chat.bean.GroupMemberBean;
import com.uwork.happymoment.mvp.social.chat.contract.IGetGroupMemberContract;
import com.uwork.happymoment.mvp.social.chat.presenter.IGetGroupMemberPresenter;
import com.uwork.happymoment.ui.DividerItemLineDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GroupMemberActivity extends BaseTitleActivity implements IGetGroupMemberContract.View {

    @BindView(R.id.rvGroupMember)
    RecyclerView mRvGroupMember;
    private IGetGroupMemberPresenter mIGetGroupMemberPresenter;
    private int mGroupId;
    private GroupMemberAdapter mGroupMemberAdapter;
    public static final String GROUP_ID = "GROUP_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected List createPresenter(List list) {
        if (list == null) {
            list = new ArrayList();
        }
        mIGetGroupMemberPresenter = new IGetGroupMemberPresenter(this);
        list.add(mIGetGroupMemberPresenter);
        return list;
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_group_member;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setTitle("群组成员");
        setBackClick();
        initList();
    }

    private void initList() {
        mRvGroupMember.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mGroupMemberAdapter = new GroupMemberAdapter(new ArrayList<>());
        mRvGroupMember.setAdapter(mGroupMemberAdapter);
        mRvGroupMember.addItemDecoration(new DividerItemDecoration(this, DividerItemLineDecoration.VERTICAL_LIST));
        mGroupMemberAdapter.setEmptyView(this);
    }

    @Override
    protected void initData(Intent intent, Bundle savedInstanceState) {
        String id = intent.getStringExtra(GROUP_ID);
        if (!TextUtils.isEmpty(id)) {
            mGroupId = Integer.valueOf(id);
            mIGetGroupMemberPresenter.getGroupMember(mGroupId);
        }
    }

    @Override
    public void showGroupMember(List<GroupMemberBean> groupMemberBeanList) {
        mGroupMemberAdapter.setNewData(groupMemberBeanList);
    }

    @Override
    public void showEmpty() {
        mGroupMemberAdapter.setEmptyView(this);
    }
}
