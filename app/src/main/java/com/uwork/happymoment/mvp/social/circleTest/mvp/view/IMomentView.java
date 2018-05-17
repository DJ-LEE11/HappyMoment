package com.uwork.happymoment.mvp.social.circleTest.mvp.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.circle_base_library.mvp.IBaseView;
import com.example.circle_base_ui.widget.commentwidget.CommentWidget;
import com.example.circle_common.common.entity.CommentInfo;
import com.example.circle_common.common.entity.LikesInfo;
import com.example.circle_common.common.entity.MomentsInfo;

import java.util.List;


/**
 * Created by 大灯泡 on 2016/12/7.
 */

public interface IMomentView extends IBaseView {

    void onLikeChange(int itemPos, List<LikesInfo> likeUserList);

    void onCommentChange(int itemPos, List<CommentInfo> commentInfoList);

    /**
     * 因为recyclerview通过位置找到itemview有可能会找不到对应的View，所以这次直接传值
     *
     * @param viewHolderRootView
     * @param itemPos
     * @param momentid
     * @param commentWidget
     */
    void showCommentBox(@Nullable View viewHolderRootView, int itemPos, String momentid, CommentWidget commentWidget);

    void onDeleteMomentsInfo(@NonNull MomentsInfo momentsInfo);

}
