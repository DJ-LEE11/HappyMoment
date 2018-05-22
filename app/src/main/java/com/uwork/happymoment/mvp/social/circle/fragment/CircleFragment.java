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
import com.example.circle_base_library.utils.ToolUtil;
import com.example.circle_base_ui.widget.commentwidget.CommentBox;
import com.example.circle_base_ui.widget.commentwidget.CommentWidget;
import com.example.circle_base_ui.widget.commentwidget.IComment;
import com.example.circle_common.common.MomentsType;
import com.kw.rxbus.RxBus;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.uwork.happymoment.R;
import com.uwork.happymoment.event.RefreshCircleEvent;
import com.uwork.happymoment.manager.UserManager;
import com.uwork.happymoment.mvp.login.bean.UserBean;
import com.uwork.happymoment.mvp.social.circle.adapter.CircleAdapter;
import com.uwork.happymoment.mvp.social.circle.bean.MomentCommentBean;
import com.uwork.happymoment.mvp.social.circle.bean.MomentContentBean;
import com.uwork.happymoment.mvp.social.circle.bean.MomentItemBean;
import com.uwork.happymoment.mvp.social.circle.bean.MomentLikeBean;
import com.uwork.happymoment.mvp.social.circle.bean.MomentsItemResponseBean;
import com.uwork.happymoment.mvp.social.circle.contract.ICircleContract;
import com.uwork.happymoment.mvp.social.circle.contract.ICircleHandelContract;
import com.uwork.happymoment.mvp.social.circle.presenter.ICircleHandelPresenter;
import com.uwork.happymoment.mvp.social.circle.presenter.ICirclePresenter;
import com.uwork.happymoment.mvp.social.circle.viewholder.EmptyMomentsViewHolderMoment;
import com.uwork.happymoment.mvp.social.circle.viewholder.MultiImageMomentsViewHolderMoment;
import com.uwork.happymoment.mvp.social.circle.viewholder.TextOnlyMomentsViewHolderMoment;
import com.uwork.happymoment.mvp.social.circle.viewholder.WebMomentsViewHolderMoment;
import com.uwork.happymoment.mvp.social.circle.ui.CircleViewHelper;
import com.uwork.librx.bean.PageResponseBean;
import com.uwork.librx.mvp.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by jie on 2018/5/19.
 */

public class CircleFragment extends BaseFragment implements ICircleContract.View<MomentsItemResponseBean> ,ICircleHandelContract.View{

    public static final String TAG = CircleFragment.class.getSimpleName();

    private static CircleFragment fragment;

    @BindView(R.id.circleRecyclerView)
    RecyclerView mCircleRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.commentBox)
    CommentBox mCommentBox;
    Unbinder unbinder;


    protected ICirclePresenter mICirclePresenter;
    private ICircleHandelPresenter mICircleHandelPresenter;
    protected CircleAdapter mCircleAdapter;
    protected boolean mIsRefresh;
    private CompositeDisposable mDisposables;
    private CircleViewHelper mViewHelper;


    public static CircleFragment newInstance() {
        if (null == fragment) {
            fragment = new CircleFragment();
        }
        return fragment;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected List createPresenter(List list) {
        if (list == null) {
            list = new ArrayList();
        }
        mICirclePresenter = new ICirclePresenter(getContext());
        mICircleHandelPresenter = new ICircleHandelPresenter(getContext());
        list.add(mICirclePresenter);
        list.add(mICircleHandelPresenter);
        return list;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_circle, container, false);
        unbinder = ButterKnife.bind(this, view);
        initList();
        initEvent();
        initKeyboardHeightObserver();
        return view;
    }

    private void initList() {
        if (mViewHelper == null) {
            mViewHelper = new CircleViewHelper(getActivity());
        }
        mCommentBox.setOnCommentSendClickListener(onCommentSendClickListener);
        mCircleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mCircleAdapter = new CircleAdapter(getContext(), new ArrayList<>(), mICircleHandelPresenter);
        mCircleAdapter.addViewHolder(EmptyMomentsViewHolderMoment.class, MomentsType.EMPTY_CONTENT)
                .addViewHolder(MultiImageMomentsViewHolderMoment.class, MomentsType.MULTI_IMAGES)
                .addViewHolder(TextOnlyMomentsViewHolderMoment.class, MomentsType.TEXT_ONLY)
                .addViewHolder(WebMomentsViewHolderMoment.class, MomentsType.WEB);
        mCircleRecyclerView.setAdapter(mCircleAdapter);
        //下拉刷新
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mIsRefresh = true;
                mICirclePresenter.loadFirst();
            }
        });

        //上拉加载
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mIsRefresh = false;
                mICirclePresenter.loadMore();
            }
        });
        autoRefresh();
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

    //=================================================>列表相关操作
    //自动刷新
    protected void autoRefresh() {
        if (mRefreshLayout != null && mICirclePresenter != null) {
            mIsRefresh = true;
            mRefreshLayout.autoRefresh();
            mICirclePresenter.loadFirst();
        }
    }

    //添加数据
    @Override
    public void addList(PageResponseBean<MomentsItemResponseBean> pager) {
        if (mCircleAdapter != null && mRefreshLayout != null) {
            if (mIsRefresh && pager.isFirst()) {
                if (pager.getContent() != null && pager.getContent().size() > 0) {
                    dealData(pager.getContent(), mIsRefresh);
                } else {
                    showEmptyView();
                }
            } else {
                dealData(pager.getContent(), mIsRefresh);
            }
            if (pager.isLast()) {
                mRefreshLayout.setLoadmoreFinished(true);
            } else {
                mRefreshLayout.setLoadmoreFinished(false);
            }
            stopLoading();
        }
    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void stopLoading() {
        if (mRefreshLayout != null) {
            if (mIsRefresh) {
                mRefreshLayout.finishRefresh(300);
            } else {
                mRefreshLayout.finishLoadmore();
            }
        }
    }
    //=================================================>

    public void setRefresh(boolean isRefresh) {
        if (mRefreshLayout != null) {
            mRefreshLayout.setEnableRefresh(isRefresh);
        }
    }

    //处理数据
    private void dealData(List<MomentsItemResponseBean> momentList, boolean isRefresh) {
        List<MomentItemBean> data = new ArrayList<>();

        for (MomentsItemResponseBean bean : momentList) {
            //内容
            MomentContentBean content = new MomentContentBean(bean.getContent(), bean.getPicture());
            //点赞
            List<MomentLikeBean> likeList = null;
            List<MomentsItemResponseBean.MomentsFavourResponseBeansBean> momentsFavourResponseBeans = bean.getMomentsFavourResponseBeans();
            if (momentsFavourResponseBeans != null && momentsFavourResponseBeans.size() > 0) {
                likeList = new ArrayList<>();
                for (MomentsItemResponseBean.MomentsFavourResponseBeansBean likeBean : momentsFavourResponseBeans) {
                    likeList.add(new MomentLikeBean(likeBean.getId(), likeBean.getName(), likeBean.getAvatar()));
                }
            }
            //评论
            List<MomentCommentBean> commentList = null;
            List<MomentsItemResponseBean.MomentsCommentResponseBeansBean> momentsCommentResponseBeans = bean.getMomentsCommentResponseBeans();
            if (momentsCommentResponseBeans != null && momentsCommentResponseBeans.size() > 0) {
                commentList = new ArrayList<>();
                for (MomentsItemResponseBean.MomentsCommentResponseBeansBean commentBean : momentsCommentResponseBeans) {
                    commentList.add(new MomentCommentBean(commentBean.getId(),
                            commentBean.getSendUserId(),
                            commentBean.getSendUserName(),
                            commentBean.getToUserId(),
                            commentBean.getToUserName(),
                            UserManager.getInstance().getUserId(getContext()),
                            commentBean.getContent(),
                            commentBean.getCreateTime()));
                }
            }
            data.add(new MomentItemBean(bean.getId(), bean.getUserId(), bean.getName(),
                    bean.getAvatar(), bean.getCreateTime(), content, likeList, commentList));
        }

        if (isRefresh) {
            mCircleAdapter.updateData(data);
        } else {
            mCircleAdapter.addMore(data);

        }

    }

    //点赞
    @Override
    public void giveLikeSuccess(int itemPosition, List<MomentLikeBean> momentLikeBeanList) {
        List<MomentLikeBean> resultLikeList = new ArrayList<>();
        if (!ToolUtil.isListEmpty(momentLikeBeanList)) {
            resultLikeList.addAll(momentLikeBeanList);
        }
        UserBean user = UserManager.getInstance().getUser(getContext());
        MomentLikeBean bean = new MomentLikeBean(user.getId(), user.getNickName(), user.getAvatar());
        resultLikeList.add(bean);


        MomentItemBean momentsInfo = mCircleAdapter.findData(itemPosition);
        if (momentsInfo != null) {
            momentsInfo.setLikesList(resultLikeList);
            mCircleAdapter.notifyDataSetChanged();
        }
    }

    //显示评论输入框
    @Override
    public void showCommentBox(@Nullable View viewHolderRootView, int itemPos, int messageId, @Nullable CommentWidget commentWidget) {
        if (viewHolderRootView != null) {
            mViewHelper.setCommentAnchorView(viewHolderRootView);
        } else if (commentWidget != null) {
            mViewHelper.setCommentAnchorView(commentWidget);
        }
        mViewHelper.setCommentItemDataPosition(itemPos);
        mCommentBox.toggleCommentBox(messageId + "", commentWidget == null ? null : commentWidget.getData(), false);
    }

    private CommentBox.OnCommentSendClickListener onCommentSendClickListener = new CommentBox.OnCommentSendClickListener() {
        @Override
        public void onCommentSendClick(View v, IComment comment, String commentContent) {
            if (TextUtils.isEmpty(commentContent)) {
                mCommentBox.dismissCommentBox(true);
                return;
            }
            int itemPos = mViewHelper.getCommentItemDataPosition();
            if (itemPos < 0 || itemPos > mCircleAdapter.getItemCount()) return;
            List<MomentCommentBean> commentInfo = mCircleAdapter.findData(itemPos).getCommentList();
            if (mCommentBox.getCommentType() == CommentBox.CommentType.TYPE_CREATE) {//评论
                mICircleHandelPresenter.discussMoment(Integer.valueOf(mCommentBox.getMomentid()), commentContent, itemPos, commentInfo);
            } else {//回复
                MomentCommentBean commentBean = (MomentCommentBean) mCommentBox.getCommentInfo().getData();
                mICircleHandelPresenter.replyMoment(Integer.valueOf(mCommentBox.getMomentid()),
                        commentBean.getSendUserId(),
                        commentBean.getSendUserName(),
                        commentContent,
                        itemPos, commentInfo);
            }
            mCommentBox.clearDraft();
            mCommentBox.dismissCommentBox(true);
        }
    };

    //评论动态成功刷新数据
    @Override
    public void discussMomentSuccess(int itemPosition, String comment, List<MomentCommentBean> momentCommentBeanList) {
        List<MomentCommentBean> commentList = new ArrayList<>();
        if (!ToolUtil.isListEmpty(momentCommentBeanList)) {
            commentList.addAll(momentCommentBeanList);
        }
        UserBean user = UserManager.getInstance().getUser(getContext());
        MomentCommentBean momentCommentBean = new MomentCommentBean(0, user.getId(), user.getNickName(), 0, "", user.getId(), comment, "");
        commentList.add(momentCommentBean);
        MomentItemBean momentItemBean = mCircleAdapter.findData(itemPosition);
        if (momentItemBean != null) {
            momentItemBean.setCommentList(commentList);
            mCircleAdapter.notifyItemChanged(itemPosition);
        }
        showToast("评论成功");
    }

    //回复动态
    @Override
    public void replyMomentSuccess(int itemPosition, Integer toUserId, String toUserName, String comment, List<MomentCommentBean> momentCommentBeanList) {
            List<MomentCommentBean> commentList = new ArrayList<>();
            if (!ToolUtil.isListEmpty(momentCommentBeanList)) {
                commentList.addAll(momentCommentBeanList);
            }
            UserBean user = UserManager.getInstance().getUser(getContext());
            MomentCommentBean momentCommentBean = new MomentCommentBean(0, user.getId(), user.getNickName(), toUserId, toUserName, user.getId(), comment, "");
            commentList.add(momentCommentBean);
            MomentItemBean momentItemBean = mCircleAdapter.findData(itemPosition);
            if (momentItemBean != null) {
                momentItemBean.setCommentList(commentList);
                mCircleAdapter.notifyItemChanged(itemPosition);
            }
            showToast("回复成功");
    }

    //删除动态
    @Override
    public void deleteMomentSuccess(int itemPosition) {
        if (itemPosition < 0) return;
        mCircleAdapter.deleteData(itemPosition);
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

    @Override
    public void onResume() {
        super.onResume();
        AppFileHelper.initStroagePath(getActivity());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mDisposables != null) {
            mDisposables.clear();
        }
        unbinder.unbind();
    }


}
