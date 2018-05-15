package com.example.circle_base_ui.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by 大灯泡 on 2016/11/1.
 *
 * 对viewholder的封装
 */

public abstract class BaseRecyclerViewHolder<T> extends RecyclerView.ViewHolder {

    private int viewType;

    public BaseRecyclerViewHolder(View itemView, int viewType) {
        super(itemView);
        this.viewType = viewType;
    }

    public final View findViewById(int resid) {
        if (resid > 0 && itemView != null) {
            return itemView.findViewById(resid);
        }
        return null;
    }

    public abstract void onBindData(T data, int position);

    protected int getViewType() {
        return viewType;
    }

    protected Context getContext(){
        return itemView.getContext();
    }

}
