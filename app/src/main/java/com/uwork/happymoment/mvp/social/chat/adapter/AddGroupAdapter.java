package com.uwork.happymoment.mvp.social.chat.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.uwork.happymoment.R;
import com.uwork.happymoment.adapter.BaseAdapter;
import com.uwork.happymoment.mvp.social.chat.bean.AddGroupIndexBean;

import java.util.List;

/**
 * Created by jie on 2018/5/14.
 */

public class AddGroupAdapter extends BaseAdapter<AddGroupIndexBean>{

    public AddGroupAdapter(@Nullable List<AddGroupIndexBean> data) {
        super(R.layout.adapter_add_group, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddGroupIndexBean item) {
        //        ImageLoaderUtils.display(mContext,helper.getView(R.id.ivAvatar),item.getAvatar());
        helper.setText(R.id.tvNickName,item.getNickName());
        helper.getView(R.id.imgSelect).setSelected(item.isAdd());
        helper.addOnClickListener(R.id.imgSelect);
    }
}
