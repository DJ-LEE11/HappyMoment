package com.uwork.happymoment.mvp.social.circle.viewholder;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.circle_base_library.utils.StringUtil;
import com.example.circle_base_library.utils.TimeUtil;
import com.example.circle_base_library.utils.ToolUtil;
import com.example.circle_base_ui.base.adapter.BaseMultiRecyclerViewHolder;
import com.example.circle_base_ui.imageloader.ImageLoadMnanger;
import com.example.circle_base_ui.util.ViewUtil;
import com.example.circle_base_ui.widget.commentwidget.CommentWidget;
import com.example.circle_base_ui.widget.commentwidget.IComment;
import com.example.circle_base_ui.widget.common.ClickShowMoreLayout;
import com.socks.library.KLog;
import com.uwork.happymoment.R;
import com.uwork.happymoment.manager.UserManager;
import com.uwork.happymoment.mvp.social.circle.bean.MomentCommentBean;
import com.uwork.happymoment.mvp.social.circle.bean.MomentItemBean;
import com.uwork.happymoment.mvp.social.circle.bean.MomentLikeBean;
import com.uwork.happymoment.mvp.social.circle.presenter.ICircleHandelPresenter;
import com.uwork.happymoment.mvp.social.circle.ui.CommentLikePopup;
import com.uwork.happymoment.mvp.social.circle.ui.comment.MomentCommentLayout;
import com.uwork.happymoment.mvp.social.circle.ui.like.GiveLikeWidget;
import com.uwork.libutil.ToastUtils;

import java.util.List;


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
    protected TextView createTime;
    protected TextView deleteMoments;
    protected ImageView commentImage;
    protected FrameLayout menuButton;
    protected LinearLayout commentAndPraiseLayout;
    protected GiveLikeWidget giveLikeWidget;
    protected View line;
    protected MomentCommentLayout commentLayout;

    //内容区
    protected LinearLayout contentLayout;

    private CommentLikePopup commentLikePopup;
//    private DeleteCommentPopup deleteCommentPopup;

    private ICircleHandelPresenter mICirclePresenter;
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
        createTime = (TextView) findView(createTime, R.id.create_time);
        deleteMoments = (TextView) findView(deleteMoments, R.id.tv_delete_moment);
        commentImage = (ImageView) findView(commentImage, R.id.menu_img);
        menuButton = (FrameLayout) findView(menuButton, R.id.menu_button);
        commentAndPraiseLayout = (LinearLayout) findView(commentAndPraiseLayout, R.id.comment_praise_layout);
        giveLikeWidget = (GiveLikeWidget) findView(giveLikeWidget, R.id.giveLike);
        line = findView(line, R.id.divider);
        commentLayout = (MomentCommentLayout) findView(commentLayout, R.id.comment_layout);
        commentLayout.setMode(MomentCommentLayout.Mode.EXPANDABLE);
        commentLayout.setOnCommentItemClickListener(onCommentItemClickListener);
        commentLayout.setOnCommentItemLongClickListener(onCommentItemLongClickListener);
        commentLayout.setOnCommentWidgetItemClickListener(onCommentWidgetItemClickListener);

        //content
        contentLayout = (LinearLayout) findView(contentLayout, R.id.content);

        //评论弹出框
        if (commentLikePopup == null) {
            commentLikePopup = new CommentLikePopup((Activity) getContext());
            commentLikePopup.setOnCommentPopupClickListener(onCommentPopupClickListener);
        }

        //删除评论弹出框
//        if (deleteCommentPopup == null) {
//            deleteCommentPopup = new DeleteCommentPopup((Activity) getContext());
//            deleteCommentPopup.setOnDeleteCommentClickListener(onDeleteCommentClickListener);
//        }
    }

    public void setPresenter(ICircleHandelPresenter iCirclePresenter) {
        this.mICirclePresenter = iCirclePresenter;
    }

    public ICircleHandelPresenter getPresenter() {
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
        menuButton.setOnClickListener(onMenuButtonClickListener);
        menuButton.setTag(R.id.momentinfo_data_tag_id, data);
        deleteMoments.setOnClickListener(onDeleteMomentClickListener);
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
        createTime.setText(TimeUtil.getTimeStringFromBmob(data.getCreateTime()));
//        //是否显示删除状态
        ViewUtil.setViewsVisible(momentsInfo.getAuthorId() == UserManager.getInstance().getUserId(getContext()) ?
                View.VISIBLE : View.GONE, deleteMoments);

        boolean needPraiseData = addLikes(data.getLikesList());
        boolean needCommentData = commentLayout.addComments(data.getCommentList());
        giveLikeWidget.setVisibility(needPraiseData ? View.VISIBLE : View.GONE);
        commentLayout.setVisibility(needCommentData ? View.VISIBLE : View.GONE);
        line.setVisibility(needPraiseData && needCommentData ? View.VISIBLE : View.GONE);
        commentAndPraiseLayout.setVisibility(needCommentData || needPraiseData ? View.VISIBLE : View.GONE);

    }

    /**
     * ==================  click listener block
     */

    private MomentCommentLayout.OnCommentWidgetItemClickListener onCommentWidgetItemClickListener = new MomentCommentLayout.OnCommentWidgetItemClickListener() {
        @Override
        public void onCommentItemClicked(@NonNull IComment comment, CharSequence text) {
            if (comment instanceof MomentCommentBean) {
//                UIHelper.ToastMessage("点击的用户 ： 【 " + text + " 】");
            }
        }
    };


    /**
     * 添加点赞
     *
     * @param likesList
     * @return ture=显示点赞，false=不显示点赞
     */
    private boolean addLikes(List<MomentLikeBean> likesList) {
        if (ToolUtil.isListEmpty(likesList)) {
            return false;
        }
        giveLikeWidget.setDatas(likesList);
        return true;
    }

    private MomentCommentLayout.OnCommentItemClickListener onCommentItemClickListener = new MomentCommentLayout.OnCommentItemClickListener() {
        @Override
        public void onCommentWidgetClick(@NonNull CommentWidget widget) {
            IComment comment = widget.getData();
            MomentCommentBean commentInfo = null;
            if (comment instanceof MomentCommentBean) {
                commentInfo = (MomentCommentBean) comment;
            }
            if (commentInfo == null) return;
            if (commentInfo.canDelete()) {
                //删除评论对话框
//                deleteCommentPopup.showPopupWindow(commentInfo);
//                ToastUtils.show(getContext(),"删除评论");
            } else {
                //回复评论对话框
//                ToastUtils.show(getContext(),"回复评论");
                mICirclePresenter.showCommentBox(null, itemPosition, momentsInfo.getMomentId(), widget);
            }
        }
    };

    private MomentCommentLayout.OnCommentItemLongClickListener onCommentItemLongClickListener = new MomentCommentLayout.OnCommentItemLongClickListener() {
        @Override
        public boolean onCommentWidgetLongClick(@NonNull CommentWidget widget) {
            return false;
        }
    };

    //删除评论回调
//    private DeleteCommentPopup.OnDeleteCommentClickListener onDeleteCommentClickListener = new DeleteCommentPopup.OnDeleteCommentClickListener() {
//        @Override
//        public void onDelClick(CommentInfo commentInfo) {
//            momentPresenter.deleteComment(itemPosition, commentInfo.getCommentid(), momentsInfo.getCommentList());
//        }
//    };

    //删除动态
    private View.OnClickListener onDeleteMomentClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new AlertDialog.Builder(getContext())
                    .setTitle("删除动态")
                    .setMessage("确定删除吗？")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            mICirclePresenter.deleteMoment(itemPosition,momentsInfo.getMomentId());
                        }
                    }).show();
        }
    };

    //点赞/取消点赞，评论
    private View.OnClickListener onMenuButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MomentItemBean info = (MomentItemBean) v.getTag(R.id.momentinfo_data_tag_id);
            if (info != null) {
                commentLikePopup.updateMomentInfo(info);
                commentLikePopup.showPopupWindow(commentImage);
            }
        }
    };

    //点赞/取消点赞，评论的回调方法
    private CommentLikePopup.OnCommentPopupClickListener onCommentPopupClickListener = new CommentLikePopup.OnCommentPopupClickListener() {
        @Override
        public void onLikeClick(View v, @NonNull MomentItemBean info, boolean hasLiked) {
            if (hasLiked) {//取消点赞
                ToastUtils.show(getContext(), "已点赞");
            } else {//点赞
                mICirclePresenter.giveLikeMoment(itemPosition,info.getLikesList() ,info.getMomentId());
            }

        }

        @Override//评论
        public void onCommentClick(View v, @NonNull MomentItemBean info) {
            mICirclePresenter.showCommentBox(itemView, itemPosition, info.getMomentId(), null);
        }
    };

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
