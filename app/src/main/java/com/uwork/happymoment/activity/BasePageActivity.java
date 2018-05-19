package com.uwork.happymoment.activity;

/**
 * @author 李栋杰
 * @time 2018/3/20  上午11:05
 * @desc 分页
 */
public abstract class BasePageActivity<T>{}// extends BaseTitleActivity implements IBaseListPageContract.View<T> {
//
//    protected RecyclerView mRvData;
//    protected SmartRefreshLayout mRefreshLayout;
//    protected IBaseListPagePresenter mIListPagePresenter;
//    protected BaseAdapter mAdapter;
//    protected boolean mIsRefresh;
//
//
//    @Override
//    protected void initData(Intent intent, Bundle savedInstanceState) {
//        autoRefresh();
//    }
//
//    protected void autoRefresh() {
//        if (mRefreshLayout != null && mIListPagePresenter != null) {
//            mIsRefresh = true;
//            mRefreshLayout.autoRefresh();
//            mIListPagePresenter.loadFirst();
//        }
//    }
//
//    @Override
//    public void addList(PageResponseBean<T> pager) {
//        if (mAdapter != null && mRefreshLayout != null) {
//            if (mIsRefresh && !pager.hasPreviousPage) {
//                if (pager.list != null && pager.list.size() > 0) {
//                    mAdapter.setNewData(pager.list);
//                } else {
//                    showEmptyView();
//                }
//            } else {
//                mAdapter.addData(pager.list);
//                mAdapter.notifyDataSetChanged();
//            }
//            if (!pager.hasNextPage) {
//                mRefreshLayout.setLoadmoreFinished(true);
//            }
//            stopLoading();
//        }
//    }
//
//    @Override
//    public void showEmptyView() {
//        if (mAdapter != null && mRvData != null) {
//            mAdapter.setEmptyView(this, mRvData, new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    autoRefresh();
//                }
//            });
//        }
//    }
//
//    @Override
//    public void showErrorView() {
//        if (mAdapter != null && mRvData != null) {
//            mAdapter.setErrorView(this, mRvData, new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    autoRefresh();
//                }
//            });
//        }
//    }
//
//    @Override
//    public void stopLoading() {
//        if (mRefreshLayout !=null){
//            if (mIsRefresh) {
//                mRefreshLayout.finishRefresh(300);
//            } else {
//                mRefreshLayout.finishLoadmore();
//            }
//        }
//    }
//}
