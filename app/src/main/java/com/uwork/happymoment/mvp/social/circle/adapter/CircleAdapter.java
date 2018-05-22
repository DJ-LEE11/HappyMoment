package com.uwork.happymoment.mvp.social.circle.adapter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.circle_base_ui.base.adapter.BaseMultiRecyclerViewAdapter;
import com.example.circle_base_ui.base.adapter.BaseRecyclerViewHolder;
import com.uwork.happymoment.mvp.social.circle.bean.MomentItemBean;
import com.uwork.happymoment.mvp.social.circle.presenter.ICircleHandelPresenter;
import com.uwork.happymoment.mvp.social.circle.viewholder.CircleMomentBaseViewHolder;

import java.util.List;



public class CircleAdapter extends BaseMultiRecyclerViewAdapter<CircleAdapter, MomentItemBean> {

    protected ICircleHandelPresenter mICirclePresenter;


    public CircleAdapter(@NonNull Context context, @NonNull List<MomentItemBean> data, ICircleHandelPresenter iCirclePresenter) {
        super(context, data);
        this.mICirclePresenter = iCirclePresenter;
    }

    @Override
    protected void onInitViewHolder(BaseRecyclerViewHolder holder) {
        if (holder instanceof CircleMomentBaseViewHolder) {
            ((CircleMomentBaseViewHolder) holder).setPresenter(mICirclePresenter);
        }
    }
}
