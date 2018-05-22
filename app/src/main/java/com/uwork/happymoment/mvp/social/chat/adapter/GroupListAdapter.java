package com.uwork.happymoment.mvp.social.chat.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.circle_base_ui.imageloader.ImageLoadMnanger;
import com.uwork.happymoment.R;
import com.uwork.happymoment.adapter.BaseAdapter;
import com.uwork.happymoment.mvp.social.chat.bean.GroupBean;

import java.util.List;

/**
 * Created by jie on 2018/5/22.
 */

public class GroupListAdapter extends BaseAdapter<GroupBean>{

    public GroupListAdapter(@Nullable List<GroupBean> data) {
        super(R.layout.adapter_group_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupBean item) {
        ImageLoadMnanger.INSTANCE.loadImage(helper.getView(R.id.ivAvatar),item.getAvatar());
        helper.setText(R.id.tvGroupName,item.getName());
    }
}
