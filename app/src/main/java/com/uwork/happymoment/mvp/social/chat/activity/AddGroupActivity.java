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
import com.uwork.happymoment.mvp.social.chat.adapter.AddGroupAdapter;
import com.uwork.happymoment.mvp.social.chat.bean.AddGroupIndexBean;
import com.uwork.happymoment.ui.DividerItemLineDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//创建群聊
public class AddGroupActivity extends BaseTitleActivity {

    @BindView(R.id.rvFriend)
    RecyclerView mRvFriend;
    @BindView(R.id.indexBar)
    IndexBar mIndexBar;
    @BindView(R.id.tvSideBarHint)
    TextView mTvSideBarHint;

    private AddGroupAdapter mAddGroupAdapter;
    private SuspensionDecoration mDecoration;
    private List<AddGroupIndexBean> mData;


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
        return R.layout.activity_add_group;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initTitle();
        initList();
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
                    for (AddGroupIndexBean bean: mData){
                        if (bean.isAdd()){
                            i++;
                        }
                    }
                }
                showToast("一共选了"+i);
            }
        });
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
        mAddGroupAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.imgSelect){
                    mData.get(position).setAdd(!mData.get(position).isAdd());
                    mAddGroupAdapter.notifyDataSetChanged();
                }
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
        mData = new ArrayList<>();
        for (int i = 0; i < stringArray.length; i++) {
            AddGroupIndexBean bean = new AddGroupIndexBean();
            bean.setId(i+1+"");
            bean.setNickName(stringArray[i]);
            mData.add(bean);
        }
        //设置数据
        mAddGroupAdapter.setNewData(mData);
        mIndexBar.setmSourceDatas(mData)
                .invalidate();
        mDecoration.setmDatas(mData);
    }
}
