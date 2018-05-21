package com.uwork.happymoment.mvp.social.circle.viewholder;

import android.support.annotation.NonNull;
import android.view.View;

import com.example.circle_base_ui.base.adapter.LayoutId;
import com.uwork.happymoment.R;
import com.uwork.happymoment.mvp.social.circle.bean.MomentItemBean;


/**
 * <p>
 * 空内容的vh
 *
 *
 */

@LayoutId(id = R.layout.moment_empty_content)
public class EmptyMomentsViewHolderMoment extends CircleMomentBaseViewHolder {


    public EmptyMomentsViewHolderMoment(View itemView, int viewType) {
        super(itemView, viewType);
    }

    @Override
    public void onFindView(@NonNull View rootView) {

    }

    @Override
    public void onBindDataToView(@NonNull MomentItemBean data, int position, int viewType) {

    }
}
