package com.example.circle_base_ui.base.adapter;

import android.view.View;

/**
 * Created by 大灯泡 on 2016/11/1.
 */

public interface OnRecyclerViewItemClickListener<T> {
    void onItemClick(View v, int position, T data);
}
