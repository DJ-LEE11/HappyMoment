package com.uwork.happymoment.mvp.social.cirle.viewholder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.circle_base_library.utils.StringUtil;
import com.example.circle_base_ui.base.adapter.BaseMultiRecyclerViewHolder;
import com.example.circle_base_ui.imageloader.ImageLoadMnanger;
import com.example.circle_base_ui.util.UIHelper;
import com.example.circle_base_ui.util.ViewUtil;
import com.example.circle_base_ui.widget.commentwidget.CommentContentsLayout;
import com.example.circle_base_ui.widget.commentwidget.IComment;
import com.example.circle_base_ui.widget.common.ClickShowMoreLayout;
import com.example.circle_common.common.entity.CommentInfo;
import com.socks.library.KLog;
import com.uwork.happymoment.R;
import com.uwork.happymoment.mvp.social.cirle.bean.MomentItemBean;
import com.uwork.happymoment.mvp.social.cirle.presenter.ICirclePresenter;


/**
 * <p>
 * 朋友圈基本item
 */
public abstract class CircleMomentBaseViewHolder extends BaseMultiRecyclerViewHolder<MomentItemBean> implements BaseMomentViewHolder<MomentItemBean> {


    //头部
    protected ImageView avatar;
    protected TextView nick;
    protected ClickShowMoreLayout userText;

    //底部
//    protected TextView createTime;
//    protected TextView deleteMoments;
//    protected ImageView commentImage;
//    protected FrameLayout menuButton;
//    protected LinearLayout commentAndPraiseLayout;
//    protected PraiseWidget praiseWidget;
//    protected View line;
//    protected CommentContentsLayout commentLayout;

    //内容区
    protected LinearLayout contentLayout;

//    private CommentPopup commentPopup;
//    private DeleteCommentPopup deleteCommentPopup;

    private ICirclePresenter mICirclePresenter;
    private int itemPosition;
    private MomentItemBean momentsInfo;


    public CircleMomentBaseViewHolder(View itemView, int viewType) {
        super(itemView, viewType);
        onFindView(itemView);

        //header
        avatar = (ImageView) findView(avatar, R.id.avatar);
        nick = (TextView) findView(nick, R.id.nick);
        userText = (ClickShowMoreLayout) findView(userText, R.id.item_text_field);
        userText.setOnStateKeyGenerateListener(new ClickShowMoreLayout.OnStateKeyGenerateListener() {
            @Override
            public int onStateKeyGenerated(int originKey) {
                return originKey + itemPosition;
            }
        });

        //bottom
//        createTime = (TextView) findView(createTime, R.id.create_time);
//        deleteMoments = (TextView) findView(deleteMoments, R.id.tv_delete_moment);
//        commentImage = (ImageView) findView(commentImage, R.id.menu_img);
//        menuButton = (FrameLayout) findView(menuButton, R.id.menu_button);
//        commentAndPraiseLayout = (LinearLayout) findView(commentAndPraiseLayout, R.id.comment_praise_layout);
//        praiseWidget = (PraiseWidget) findView(praiseWidget, R.id.praise);
//        line = findView(line, R.id.divider);
//        commentLayout = (CommentContentsLayout) findView(commentLayout, R.id.comment_layout);
//        commentLayout.setMode(CommentContentsLayout.Mode.EXPANDABLE);
//        commentLayout.setOnCommentItemClickListener(onCommentItemClickListener);
//        commentLayout.setOnCommentItemLongClickListener(onCommentItemLongClickListener);
//        commentLayout.setOnCommentWidgetItemClickListener(onCommentWidgetItemClickListener);

        //content
        contentLayout = (LinearLayout) findView(contentLayout, R.id.content);

        //评论弹出框
//        if (commentPopup == null) {
//            commentPopup = new CommentPopup((Activity) getContext());
//            commentPopup.setOnCommentPopupClickListener(onCommentPopupClickListener);
//        }

        //删除评论弹出框
//        if (deleteCommentPopup == null) {
//            deleteCommentPopup = new DeleteCommentPopup((Activity) getContext());
//            deleteCommentPopup.setOnDeleteCommentClickListener(onDeleteCommentClickListener);
//        }
    }

    public void setPresenter(ICirclePresenter iCirclePresenter) {
        this.mICirclePresenter = iCirclePresenter;
    }

    public ICirclePresenter getPresenter() {
        return mICirclePresenter;
    }

    @Override
    public void onBindData(MomentItemBean data, int position) {
        if (data == null) {
            KLog.e("数据是空的！！！！");
            findView(userText, R.id.item_text_field);
            userText.setText("这个动态的数据是空的。。。。OMG");
            return;
        }
        this.momentsInfo = data;
        this.itemPosition = position;
        //通用数据绑定
        onBindMutualDataToViews(data);
        //点击事件
//        menuButton.setOnClickListener(onMenuButtonClickListener);
//        menuButton.setTag(R.id.momentinfo_data_tag_id, data);
//        deleteMoments.setOnClickListener(onDeleteMomentClickListener);
        //传递到子类
        onBindDataToView(data, position, getViewType());
    }

    //绑定头部和底部的公共数据
    private void onBindMutualDataToViews(MomentItemBean data) {
        //header
        ImageLoadMnanger.INSTANCE.loadImage(avatar, data);
        nick.setText(data.getAuthorName());
        userText.setText(data.getContent().getText());
        ViewUtil.setViewsVisible(StringUtil.noEmpty(data.getContent().getText()) ?
                View.VISIBLE : View.GONE, userText);

        //bottom
//        createTime.setText(TimeUtil.getTimeStringFromBmob(data.getCreatedAt()));
//        //是否显示删除状态
//        ViewUtil.setViewsVisible(TextUtils.equals(momentsInfo.getAuthor().getUserid(), LocalHostManager.INSTANCE.getUserid()) ?
//                View.VISIBLE : View.GONE, deleteMoments);
//        boolean needPraiseData = addLikes(data.getLikesList());
//        boolean needCommentData = commentLayout.addComments(data.getCommentList());
//        praiseWidget.setVisibility(needPraiseData ? View.VISIBLE : View.GONE);
//        commentLayout.setVisibility(needCommentData ? View.VISIBLE : View.GONE);
//        line.setVisibility(needPraiseData && needCommentData ? View.VISIBLE : View.GONE);
//        commentAndPraiseLayout.setVisibility(needCommentData || needPraiseData ? View.VISIBLE : View.GONE);

    }

    /**
     * ==================  click listener block
     */

    private CommentContentsLayout.OnCommentWidgetItemClickListener onCommentWidgetItemClickListener = new CommentContentsLayout.OnCommentWidgetItemClickListener() {
        @Override
        public void onCommentItemClicked(@NonNull IComment comment, CharSequence text) {
            if (comment instanceof CommentInfo) {
                UIHelper.ToastMessage("点击的用户 ： 【 " + text + " 】");
            }
        }
    };


    /**
     * 添加点赞
     *
     * @param likesList
     * @return ture=显示点赞，false=不显示点赞
     */
//    private boolean addLikes(List<LikesInfo> likesList) {
//        if (ToolUtil.isListEmpty(likesList)) {
//            return false;
//        }
//        praiseWidget.setDatas(likesList);
//        return true;
//    }
//
//    private CommentContentsLayout.OnCommentItemClickListener onCommentItemClickListener = new CommentContentsLayout.OnCommentItemClickListener() {
//        @Override
//        public void onCommentWidgetClick(@NonNull CommentWidget widget) {
//            IComment comment = widget.getData();
//            CommentInfo commentInfo = null;
//            if (comment instanceof CommentInfo) {
//                commentInfo = (CommentInfo) comment;
//            }
//            if (commentInfo == null) return;
//            if (commentInfo.canDelete()) {
//                deleteCommentPopup.showPopupWindow(commentInfo);//删除评论对话框
//            } else {
//                momentPresenter.showCommentBox(null, itemPosition, momentsInfo.getMomentid(), widget);//回复评论对话框
//            }
//        }
//    };
//
//    private CommentContentsLayout.OnCommentItemLongClickListener onCommentItemLongClickListener = new CommentContentsLayout.OnCommentItemLongClickListener() {
//        @Override
//        public boolean onCommentWidgetLongClick(@NonNull CommentWidget widget) {
//            return false;
//        }
//    };
//
//    //删除评论回调
//    private DeleteCommentPopup.OnDeleteCommentClickListener onDeleteCommentClickListener = new DeleteCommentPopup.OnDeleteCommentClickListener() {
//        @Override
//        public void onDelClick(CommentInfo commentInfo) {
//            momentPresenter.deleteComment(itemPosition, commentInfo.getCommentid(), momentsInfo.getCommentList());
//        }
//    };
//
//    //删除动态
//    private View.OnClickListener onDeleteMomentClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            momentPresenter.deleteMoments(v.getContext(), momentsInfo);
//        }
//    };
//
//    //点赞/取消点赞，评论
//    private View.OnClickListener onMenuButtonClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            MomentsInfo info = (MomentsInfo) v.getTag(R.id.momentinfo_data_tag_id);
//            if (info != null) {
//                commentPopup.updateMomentInfo(info);
//                commentPopup.showPopupWindow(commentImage);
//            }
//        }
//    };
//
//    //点赞/取消点赞，评论的回调方法
//    private CommentPopup.OnCommentPopupClickListener onCommentPopupClickListener = new CommentPopup.OnCommentPopupClickListener() {
//        @Override
//        public void onLikeClick(View v, @NonNull MomentsInfo info, boolean hasLiked) {
//            if (hasLiked) {//取消点赞
//                momentPresenter.unLike(itemPosition, info.getLikesObjectid(), info.getLikesList());
//            } else {//点赞
//                momentPresenter.addLike(itemPosition, info.getMomentid(), info.getLikesList());
//            }
//
//        }
//
//        @Override//评论
//        public void onCommentClick(View v, @NonNull MomentsInfo info) {
//            momentPresenter.showCommentBox(itemView, itemPosition, info.getMomentid(), null);
//        }
//    };

    /**
     * ============  tools method block
     */


    protected final View findView(View view, int resid) {
        if (resid > 0 && itemView != null && view == null) {
            return itemView.findViewById(resid);
        }
        return view;
    }


}
