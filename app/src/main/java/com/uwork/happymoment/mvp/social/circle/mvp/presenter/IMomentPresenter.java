package com.uwork.happymoment.mvp.social.circle.mvp.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.circle_base_library.mvp.IBasePresenter;
import com.example.circle_common.common.entity.CommentInfo;
import com.example.circle_common.common.entity.LikesInfo;
import com.example.circle_common.common.entity.MomentsInfo;
import com.uwork.happymoment.mvp.social.circle.mvp.view.IMomentView;

import java.util.List;


/**
 * Created by 大灯泡 on 2016/12/7.
 */

public interface IMomentPresenter extends IBasePresenter<IMomentView> {


    void addLike(int viewHolderPos, String momentid, List<LikesInfo> currentLikeList);

    void unLike(int viewHolderPos, String likesid, List<LikesInfo> currentLikeList);

    void addComment(int viewHolderPos, String momentid, String replyUserid, String commentContent, List<CommentInfo> currentCommentList);

    void deleteComment(int viewHolderPos, String commentid, List<CommentInfo> currentCommentList);

    void deleteMoments(Context context, @NonNull MomentsInfo momentsInfo);

}
