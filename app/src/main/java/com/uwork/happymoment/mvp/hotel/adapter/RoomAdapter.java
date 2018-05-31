package com.uwork.happymoment.mvp.hotel.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.circle_base_ui.imageloader.ImageLoadMnanger;
import com.uwork.happymoment.R;
import com.uwork.happymoment.adapter.BaseAdapter;
import com.uwork.happymoment.mvp.hotel.bean.RoomItemBean;

import java.util.List;

/**
 * Created by jie on 2018/5/31.
 */

public class RoomAdapter extends BaseAdapter<RoomItemBean>{

    public RoomAdapter(@Nullable List<RoomItemBean> data) {
        super(R.layout.adapter_room, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RoomItemBean item) {
        helper.setText(R.id.tvRoomNo,item.getRoomNo());
        helper.setText(R.id.tvRoomPrice,"￥"+item.getRoomPrice()+"/晚");
        helper.setText(R.id.tvFit,"适合"+item.getRoomNo()+"人");
        helper.setText(R.id.tvRoomModel,item.getRoomModel());
        if (item.getImgLink()!=null && item.getImgLink().size()>0){
            ImageLoadMnanger.INSTANCE.loadImage(helper.getView(R.id.ivRoom),item.getImgLink().get(0));
        }
    }
}
