package com.uwork.happymoment.mvp.social.cirle.viewholder;

import android.support.annotation.NonNull;
import android.view.View;

import com.example.circle_base_ui.base.adapter.LayoutId;
import com.uwork.happymoment.R;
import com.uwork.happymoment.mvp.social.cirle.bean.MomentItemBean;


/**
 *
 * 衹有文字的vh
 *
 *
 */

@LayoutId(id =  R.layout.moment_only_text)
public class TextOnlyMomentsViewHolderMoment extends CircleMomentBaseViewHolder {

    public TextOnlyMomentsViewHolderMoment(View itemView, int viewType) {
        super(itemView, viewType);
    }

    @Override
    public void onFindView(@NonNull View rootView) {

    }

    @Override
    public void onBindDataToView(@NonNull MomentItemBean data, int position, int viewType) {

    }
}
