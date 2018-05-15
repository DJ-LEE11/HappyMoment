package com.example.circle_base_ui.base.adapter;

import android.view.View;

/**
 * Created by 大灯泡 on 2018/4/10.
 */
public abstract class BaseMultiRecyclerViewHolder<T extends MultiType> extends BaseRecyclerViewHolder<T> {

    public BaseMultiRecyclerViewHolder(View itemView, int viewType) {
        super(itemView, viewType);
    }

}
