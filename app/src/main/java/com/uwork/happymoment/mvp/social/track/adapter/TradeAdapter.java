package com.uwork.happymoment.mvp.social.track.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.uwork.happymoment.R;
import com.uwork.happymoment.adapter.BaseAdapter;
import com.uwork.happymoment.mvp.social.track.bean.TradeListBean;

import java.util.List;


/**
 * @author 李栋杰
 * @time 2017/12/27  下午1:59
 * @desc ${TODD}
 */
public class TradeAdapter extends BaseAdapter<TradeListBean> {

    public TradeAdapter(@Nullable List<TradeListBean> data) {
        super(R.layout.adapter_trade, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, TradeListBean messageBean) {
        baseViewHolder.setText(R.id.tv,"第"+messageBean.getIndex());
    }
}
