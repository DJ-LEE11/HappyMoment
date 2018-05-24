package com.uwork.happymoment.mvp.social.chat.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BaseTitleActivity;
import com.uwork.happymoment.manager.IMRongManager;
import com.uwork.happymoment.mvp.social.chat.adapter.NewFriendAdapter;
import com.uwork.happymoment.mvp.social.chat.bean.NewFriendBean;
import com.uwork.happymoment.mvp.social.chat.bean.NewFriendResponseBean;
import com.uwork.happymoment.mvp.social.chat.contract.IGetNewFriendContract;
import com.uwork.happymoment.mvp.social.chat.contract.IMakeFriendContract;
import com.uwork.happymoment.mvp.social.chat.presenter.IGetNewFriendPresenter;
import com.uwork.happymoment.mvp.social.chat.presenter.IMakeFriendPresenter;
import com.uwork.happymoment.ui.DividerItemLineDecoration;
import com.uwork.happymoment.util.scanCode.ScanActivity;

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
                openScan();
            }
        });
    }

    private static final int PERMISSION_REQUEST = 100;
    private static final int SCAN_REQUEST_CODE = 0x001;

    //打开二维码扫码
    private void openScan() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.VIBRATE)
                        != PackageManager.PERMISSION_GRANTED) {
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            ActivityCompat.requestPermissions(NewFriendActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.VIBRATE}, PERMISSION_REQUEST);
        } else {
            startActivityForResult(new Intent(this, ScanActivity.class), SCAN_REQUEST_CODE);
        }
    }

    public static final String PHONE_NUMBER = "PHONE";
    //扫描二维码后的回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SCAN_REQUEST_CODE:
                    String result = data.getStringExtra("result");
                    if (!TextUtils.isEmpty(result) && result.length() == 11){
                        goTo(SearchNewFriendActivity.class,false,PHONE_NUMBER,result);
                    }else {
                        showToast("请扫描有效二维码");
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String mUserId;
    private String mName;
    private String mAvatar;
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
                    mUserId = newFriendBean.getId();
                    mName = newFriendBean.getNickName();
                    mAvatar = newFriendBean.getAvatar();
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
    }

    @OnClick(R.id.llSearch)
    public void onViewClicked() {
        goTo(SearchNewFriendActivity.class);
    }

    @Override
    public void makeFriendSuccess() {
        showToast("添加成功");
        IMRongManager.updateUserInfo(this,mUserId,mName,mAvatar);
        mIGetNewFriendPresenter.getNewFriend();
    }
}
