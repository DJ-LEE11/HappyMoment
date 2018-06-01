package com.uwork.happymoment.fragment;

import android.view.View;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.uwork.happymoment.adapter.BaseMultipleAdapter;
import com.uwork.librx.bean.PageResponseBean;

import java.util.Collection;
import java.util.List;

/**
 * @author 李栋杰
 * @time 2018/3/29  上午11:52
 * @desc ${TODD}
 */
public abstract class BasePageMultiAdapterFragment<T> extends BasePageFragment<T>{
    protected BaseMultipleAdapter mAdapter;

    @Override
    public void addList(PageResponseBean<T> pager) {
        if (mAdapter != null && mRefreshLayout != null) {
            if (mIsRefresh && !pager.isFirst()) {
                if (pager.getContent() != null && pager.getContent().size() > 0) {
                    mAdapter.setNewData((List<MultiItemEntity>) pager.getContent());
                } else {
                    showEmptyView();
                }
            } else {
                mAdapter.addData((Collection<? extends MultiItemEntity>) pager.getContent());
                mAdapter.notifyDataSetChanged();
            }
            if (!pager.isLast()) {
                mRefreshLayout.setLoadmoreFinished(true);
            }
            stopLoading();
        }
    }

    @Override
    public void showEmptyView() {
        if (mAdapter != null && mRvData != null) {
            mAdapter.setEmptyView(getActivity(), mRvData, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    autoRefresh();
                }
            });
        }
    }

    @Override
    public void showErrorView() {
        if (mAdapter != null && mRvData != null) {
            mAdapter.setErrorView(getActivity(), mRvData, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    autoRefresh();
                }
            });
        }
    }
}
