package com.uwork.happymoment.mvp.social.chat.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.uwork.happymoment.R;
import com.uwork.happymoment.adapter.BaseAdapter;

import java.util.List;

/**
 * Created by jie on 2018/5/17.
 */

public class FriendDetailPhoneAdapter extends BaseAdapter<String>{

    public FriendDetailPhoneAdapter(@Nullable List<String> data) {
        super(R.layout.friend_detail_phone, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tvPhone,item);
    }
}
