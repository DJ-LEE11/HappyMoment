package com.uwork.happymoment.fragment;

/**
 * @author 李栋杰
 * @time 2018/3/29  上午11:52
 * @desc ${TODD}
 */
public abstract class BasePageMultiAdapterFragment<T>{} //extends BasePageFragment<T>{
//    protected BaseMultipleAdapter mAdapter;
//
//    @Override
//    public void addList(PageResponseBean<T> pager) {
//        if (mAdapter != null && mRefreshLayout != null) {
//            if (mIsRefresh && !pager.hasPreviousPage) {
//                if (pager.list != null && pager.list.size() > 0) {
//                    mAdapter.setNewData((List<MultiItemEntity>) pager.list);
//                } else {
//                    showEmptyView();
//                }
//            } else {
//                mAdapter.addData((Collection<? extends MultiItemEntity>) pager.list);
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
//            mAdapter.setEmptyView(getActivity(), mRvData, new View.OnClickListener() {
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
//            mAdapter.setErrorView(getActivity(), mRvData, new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    autoRefresh();
//                }
//            });
//        }
//    }
//}
