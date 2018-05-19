package com.uwork.happymoment.mvp.social.chat.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
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
import com.uwork.happymoment.manager.IMRongManager;
import com.uwork.happymoment.mvp.social.chat.adapter.AddGroupAdapter;
import com.uwork.happymoment.mvp.social.chat.bean.AddGroupBean;
import com.uwork.happymoment.mvp.social.chat.bean.AddGroupIndexBean;
import com.uwork.happymoment.mvp.social.chat.bean.FriendIndexBean;
import com.uwork.happymoment.mvp.social.chat.contract.IAddGroupContract;
import com.uwork.happymoment.mvp.social.chat.contract.IGetFriendIndexContract;
import com.uwork.happymoment.mvp.social.chat.presenter.IAddGroupPresenter;
import com.uwork.happymoment.mvp.social.chat.presenter.IGetFriendIndexPresenter;
import com.uwork.happymoment.ui.DividerItemLineDecoration;
import com.uwork.happymoment.ui.dialog.InputDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Group;

//创建群聊
public class AddGroupActivity extends BaseTitleActivity implements IGetFriendIndexContract.View,IAddGroupContract.View{

    @BindView(R.id.rvFriend)
    RecyclerView mRvFriend;
    @BindView(R.id.indexBar)
    IndexBar mIndexBar;
    @BindView(R.id.tvSideBarHint)
    TextView mTvSideBarHint;

    private AddGroupAdapter mAddGroupAdapter;
    private SuspensionDecoration mDecoration;
    private IGetFriendIndexPresenter mIGetFriendIndexPresenter;
    private IAddGroupPresenter mIAddGroupPresenter;
    private List<AddGroupIndexBean> mData;
    private List<String> mSelectId;
    private InputDialog mInputBaseDialog;
    private Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected List createPresenter(List list) {
        if (list == null) {
            list = new ArrayList();
        }
        mIGetFriendIndexPresenter = new IGetFriendIndexPresenter(this);
        mIAddGroupPresenter = new IAddGroupPresenter(this);
        list.add(mIGetFriendIndexPresenter);
        list.add(mIAddGroupPresenter);
        return list;
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_add_group;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initTitle();
        initList();
        intiDialog();
    }

    private void initTitle() {
        setTitle("选择联系人");
        setMenuClick(0, "取消", R.color.text_color_01, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }, 0, "完成", R.color.text_color_yellow, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 0;
                if (mData!=null && mData.size()>0){
                    mSelectId = new ArrayList<>();
                    for (AddGroupIndexBean bean: mData){
                        if (bean.isAdd()){
                            i++;
                            mSelectId.add(bean.getId());
                        }
                    }
                }
                if (i>0){
                    showDialog();
                }else {
                    showToast("请选择群聊联系人");
                }
            }
        });
    }

    private void intiDialog() {
        mInputBaseDialog = new InputDialog();
        mInputBaseDialog.onGetInputListener(new InputDialog.onGetInputListener() {
            @Override
            public void onGetInput(String input) {
                if (mDialog !=null){
                    mDialog.dismiss();
                }
                mIAddGroupPresenter.addGroup(input,mSelectId);
            }
        });
    }

    private void showDialog(){
        if (mDialog !=null){
            mDialog.dismiss();
        }
        mDialog = mInputBaseDialog.createInputDialog(this,"请输入群名");
        mDialog.show();
    }

    private void initList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRvFriend.setLayoutManager(layoutManager);
        mAddGroupAdapter = new AddGroupAdapter(new ArrayList<>());
        mRvFriend.setAdapter(mAddGroupAdapter);
        mDecoration = new SuspensionDecoration(this, new ArrayList<>());
        mRvFriend.addItemDecoration(mDecoration);
        //如果add两个，那么按照先后顺序，依次渲染。
        mRvFriend.addItemDecoration(new DividerItemDecoration(this, DividerItemLineDecoration.VERTICAL_LIST));
        //点击事件
        mAddGroupAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mData.get(position).setAdd(!mData.get(position).isAdd());
                mAddGroupAdapter.notifyDataSetChanged();
            }
        });

        //indexbar初始化
        mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                .setNeedRealIndex(false)//设置需要真实的索引
                .setmLayoutManager(layoutManager);//设置RecyclerView的LayoutManager
    }

    @Override
    protected void initData(Intent intent, Bundle savedInstanceState) {
        mIGetFriendIndexPresenter.getFriendIndex();
    }

    @Override
    public void showFriendIndex(List<FriendIndexBean> friendIndexBeanList) {
        if (friendIndexBeanList != null && friendIndexBeanList.size() > 0) {
            mData = new ArrayList<>();
            for (FriendIndexBean friendIndexBean : friendIndexBeanList){
                mData.add(new AddGroupIndexBean(friendIndexBean.getId()+"",friendIndexBean.getTarget(),friendIndexBean.getAvatar()));
            }
            initData(mData);
        } else {
            mAddGroupAdapter.setEmptyView(AddGroupActivity.this);
        }
    }

    private void initData(List<AddGroupIndexBean> addGroupIndexBean) {
        //设置数据
        mAddGroupAdapter.setNewData(mData);
        mIndexBar.setmSourceDatas(mData)
                .invalidate();
        mDecoration.setmDatas(mData);
    }

    @Override
    public void addCreateGroup(AddGroupBean addGroupBean) {
        finish();
        RongIM.setGroupInfoProvider(new RongIM.GroupInfoProvider() {
            @Override
            public Group getGroupInfo(String s) {
                //调用接口获取groupInfo信息。然后刷新 refreshGroupInfoCache(group);
                Group group=new Group(Integer.valueOf(addGroupBean.getId()).toString(),addGroupBean.getName(),  Uri.parse(""));
                RongIM.getInstance().refreshGroupInfoCache(group);
                return group;
            }
        }, true);
        IMRongManager.groupChat(this,addGroupBean.getId()+"",addGroupBean.getName());
    }
}
