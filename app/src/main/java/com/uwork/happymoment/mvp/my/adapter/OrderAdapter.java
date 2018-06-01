package com.uwork.happymoment.mvp.my.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.uwork.happymoment.R;
import com.uwork.happymoment.adapter.BaseMultipleAdapter;
import com.uwork.happymoment.mvp.my.bean.OrderBean;

import java.util.List;

/**
 * Created by jie on 2018/6/1.
 */

public class OrderAdapter extends BaseMultipleAdapter{

    public OrderAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_ITEM1, R.layout.adapter_no_pay_order);
        addItemType(TYPE_ITEM2, R.layout.adapter_had_pay_order);
        addItemType(TYPE_ITEM3, R.layout.adapter_cancel_order);
        addItemType(TYPE_ITEM4, R.layout.adapter_complete_order);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity multiItemEntity) {
        OrderBean orderBean = (OrderBean) multiItemEntity;
//        helper.setText(R.id.tvHotelName,"");
//        ImageLoadMnanger.INSTANCE.loadImage(helper.getView(R.id.ivOrder),"");
//        helper.setText(R.id.tvRoom,"");
//        helper.setText(R.id.tvPrice,"￥");
//        helper.setText(R.id.tvNum,"x");
//        helper.setText(R.id.tvAllPrice,"合计：￥");
        switch (orderBean.getItemType()) {
            case TYPE_ITEM1:
                helper.addOnClickListener(R.id.tvPay);
                break;
            case TYPE_ITEM2:
                break;
            case TYPE_ITEM3:
                helper.addOnClickListener(R.id.tvDelete);
                break;
            case TYPE_ITEM4:
                helper.addOnClickListener(R.id.tvDelete);
                helper.addOnClickListener(R.id.tvEvaluate);
                break;
        }

    }
}
