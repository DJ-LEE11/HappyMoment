package com.uwork.happymoment.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.uwork.happymoment.R;

import java.util.List;


/**
 * @author 李栋杰
 * @time 2017/12/23  下午2:57
 * @desc 多种布局
 */
public abstract class BaseMultipleAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    //第一种布局
    public static final int TYPE_ITEM1 = 1;
    //第二种布局
    public static final int TYPE_ITEM2 = 2;
    //第三种布局
    public static final int TYPE_ITEM3 = 3;

    public BaseMultipleAdapter(List<MultiItemEntity> data) {
        super(data);
    }

    //没有数据
    public void setEmptyView(Activity activity , View viewGroup, View.OnClickListener onClickListener){
        View emptyView = activity.getLayoutInflater().inflate(R.layout.layout_empty, (ViewGroup) viewGroup.getParent(), false);
        emptyView.setOnClickListener(onClickListener);
        setEmptyView(emptyView);
    }

    public void setEmptyView(Activity activity){
        View emptyView = activity.getLayoutInflater().inflate(R.layout.layout_empty, getRecyclerView(), false);
        setEmptyView(emptyView);
    }

    public void setEmptyView(Activity activity,String tip){
        View emptyView = activity.getLayoutInflater().inflate(R.layout.layout_empty, getRecyclerView(), false);
        TextView tv = emptyView.findViewById(R.id.tvEmpty);
        tv.setText(tip);
        setEmptyView(emptyView);
    }

    //没有网络、加载错误
    public void setErrorView(Activity activity , View viewGroup, View.OnClickListener onClickListener){
        View errorView = activity.getLayoutInflater().inflate(R.layout.layout_error, (ViewGroup) viewGroup.getParent(), false);
        errorView.setOnClickListener(onClickListener);
        setEmptyView(errorView);
    }

}
