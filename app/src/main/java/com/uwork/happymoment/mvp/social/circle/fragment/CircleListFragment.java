package com.uwork.happymoment.mvp.social.circle.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.circle_base_library.helper.AppFileHelper;
import com.example.circle_base_library.manager.KeyboardControlMnanager;
import com.example.circle_base_library.network.base.OnResponseListener;
import com.example.circle_base_library.utils.ToolUtil;
import com.example.circle_base_ui.widget.commentwidget.CommentBox;
import com.example.circle_base_ui.widget.commentwidget.CommentWidget;
import com.example.circle_base_ui.widget.commentwidget.IComment;
import com.example.circle_common.common.MomentsType;
import com.example.circle_common.common.entity.CommentInfo;
import com.example.circle_common.common.entity.LikesInfo;
import com.example.circle_common.common.entity.MomentsInfo;
import com.example.circle_common.common.request.MomentsRequest;
import com.kw.rxbus.RxBus;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.socks.library.KLog;
import com.uwork.happymoment.R;
import com.uwork.happymoment.event.RefreshCircleEvent;
import com.uwork.happymoment.mvp.social.circle.activity.test.circledemo.CircleViewTestHelper;
import com.uwork.happymoment.mvp.social.circle.mvp.presenter.impl.MomentPresenter;
import com.uwork.happymoment.mvp.social.circle.mvp.view.IMomentView;
import com.uwork.happymoment.ui.circle.adapter.CircleMomentsAdapter;
import com.uwork.happymoment.ui.circle.viewholder.EmptyMomentsVH;
import com.uwork.happymoment.ui.circle.viewholder.MultiImageMomentsVH;
import com.uwork.happymoment.ui.circle.viewholder.TextOnlyMomentsVH;
import com.uwork.happymoment.ui.circle.viewholder.WebMomentsVH;
import com.uwork.librx.mvp.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.exception.BmobException;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by jie on 2018/5/9.
 */

public class CircleListFragment extends BaseFragment  implements IMomentView {

    public static final String TAG = CircleListFragment.class.getSimpleName();

    private static CircleListFragment fragment;
    Unbinder unbinder;
    @BindView(R.id.circleRecyclerView)
    RecyclerView mCircleRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.commentBox)
    CommentBox mCommentBox;

    private CircleMomentsAdapter mAdapter;
    private List<MomentsInfo> mMomentsInfoList;
    //request
    private MomentsRequest mMomentsRequest;
    private MomentPresenter mPresenter;
    private CompositeDisposable mDisposables;


    private CircleViewTestHelper mViewHelper;

    private static final int REQUEST_REFRESH = 0x10;
    private static final int REQUEST_LOADMORE = 0x11;

    public static CircleListFragment newInstance() {
        if (null == fragment) {
            fragment = new CircleListFragment();
        }
        return fragment;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected List createPresenter(List list) {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_circle_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initKeyboardHeightObserver();
        initData();
        initEvent();
        return view;
    }

    private void initView() {
        mMomentsInfoList = new ArrayList<>();
        mMomentsRequest = new MomentsRequest();

        if (mViewHelper == null) {
            mViewHelper = new CircleViewTestHelper(getActivity());
        }
        mPresenter = new MomentPresenter(this);
        mCircleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new CircleMomentsAdapter(getContext(), mMomentsInfoList, mPresenter);
        mAdapter.addViewHolder(EmptyMomentsVH.class, MomentsType.EMPTY_CONTENT)
                .addViewHolder(MultiImageMomentsVH.class, MomentsType.MULTI_IMAGES)
                .addViewHolder(TextOnlyMomentsVH.class, MomentsType.TEXT_ONLY)
                .addViewHolder(WebMomentsVH.class, MomentsType.WEB);
        mCircleRecyclerView.setAdapter(mAdapter);
        //下拉刷新
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshData();
            }
        });

        //上拉加载
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                loadMoreData();
            }
        });
        mRefreshLayout.setEnableLoadmoreWhenContentNotFull(true);

        mCommentBox.setOnCommentSendClickListener(onCommentSendClickListener);
    }


    private void refreshData() {
        mMomentsRequest.setOnResponseListener(momentsRequestCallBack);
        mMomentsRequest.setRequestType(REQUEST_REFRESH);
        mMomentsRequest.setCurPage(0);
        mMomentsRequest.execute();
    }

    private void loadMoreData() {
        mMomentsRequest.setOnResponseListener(momentsRequestCallBack);
        mMomentsRequest.setRequestType(REQUEST_LOADMORE);
        mMomentsRequest.execute();
    }

    private void initKeyboardHeightObserver() {
        //观察键盘弹出与消退
        KeyboardControlMnanager.observerKeyboardVisibleChange(getActivity(), new KeyboardControlMnanager.OnKeyboardStateChangeListener() {
            View anchorView;

            @Override
            public void onKeyboardChange(int keyboardHeight, boolean isVisible) {
                int commentType = mCommentBox.getCommentType();
                if (isVisible) {
                    //定位评论框到view
                    anchorView = mViewHelper.alignCommentBoxToView(mCircleRecyclerView, mCommentBox, commentType);
                } else {
                    //定位到底部
                    mCommentBox.dismissCommentBox(false);
                    mViewHelper.alignCommentBoxToViewWhenDismiss(mCircleRecyclerView, mCommentBox, commentType, anchorView);
                }
            }
        });
    }

    private void initData() {
        mRefreshLayout.autoRefresh();
    }

    private OnResponseListener.SimpleResponseListener<List<MomentsInfo>> momentsRequestCallBack = new OnResponseListener.SimpleResponseListener<List<MomentsInfo>>() {
        @Override
        public void onSuccess(List<MomentsInfo> response, int requestType) {
            switch (requestType) {
                case REQUEST_REFRESH:
                    if (!ToolUtil.isListEmpty(response)) {
                        KLog.i("firstMomentid", "第一条动态ID   >>>   " + response.get(0).getMomentid());
                        mAdapter.updateData(response);
                    }
                    mRefreshLayout.finishRefresh();
                    break;
                case REQUEST_LOADMORE:
                    mAdapter.addMore(response);
                    mRefreshLayout.finishLoadmore();
                    break;
            }
        }

        @Override
        public void onError(BmobException e, int requestType) {
            super.onError(e, requestType);
            mRefreshLayout.finishRefresh();
            mRefreshLayout.finishLoadmore();
        }
    };

    public void setRefresh(boolean isRefresh){
        mRefreshLayout.setEnableRefresh(isRefresh);
    }

    //发完朋友圈后刷新
    private void initEvent() {
        if (mDisposables == null) {
            mDisposables = new CompositeDisposable();
        }
        mDisposables.add(RxBus.getInstance().register(RefreshCircleEvent.class, new Consumer<RefreshCircleEvent>() {
            @Override
            public void accept(RefreshCircleEvent refreshCircleEvent) throws Exception {
                mRefreshLayout.setEnableRefresh(true);
                mRefreshLayout.autoRefresh();
            }
        }));
    }

    @Override
    public void onLikeChange(int itemPos, List<LikesInfo> likeUserList) {
        MomentsInfo momentsInfo = mAdapter.findData(itemPos);
        if (momentsInfo != null) {
            momentsInfo.setLikesList(likeUserList);
            mAdapter.notifyItemChanged(itemPos);
        }
    }

    @Override
    public void onCommentChange(int itemPos, List<CommentInfo> commentInfoList) {
        MomentsInfo momentsInfo = mAdapter.findData(itemPos);
        if (momentsInfo != null) {
            momentsInfo.setCommentList(commentInfoList);
            mAdapter.notifyItemChanged(itemPos);
        }
    }

    @Override
    public void showCommentBox(@Nullable View viewHolderRootView, int itemPos, String momentid, CommentWidget commentWidget) {
        if (viewHolderRootView != null) {
            mViewHelper.setCommentAnchorView(viewHolderRootView);
        } else if (commentWidget != null) {
            mViewHelper.setCommentAnchorView(commentWidget);
        }
        mViewHelper.setCommentItemDataPosition(itemPos);
        mCommentBox.toggleCommentBox(momentid, commentWidget == null ? null : commentWidget.getData(), false);
    }

    @Override
    public void onDeleteMomentsInfo(@NonNull MomentsInfo momentsInfo) {
        int pos = mAdapter.getDatas().indexOf(momentsInfo);
        if (pos < 0) return;
        mAdapter.deleteData(pos);
    }

    private CommentBox.OnCommentSendClickListener onCommentSendClickListener = new CommentBox.OnCommentSendClickListener() {
        @Override
        public void onCommentSendClick(View v, IComment comment, String commentContent) {
            if (TextUtils.isEmpty(commentContent)) {
                mCommentBox.dismissCommentBox(true);
                return;
            }
            int itemPos = mViewHelper.getCommentItemDataPosition();
            if (itemPos < 0 || itemPos > mAdapter.getItemCount()) return;
            List<CommentInfo> commentInfos = mAdapter.findData(itemPos).getCommentList();
            String userid = (comment instanceof CommentInfo) ? ((CommentInfo) comment).getAuthor().getUserid() : null;
            mPresenter.addComment(itemPos, mCommentBox.getMomentid(), userid, commentContent, commentInfos);
            mCommentBox.clearDraft();
            mCommentBox.dismissCommentBox(true);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        AppFileHelper.initStroagePath(getActivity());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
