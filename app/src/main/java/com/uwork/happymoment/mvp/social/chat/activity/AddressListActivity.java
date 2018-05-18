package com.uwork.happymoment.mvp.social.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.libindex.IndexBar.widget.IndexBar;
import com.example.libindex.suspension.SuspensionDecoration;
import com.kw.rxbus.RxBus;
import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BaseTitleActivity;
import com.uwork.happymoment.event.RemarkNameEvent;
import com.uwork.happymoment.mvp.social.chat.adapter.FriendIndexAdapter;
import com.uwork.happymoment.mvp.social.chat.bean.FriendIndexBean;
import com.uwork.happymoment.mvp.social.chat.contract.IGetFriendIndexContract;
import com.uwork.happymoment.mvp.social.chat.presenter.IGetFriendIndexPresenter;
import com.uwork.happymoment.ui.DividerItemLineDecoration;
import com.uwork.libutil.IntentUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

import static com.uwork.happymoment.mvp.social.chat.activity.FriendDetailActivity.AVATAR;
import static com.uwork.happymoment.mvp.social.chat.activity.FriendDetailActivity.FRIEND_ID;

//通讯录
public class AddressListActivity extends BaseTitleActivity implements IGetFriendIndexContract.View {

    @BindView(R.id.rvFriend)
    RecyclerView mRvFriend;
    @BindView(R.id.indexBar)
    IndexBar mIndexBar;
    @BindView(R.id.tvSideBarHint)
    TextView mTvSideBarHint;

    private FriendIndexAdapter mFriendIndexAdapter;
    private LinearLayoutManager mLayoutManager;
    private SuspensionDecoration mDecoration;
    private IGetFriendIndexPresenter mIGetFriendIndexPresenter;
    private CompositeDisposable mDisposables;


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
        list.add(mIGetFriendIndexPresenter);
        return list;
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_address_list;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setTitle("通讯录");
        setBackClick();
        initView();
        initEvent();
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRvFriend.setLayoutManager(mLayoutManager);
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
                new IntentUtils.Builder(AddressListActivity.this)
                        .to(FriendDetailActivity.class)
                        .putExtra(FRIEND_ID, data.getId() + "")
                        .putExtra(AVATAR, data.getAvatar() + "")
                        .build()
                        .start();
            }
        });

        //indexbar初始化
        mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                .setNeedRealIndex(false)//设置需要真实的索引
                .setmLayoutManager(mLayoutManager);//设置RecyclerView的LayoutManager
    }

    //设置完备注后刷新
    private void initEvent() {
        if (mDisposables == null) {
            mDisposables = new CompositeDisposable();
        }
        mDisposables.add(RxBus.getInstance().register(RemarkNameEvent.class, new Consumer<RemarkNameEvent>() {
            @Override
            public void accept(RemarkNameEvent remarkNameEvent) throws Exception {
                mIGetFriendIndexPresenter.getFriendIndex();
            }
        }));
    }

    @Override
    protected void initData(Intent intent, Bundle savedInstanceState) {
        mIGetFriendIndexPresenter.getFriendIndex();
    }

    @Override
    public void showFriendIndex(List<FriendIndexBean> friendIndexBeanList) {
        if (friendIndexBeanList != null && friendIndexBeanList.size() > 0) {
            initList(friendIndexBeanList);
        } else {
            mFriendIndexAdapter.setEmptyView(AddressListActivity.this);
        }
    }

    private void initList(List<FriendIndexBean> friendIndexBeanList) {
        //设置数据
        mFriendIndexAdapter.setNewData(friendIndexBeanList);
        mIndexBar.setmSourceDatas(friendIndexBeanList)
                .invalidate();
        mDecoration.setmDatas(friendIndexBeanList);
    }

    @OnClick(R.id.llSearch)
    public void onViewClicked() {
        goTo(SearchFriendActivity.class);
    }

    @Override
    protected void onDestroy() {
        if (mDisposables != null) {
            mDisposables.clear();
        }
        super.onDestroy();
    }
}
