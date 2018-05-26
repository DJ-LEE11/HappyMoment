package com.uwork.happymoment.mvp.main.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.uwork.happymoment.R;
import com.uwork.happymoment.adapter.BaseAdapter;
import com.uwork.happymoment.mvp.main.bean.MessageBean;

import java.util.List;

/**
 * Created by jie on 2018/5/26.
 */

public class MessageAdapter extends BaseAdapter<MessageBean>{

    public MessageAdapter(@Nullable List<MessageBean> data) {
        super(R.layout.adapter_message, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean item) {

    }
}
