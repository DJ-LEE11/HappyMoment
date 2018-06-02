package com.uwork.happymoment.mvp.my.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.uwork.happymoment.R;
import com.uwork.happymoment.adapter.BaseAdapter;
import com.uwork.happymoment.mvp.my.bean.EvaluateBean;
import com.uwork.happymoment.ui.StarBarView;

import java.util.List;

/**
 * Created by jie on 2018/6/1.
 */

public class EvaluateAdapter extends BaseAdapter<EvaluateBean>{

    public EvaluateAdapter(@Nullable List<EvaluateBean> data) {
        super(R.layout.adapter_evaluate, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EvaluateBean item) {
        StarBarView starAccord = helper.getView(R.id.starAccord);
        starAccord.setStarMaxNumber(3);
        starAccord.setStarRating(3);

        StarBarView starAttitude = helper.getView(R.id.starAttitude);
        starAttitude.setStarMaxNumber(5);
        starAttitude.setStarRating(5);

//        helper.setText(R.id.tvTime,"");
//        helper.setText(R.id.tvRoom,"");
//        helper.setText(R.id.tvEvaluate,"");

    }
}
