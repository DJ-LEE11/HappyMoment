package com.uwork.happymoment.mvp.social.chat.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.circle_base_ui.imageloader.ImageLoadMnanger;
import com.uwork.happymoment.R;
import com.uwork.happymoment.adapter.BaseAdapter;
import com.uwork.happymoment.mvp.social.chat.bean.GroupMemberBean;

import java.util.List;

/**
 * Created by jie on 2018/5/23.
 */

public class GroupMemberAdapter extends BaseAdapter<GroupMemberBean>{

    public GroupMemberAdapter(@Nullable List<GroupMemberBean> data) {
        super(R.layout.adapter_group_member, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupMemberBean item) {
        ImageLoadMnanger.INSTANCE.loadImage(helper.getView(R.id.ivAvatar),item.getAvatar());
        String name = TextUtils.isEmpty(item.getRemarksName())?item.getNickName():item.getRemarksName();
        helper.setText(R.id.tvNickName,name);
    }
}
