package com.uwork.happymoment.mvp.social.circleTest.activity.test.circledemo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;

import com.example.circle_base_library.common.entity.ImageInfo;
import com.example.circle_base_library.helper.AppFileHelper;
import com.example.circle_base_library.manager.KeyboardControlMnanager;
import com.example.circle_base_library.network.base.OnResponseListener;
import com.example.circle_base_library.utils.ToolUtil;
import com.example.circle_base_ui.base.BaseTitleBarActivity;
import com.example.circle_base_ui.helper.PhotoHelper;
import com.example.circle_base_ui.util.UIHelper;
import com.example.circle_base_ui.widget.commentwidget.CommentBox;
import com.example.circle_base_ui.widget.commentwidget.CommentWidget;
import com.example.circle_base_ui.widget.commentwidget.IComment;
import com.example.circle_base_ui.widget.common.TitleBar;
import com.example.circle_base_ui.widget.popup.SelectPhotoMenuPopup;
import com.example.circle_base_ui.widget.pullrecyclerview.CircleRecyclerView;
import com.example.circle_base_ui.widget.pullrecyclerview.interfaces.OnRefreshListener2;
import com.example.circle_common.common.MomentsType;
import com.example.circle_common.common.entity.CommentInfo;
import com.example.circle_common.common.entity.LikesInfo;
import com.example.circle_common.common.entity.MomentsInfo;
import com.example.circle_common.common.request.MomentsRequest;
import com.example.circle_common.common.router.RouterList;
import com.socks.library.KLog;
import com.uwork.happymoment.R;
import com.uwork.happymoment.mvp.social.circle.activity.ActivityLauncher;
import com.uwork.happymoment.mvp.social.circleTest.mvp.presenter.impl.MomentPresenter;
import com.uwork.happymoment.mvp.social.circleTest.mvp.view.IMomentView;
import com.uwork.happymoment.ui.circle.adapter.CircleMomentsAdapter;
import com.uwork.happymoment.ui.circle.viewholder.EmptyMomentsVH;
import com.uwork.happymoment.ui.circle.viewholder.MultiImageMomentsVH;
import com.uwork.happymoment.ui.circle.viewholder.TextOnlyMomentsVH;
import com.uwork.happymoment.ui.circle.viewholder.WebMomentsVH;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by 大灯泡 on 2016/10/26.
 * <p>
 * 朋友圈主界面
 */

public class FriendCircleDemoActivity extends BaseTitleBarActivity implements OnRefreshListener2, IMomentView, CircleRecyclerView.OnPreDispatchTouchListener {

    private static final int REQUEST_REFRESH = 0x10;
    private static final int REQUEST_LOADMORE = 0x11;


    private CircleRecyclerView circleRecyclerView;
    private CommentBox commentBox;
    private CircleMomentsAdapter adapter;
    private List<MomentsInfo> momentsInfoList;
    //request
    private MomentsRequest momentsRequest;
    private MomentPresenter presenter;

    private CircleViewHelper mViewHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_cirecle);
        momentsInfoList = new ArrayList<>();
        momentsRequest = new MomentsRequest();
        initView();
        initKeyboardHeightObserver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppFileHelper.initStroagePath(this);
    }

    @Override
    public void onHandleIntent(Intent intent) {

    }


    private void initView() {
        if (mViewHelper == null) {
            mViewHelper = new CircleViewHelper(this);
        }
        setTitle("朋友圈");
        setTitleMode(TitleBar.MODE_BOTH);
        setTitleRightIcon(R.drawable.ic_camera);
        setTitleLeftText("发现");
        setTitleLeftIcon(R.drawable.back_left);
        presenter = new MomentPresenter(this);

        circleRecyclerView = (CircleRecyclerView) findViewById(R.id.recycler);
        circleRecyclerView.setOnRefreshListener(this);
        circleRecyclerView.setOnPreDispatchTouchListener(this);

        commentBox = (CommentBox) findViewById(R.id.widget_comment);
        commentBox.setOnCommentSendClickListener(onCommentSendClickListener);

        adapter = new CircleMomentsAdapter(this, momentsInfoList, presenter);
        adapter.addViewHolder(EmptyMomentsVH.class, MomentsType.EMPTY_CONTENT)
                .addViewHolder(MultiImageMomentsVH.class, MomentsType.MULTI_IMAGES)
                .addViewHolder(TextOnlyMomentsVH.class, MomentsType.TEXT_ONLY)
                .addViewHolder(WebMomentsVH.class, MomentsType.WEB);
        circleRecyclerView.setAdapter(adapter);
        circleRecyclerView.autoRefresh();

    }

    private void initKeyboardHeightObserver() {
        //观察键盘弹出与消退
        KeyboardControlMnanager.observerKeyboardVisibleChange(this, new KeyboardControlMnanager.OnKeyboardStateChangeListener() {
            View anchorView;

            @Override
            public void onKeyboardChange(int keyboardHeight, boolean isVisible) {
                int commentType = commentBox.getCommentType();
                if (isVisible) {
                    //定位评论框到view
                    anchorView = mViewHelper.alignCommentBoxToView(circleRecyclerView, commentBox, commentType);
                } else {
                    //定位到底部
                    commentBox.dismissCommentBox(false);
                    mViewHelper.alignCommentBoxToViewWhenDismiss(circleRecyclerView, commentBox, commentType, anchorView);
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        momentsRequest.setOnResponseListener(momentsRequestCallBack);
        momentsRequest.setRequestType(REQUEST_REFRESH);
        momentsRequest.setCurPage(0);
        momentsRequest.execute();
    }

    @Override
    public void onLoadMore() {
        momentsRequest.setOnResponseListener(momentsRequestCallBack);
        momentsRequest.setRequestType(REQUEST_LOADMORE);
        momentsRequest.execute();
    }


    //titlebar click
    @Override
    public void onTitleRightClick() {
        new SelectPhotoMenuPopup(this).setOnSelectPhotoMenuClickListener(new SelectPhotoMenuPopup.OnSelectPhotoMenuClickListener() {
            @Override
            public void onShootClick() {
                PhotoHelper.fromCamera(FriendCircleDemoActivity.this, false);
            }

            @Override
            public void onAlbumClick() {
                ActivityLauncher.startToPhotoSelectActivity(getActivity(), RouterList.PhotoSelectActivity.requestCode);
            }
        }).showPopupWindow();
    }

    @Override
    public boolean onTitleRightLongClick() {
        ActivityLauncher.startToPublishActivityWithResult(this, RouterList.PublishActivity.MODE_TEXT, null, RouterList.PublishActivity.requestCode);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PhotoHelper.handleActivityResult(this, requestCode, resultCode, data, new PhotoHelper.PhotoCallback() {
            @Override
            public void onFinish(String filePath) {
                List<ImageInfo> selectedPhotos = new ArrayList<ImageInfo>();
                selectedPhotos.add(new ImageInfo(filePath, null, null, 0, 0));
                ActivityLauncher.startToPublishActivityWithResult(FriendCircleDemoActivity.this,
                        RouterList.PublishActivity.MODE_MULTI,
                        selectedPhotos,
                        RouterList.PublishActivity.requestCode);
            }

            @Override
            public void onError(String msg) {
                UIHelper.ToastMessage(msg);
            }
        });
        if (requestCode == RouterList.PhotoSelectActivity.requestCode && resultCode == RESULT_OK) {
            List<ImageInfo> selectedPhotos = data.getParcelableArrayListExtra(RouterList.PhotoSelectActivity.key_result);
            if (selectedPhotos != null) {
                ActivityLauncher.startToPublishActivityWithResult(this, RouterList.PublishActivity.MODE_MULTI, selectedPhotos, RouterList.PublishActivity.requestCode);
            }
        }

        if (requestCode == RouterList.PublishActivity.requestCode && resultCode == RESULT_OK) {
            circleRecyclerView.autoRefresh();
        }
    }

    //request
    //==============================================
    private OnResponseListener.SimpleResponseListener<List<MomentsInfo>> momentsRequestCallBack = new OnResponseListener.SimpleResponseListener<List<MomentsInfo>>() {
        @Override
        public void onSuccess(List<MomentsInfo> response, int requestType) {
            circleRecyclerView.compelete();
            switch (requestType) {
                case REQUEST_REFRESH:
                    if (!ToolUtil.isListEmpty(response)) {
                        KLog.i("firstMomentid", "第一条动态ID   >>>   " + response.get(0).getMomentid());
                        adapter.updateData(response);
                    }
                    break;
                case REQUEST_LOADMORE:
                    adapter.addMore(response);
                    break;
            }
        }

        @Override
        public void onError(BmobException e, int requestType) {
            super.onError(e, requestType);
            circleRecyclerView.compelete();
        }
    };


    //=============================================================View's method
    @Override
    public void onLikeChange(int itemPos, List<LikesInfo> likeUserList) {
        MomentsInfo momentsInfo = adapter.findData(itemPos);
        if (momentsInfo != null) {
            momentsInfo.setLikesList(likeUserList);
            adapter.notifyItemChanged(itemPos);
        }
    }

    @Override
    public void onCommentChange(int itemPos, List<CommentInfo> commentInfoList) {
        MomentsInfo momentsInfo = adapter.findData(itemPos);
        if (momentsInfo != null) {
            momentsInfo.setCommentList(commentInfoList);
            adapter.notifyItemChanged(itemPos);
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
        commentBox.toggleCommentBox(momentid, commentWidget == null ? null : commentWidget.getData(), false);
    }

    @Override
    public void onDeleteMomentsInfo(@NonNull MomentsInfo momentsInfo) {
        int pos = adapter.getDatas().indexOf(momentsInfo);
        if (pos < 0) return;
        adapter.deleteData(pos);
    }

    @Override
    public boolean onPreTouch(MotionEvent ev) {
        if (commentBox != null && commentBox.isShowing()) {
            commentBox.dismissCommentBox(false);
            return true;
        }
        return false;
    }


    //=============================================================call back
    private CommentBox.OnCommentSendClickListener onCommentSendClickListener = new CommentBox.OnCommentSendClickListener() {
        @Override
        public void onCommentSendClick(View v, IComment comment, String commentContent) {
            if (TextUtils.isEmpty(commentContent)) {
                commentBox.dismissCommentBox(true);
                return;
            }
            int itemPos = mViewHelper.getCommentItemDataPosition();
            if (itemPos < 0 || itemPos > adapter.getItemCount()) return;
            List<CommentInfo> commentInfos = adapter.findData(itemPos).getCommentList();
            String userid = (comment instanceof CommentInfo) ? ((CommentInfo) comment).getAuthor().getUserid() : null;
            presenter.addComment(itemPos, commentBox.getMomentid(), userid, commentContent, commentInfos);
            commentBox.clearDraft();
            commentBox.dismissCommentBox(true);
        }
    };
}
