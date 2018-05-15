package com.uwork.happymoment.ui.circle.viewholder;

import android.support.annotation.NonNull;
import android.view.View;

import com.example.circle_base_ui.base.adapter.LayoutId;
import com.example.circle_common.common.entity.MomentsInfo;
import com.uwork.happymoment.R;


/**
 * Created by 大灯泡 on 2016/11/3.
 * <p>
 * 空内容的vh
 *
 *
 */

@LayoutId(id = R.layout.moments_empty_content)
public class EmptyMomentsVH extends CircleBaseViewHolder {


    public EmptyMomentsVH(View itemView, int viewType) {
        super(itemView, viewType);
    }

    @Override
    public void onFindView(@NonNull View rootView) {

    }

    @Override
    public void onBindDataToView(@NonNull MomentsInfo data, int position, int viewType) {

    }
}
