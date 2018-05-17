package com.uwork.happymoment.mvp.social.circleTest.mvp.callback;


import com.example.circle_common.common.entity.CommentInfo;

/**
 * Created by 大灯泡 on 2016/12/9.
 * <p>
 * 评论Callback
 */

public interface OnCommentChangeCallback {

    void onAddComment(CommentInfo response);

    void onDeleteComment(String commentid);

}
