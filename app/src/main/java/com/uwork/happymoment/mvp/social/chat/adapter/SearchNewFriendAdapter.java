package com.uwork.happymoment.mvp.social.chat.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.circle_base_ui.imageloader.ImageLoadMnanger;
import com.uwork.happymoment.R;
import com.uwork.happymoment.adapter.BaseAdapter;
import com.uwork.happymoment.mvp.social.chat.bean.SearchNewFriendBean;

import java.util.List;

/**
 * Created by jie on 2018/5/18.
 */

public class SearchNewFriendAdapter extends BaseAdapter<SearchNewFriendBean> {

    public SearchNewFriendAdapter(@Nullable List<SearchNewFriendBean> data) {
        super(R.layout.adapter_search_new_friend, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchNewFriendBean bean) {
        ImageLoadMnanger.INSTANCE.loadImage(helper.getView(R.id.ivAvatar), bean.getAvatar());
        helper.setText(R.id.tvNickName, bean.getNickName());
    }
}
