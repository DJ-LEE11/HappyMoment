package com.uwork.happymoment.mvp.social.cirle.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.circle_base_library.utils.ToolUtil;
import com.example.circle_common.common.MomentsType;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.uwork.happymoment.R;
import com.uwork.happymoment.manager.UserManager;
import com.uwork.happymoment.mvp.login.bean.UserBean;
import com.uwork.happymoment.mvp.social.cirle.adapter.CircleAdapter;
import com.uwork.happymoment.mvp.social.cirle.bean.MomentContentBean;
import com.uwork.happymoment.mvp.social.cirle.bean.MomentItemBean;
import com.uwork.happymoment.mvp.social.cirle.bean.MomentLikeBean;
import com.uwork.happymoment.mvp.social.cirle.bean.MomentsItemResponseBean;
import com.uwork.happymoment.mvp.social.cirle.contract.ICircleContract;
import com.uwork.happymoment.mvp.social.cirle.presenter.ICirclePresenter;
import com.uwork.happymoment.mvp.social.cirle.viewholder.EmptyMomentsViewHolderMoment;
import com.uwork.happymoment.mvp.social.cirle.viewholder.MultiImageMomentsViewHolderMoment;
import com.uwork.happymoment.mvp.social.cirle.viewholder.TextOnlyMomentsViewHolderMoment;
import com.uwork.happymoment.mvp.social.cirle.viewholder.WebMomentsViewHolderMoment;
import com.uwork.librx.bean.PageResponseBean;
import com.uwork.librx.mvp.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jie on 2018/5/19.
 */

public class CircleFragment extends BaseFragment implements ICircleContract.View<MomentsItemResponseBean> {

    public static final String TAG = CircleFragment.class.getSimpleName();

    private static CircleFragment fragment;

    @BindView(R.id.circleRecyclerView)
    RecyclerView mCircleRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;


    protected ICirclePresenter mICirclePresenter;
    protected CircleAdapter mCircleAdapter;
    protected boolean mIsRefresh;

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
        list.add(mICirclePresenter);
        return list;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_circle, container, false);
        unbinder = ButterKnife.bind(this, view);
        initList();
        return view;
    }

    private void initList() {
        mCircleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mCircleAdapter = new CircleAdapter(getContext(), new ArrayList<>(), mICirclePresenter);
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
            data.add(new MomentItemBean(bean.getId(), bean.getUserId(), bean.getName(),
                    bean.getAvatar(), bean.getCreateTime(), content, likeList));
        }

        if (isRefresh) {
            mCircleAdapter.updateData(data);
        } else {
            mCircleAdapter.addMore(data);

        }

    }

    //点赞
    @Override
    public void giveLikeSuccess(int itemPosition,List<MomentLikeBean> momentLikeBeanList) {
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

    //删除动态
    @Override
    public void deleteMomentSuccess(int itemPosition) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
