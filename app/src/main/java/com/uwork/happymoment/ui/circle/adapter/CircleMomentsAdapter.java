package com.uwork.happymoment.ui.circle.adapter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.circle_base_ui.base.adapter.BaseMultiRecyclerViewAdapter;
import com.example.circle_base_ui.base.adapter.BaseRecyclerViewHolder;
import com.example.circle_common.common.entity.MomentsInfo;
import com.uwork.happymoment.mvp.social.circleTest.mvp.presenter.impl.MomentPresenter;
import com.uwork.happymoment.ui.circle.viewholder.CircleBaseViewHolder;

import java.util.List;


/**
 * Created by 大灯泡 on 2016/11/1.
 * <p>
 * 朋友圈adapter
 */

public class CircleMomentsAdapter extends BaseMultiRecyclerViewAdapter<CircleMomentsAdapter, MomentsInfo> {

    private MomentPresenter momentPresenter;

    public CircleMomentsAdapter(@NonNull Context context, @NonNull List<MomentsInfo> datas, MomentPresenter presenter) {
        super(context, datas);
        this.momentPresenter = presenter;
    }

    @Override
    protected void onInitViewHolder(BaseRecyclerViewHolder holder) {
        if (holder instanceof CircleBaseViewHolder) {
            ((CircleBaseViewHolder) holder).setPresenter(momentPresenter);
        }
    }
}
