package com.uwork.happymoment.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.uwork.happymoment.R;

import java.util.List;


/**
 * @author 李栋杰
 * @time 2017/12/13  下午8:37
 * @desc 普通adapter
 */
public abstract class BaseAdapter<T> extends BaseQuickAdapter<T,BaseViewHolder> {

    public BaseAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
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
