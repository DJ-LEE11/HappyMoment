package com.uwork.happymoment.mvp.my.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.uwork.happymoment.adapter.BaseMultipleAdapter;

import java.io.Serializable;

/**
 * Created by jie on 2018/6/1.
 */

public class OrderBean implements Serializable, MultiItemEntity {

    private int orderStatus;

    public OrderBean(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public int getItemType() {
        switch (orderStatus){
            case 0:
                return BaseMultipleAdapter.TYPE_ITEM1;
            case 1:
                return BaseMultipleAdapter.TYPE_ITEM2;
            case 2:
                return BaseMultipleAdapter.TYPE_ITEM3;
            case 3:
                return BaseMultipleAdapter.TYPE_ITEM4;
        }
        return 0;
    }
}
