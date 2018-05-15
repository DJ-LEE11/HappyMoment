package com.uwork.happymoment.ui.circle.viewholder;

import android.support.annotation.NonNull;
import android.view.View;

import com.example.circle_base_ui.base.adapter.LayoutId;
import com.example.circle_common.common.entity.MomentsInfo;
import com.uwork.happymoment.R;


/**
 * Created by 大灯泡 on 2016/11/3.
 *
 * 衹有文字的vh
 *
 *
 */

@LayoutId(id =  R.layout.moments_only_text)
public class TextOnlyMomentsVH extends CircleBaseViewHolder {

    public TextOnlyMomentsVH(View itemView, int viewType) {
        super(itemView, viewType);
    }

    @Override
    public void onFindView(@NonNull View rootView) {

    }

    @Override
    public void onBindDataToView(@NonNull MomentsInfo data, int position, int viewType) {

    }
}
