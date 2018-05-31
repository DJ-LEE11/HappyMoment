package com.uwork.happymoment.mvp.hotel.activity;

import android.os.Bundle;

import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BaseTitleActivity;
import com.uwork.happymoment.mvp.hotel.bean.IntegralBean;
import com.uwork.happymoment.mvp.hotel.contract.IRoomOrderContract;
import com.uwork.happymoment.mvp.hotel.presenter.IRoomOrderPresenter;

import java.util.ArrayList;
import java.util.List;

public class RoomOrderActivity extends BaseTitleActivity implements IRoomOrderContract.View{

    private IRoomOrderPresenter mIRoomOrderPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected List createPresenter(List list) {
        if (list == null){
            list = new ArrayList();
        }
        mIRoomOrderPresenter = new IRoomOrderPresenter(this);
        list.add(mIRoomOrderPresenter);
        return list;
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_room_order;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {

    }

    @Override
    public void addOrderSuccess() {

    }

    @Override
    public void getIntegralSuccess(IntegralBean integralBean) {

    }
}
