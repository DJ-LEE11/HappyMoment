package com.uwork.happymoment.mvp.social.chat.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.circle_base_ui.imageloader.ImageLoadMnanger;
import com.uwork.happymoment.R;
import com.uwork.happymoment.adapter.BaseMultipleAdapter;
import com.uwork.happymoment.mvp.social.chat.bean.NewFriendBean;

import java.util.List;

/**
 * Created by jie on 2018/5/18.
 */

public class NewFriendAdapter extends BaseMultipleAdapter {

    public NewFriendAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_ITEM1, R.layout.adapter_new_firend_not_add);//未添加
        addItemType(TYPE_ITEM2, R.layout.adapter_new_firend_add);//已添加
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity multiItemEntity) {
        NewFriendBean newFriendBean = (NewFriendBean) multiItemEntity;
        ImageLoadMnanger.INSTANCE.loadImage(helper.getView(R.id.ivAvatar),newFriendBean.getAvatar());
        switch (newFriendBean.getItemType()) {
            case TYPE_ITEM1:
                helper.setText(R.id.tvNickName,newFriendBean.getNickName());
                helper.addOnClickListener(R.id.tvLook);
                break;
            case TYPE_ITEM2:
                String name = TextUtils.isEmpty(newFriendBean.getRemarksName())?newFriendBean.getNickName():newFriendBean.getRemarksName();
                helper.setText(R.id.tvNickName,name);
                break;
        }
    }
}
