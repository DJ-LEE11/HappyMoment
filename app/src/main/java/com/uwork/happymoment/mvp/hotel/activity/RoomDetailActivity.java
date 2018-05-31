package com.uwork.happymoment.mvp.hotel.activity;

import android.content.Intent;
import android.os.Bundle;

import com.uwork.happymoment.R;
import com.uwork.happymoment.mvp.hotel.bean.RoomDetailBean;
import com.uwork.happymoment.mvp.hotel.contract.IRoomDetailContract;
import com.uwork.happymoment.mvp.hotel.presenter.IRoomDetailPresenter;
import com.uwork.librx.mvp.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class RoomDetailActivity extends BaseActivity implements IRoomDetailContract.View {

    public static final String ROOM_ID = "ROOM_ID";
    private int mRoomId;
    private IRoomDetailPresenter mIRoomDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected List createPresenter(List list) {
        if (list == null) {
            list = new ArrayList();
        }
        mIRoomDetailPresenter = new IRoomDetailPresenter(this);
        list.add(mIRoomDetailPresenter);
        return list;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_room_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData(Intent intent, Bundle savedInstanceState) {
        mRoomId = intent.getIntExtra(ROOM_ID, 0);
        mIRoomDetailPresenter.roomDetail(mRoomId);
    }

    @Override
    public void showRoomDetail(RoomDetailBean roomDetailBean) {

    }
}
