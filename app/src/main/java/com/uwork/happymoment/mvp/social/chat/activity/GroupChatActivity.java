package com.uwork.happymoment.mvp.social.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BaseTitleActivity;
import com.uwork.happymoment.manager.IMRongManager;
import com.uwork.happymoment.mvp.social.chat.adapter.GroupListAdapter;
import com.uwork.happymoment.mvp.social.chat.bean.GroupBean;
import com.uwork.happymoment.mvp.social.chat.contract.IGetGroupListContract;
import com.uwork.happymoment.mvp.social.chat.presenter.IGetGroupListPresenter;
import com.uwork.happymoment.ui.DividerItemLineDecoration;
import com.uwork.libutil.IntentUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

//群聊
public class GroupChatActivity extends BaseTitleActivity implements IGetGroupListContract.View{

    @BindView(R.id.rvGroup)
    RecyclerView mRvGroup;

    private GroupListAdapter mGroupListAdapter;
    private IGetGroupListPresenter mIGetGroupListPresenter;
    private static final int REFRESH_GROUP = 0x001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected List createPresenter(List list) {
        if (list == null){
            list = new ArrayList();
        }
        mIGetGroupListPresenter = new IGetGroupListPresenter(this);
        list.add(mIGetGroupListPresenter);
        return list;
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_group_chat;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setTitle("群聊");
        setBackClick();
        initList();
    }

    @OnClick(R.id.llAddGroup)
    public void onViewClicked() {
        new IntentUtils.Builder(this)
                .to(AddGroupActivity.class)
                .build(REFRESH_GROUP)
                .start();
    }

    private void initList() {
        mRvGroup.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mGroupListAdapter = new GroupListAdapter(new ArrayList<>());
        mRvGroup.setAdapter(mGroupListAdapter);
        mRvGroup.addItemDecoration(new DividerItemDecoration(this, DividerItemLineDecoration.VERTICAL_LIST));
        mGroupListAdapter.setEmptyView(this);

        mGroupListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GroupBean groupBean = (GroupBean) adapter.getData().get(position);
                IMRongManager.groupChat(GroupChatActivity.this, groupBean.getId() + "", groupBean.getName());
            }
        });
    }

    @Override
    protected void initData(Intent intent, Bundle savedInstanceState) {
        mIGetGroupListPresenter.getGroupList();
    }

    @Override
    public void showGroupList(List<GroupBean> groupBeans) {
        mGroupListAdapter.setNewData(groupBeans);
    }

    @Override
    public void showEmpty() {
        mGroupListAdapter.setEmptyView(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REFRESH_GROUP){
            mIGetGroupListPresenter.getGroupList();
        }
    }
}
