package com.uwork.happymoment.mvp.social.chat.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.uwork.happymoment.R;
import com.uwork.happymoment.adapter.BaseAdapter;
import com.uwork.happymoment.mvp.social.chat.bean.SearchFriendBean;

import java.util.List;

/**
 * Created by jie on 2018/5/18.
 */

public class SearchFriendListAdapter extends BaseAdapter<SearchFriendBean>{

    public SearchFriendListAdapter(@Nullable List<SearchFriendBean> data) {
        super(R.layout.adapter_search_firend, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchFriendBean item) {
//        ImageLoadMnanger.INSTANCE.loadImage(helper.getView(R.id.ivAvatar),item.getAvatar());
//        String name = TextUtils.isEmpty(item.getRemarksName())?item.getNickName():item.getRemarksName();
//        helper.setText(R.id.tvNickName,name);
        helper.setText(R.id.tvNickName,"测试");
    }
}
