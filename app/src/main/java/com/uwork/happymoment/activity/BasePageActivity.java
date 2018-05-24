package com.uwork.happymoment.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.uwork.happymoment.adapter.BaseAdapter;
import com.uwork.librx.bean.PageResponseBean;
import com.uwork.librx.mvp.contract.IBaseListPageContract;
import com.uwork.librx.mvp.presenter.IBaseListPagePresenter;

/**
 * @author 李栋杰
 * @time 2018/3/20  上午11:05
 * @desc 分页
 */
public abstract class BasePageActivity<T> extends BaseTitleActivity implements IBaseListPageContract.View<T> {

    protected RecyclerView mRvData;
    protected SmartRefreshLayout mRefreshLayout;
    protected IBaseListPagePresenter mIListPagePresenter;
    protected BaseAdapter mAdapter;
    protected boolean mIsRefresh;


    protected void autoRefresh() {
        if (mRefreshLayout != null && mIListPagePresenter != null) {
            mIsRefresh = true;
            mRefreshLayout.autoRefresh();
            mIListPagePresenter.loadFirst();
        }
    }

    @Override
    public void addList(PageResponseBean<T> pager) {
        if (mAdapter != null && mRefreshLayout != null) {
            if (mIsRefresh && pager.isFirst()) {
                if (pager.getContent() != null && pager.getContent().size() > 0) {
                    mAdapter.setNewData(pager.getContent());
                } else {
                    showEmptyView();
                }
            } else {
                mAdapter.addData(pager.getContent());
                mAdapter.notifyDataSetChanged();
            }
            if (pager.isLast()) {
                mRefreshLayout.setLoadmoreFinished(true);
            }else {
                mRefreshLayout.setLoadmoreFinished(false);
            }
            stopLoading();
        }
    }

    @Override
    public void showEmptyView() {
        if (mAdapter != null && mRvData != null) {
            mAdapter.setEmptyView(this, mRvData, new View.OnClickListener() {
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
            mAdapter.setErrorView(this, mRvData, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    autoRefresh();
                }
            });
        }
    }

    @Override
    public void stopLoading() {
        if (mRefreshLayout !=null){
            if (mIsRefresh) {
                mRefreshLayout.finishRefresh(300);
            } else {
                mRefreshLayout.finishLoadmore();
            }
        }
    }
}
