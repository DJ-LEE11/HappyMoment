package com.uwork.happymoment.mvp.hotel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BaseTitleActivity;
import com.uwork.happymoment.mvp.hotel.adapter.RoomAdapter;
import com.uwork.happymoment.mvp.hotel.bean.RoomItemBean;
import com.uwork.happymoment.mvp.hotel.contract.IGetRoomListContract;
import com.uwork.happymoment.mvp.hotel.presenter.IGetRoomListPresenter;
import com.uwork.happymoment.ui.banner.HomeTopBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.uwork.happymoment.mvp.hotel.activity.RoomDetailActivity.ROOM_ID;

public class RoomListActivity extends BaseTitleActivity implements IGetRoomListContract.View {

    public static final String HOTEL_ID = "HOTEL_ID";
    public static final String HOTEL_NAME = "HOTEL_NAME";
    @BindView(R.id.banner)
    HomeTopBanner mBanner;
    @BindView(R.id.rvRoom)
    RecyclerView mRvRoom;

    private int mHotelId;
    private String mHotelName;
    private IGetRoomListPresenter mIGetRoomListPresenter;
    private RoomAdapter mRoomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected List createPresenter(List list) {
        if (list == null) {
            list = new ArrayList();
        }
        mIGetRoomListPresenter = new IGetRoomListPresenter(this);
        list.add(mIGetRoomListPresenter);
        return list;
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_room_list;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initList();
    }

    private void initList() {
        mRvRoom.setNestedScrollingEnabled(false);
        mRvRoom.setLayoutManager(new LinearLayoutManager(this));
        mRoomAdapter = new RoomAdapter(new ArrayList<>());
        mRvRoom.setAdapter(mRoomAdapter);
        mRoomAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                RoomItemBean roomItemBean = (RoomItemBean) adapter.getData().get(position);
                goTo(RoomDetailActivity.class,false,ROOM_ID,roomItemBean.getId());
            }
        });
    }

    @Override
    protected void initData(Intent intent, Bundle savedInstanceState) {
        mHotelId = intent.getIntExtra(HOTEL_ID, 0);
        mHotelName = intent.getStringExtra(HOTEL_NAME);
        setTitle(mHotelName);
        setBackClick();
        mIGetRoomListPresenter.getRoomList(mHotelId);
    }

    @Override
    public void showRoomList(List<RoomItemBean> roomItemBeanList) {
        mRoomAdapter.setNewData(roomItemBeanList);
    }

    @Override
    public void showEmpty() {
        mRoomAdapter.setEmptyView(this);
    }
}
