package com.uwork.happymoment.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.uwork.happymoment.R;

import java.util.List;


/**
 * @author 李栋杰
 * @time 2017/12/15  下午2:29
 * @desc 多级可展开的adapter
 */
public abstract class BaseExpandableAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    //第一级列表
    public static final int TYPE_LEVEL_0 = 0;
    //第二级列表
    public static final int TYPE_LEVEL_1 = 1;
    //第三级列表
    public static final int TYPE_LEVEL_2 = 2;

    public BaseExpandableAdapter(List<MultiItemEntity> data) {
        super(data);
    }

    //没有数据
    public void setEmptyView(Activity activity , View viewGroup, View.OnClickListener onClickListener){
        View emptyView = activity.getLayoutInflater().inflate(R.layout.layout_empty, (ViewGroup) viewGroup.getParent(), false);
        emptyView.setOnClickListener(onClickListener);
        setEmptyView(emptyView);
    }

    //没有网络、加载错误
    public void setErrorView(Activity activity , View viewGroup, View.OnClickListener onClickListener){
        View errorView = activity.getLayoutInflater().inflate(R.layout.layout_error, (ViewGroup) viewGroup.getParent(), false);
        errorView.setOnClickListener(onClickListener);
        setEmptyView(errorView);
    }
}
