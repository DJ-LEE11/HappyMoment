package com.uwork.happymoment.mvp.social.circle.bean;

import com.example.circle_base_ui.widget.commentwidget.IComment;

/**
 * Created by jie on 2018/5/21.
 */

public class MomentCommentBean implements IComment<MomentCommentBean>{


    /**
     * content : string
     * createTime : string
     * id : 0
     * sendUserId : 0
     * sendUserName : string
     * toUserId : 0
     * toUserName : string
     */

    private int contentId;
    private int sendUserId;
    private String sendUserName;
    private int toUserId;
    private String toUserName;
    private int currentUserId;
    private String content;
    private String createTime;

    public MomentCommentBean(int contentId, int sendUserId, String sendUserName, int toUserId, String toUserName, int currentUserId, String content, String createTime) {
        this.contentId = contentId;
        this.sendUserId = sendUserId;
        this.sendUserName = sendUserName;
        this.toUserId = toUserId;
        this.toUserName = toUserName;
        this.currentUserId = currentUserId;
        this.content = content;
        this.createTime = createTime;
    }

    public boolean canDelete() {
        return sendUserId == currentUserId;
    }

    @Override
    public String getCommentCreatorName() {
        return sendUserName;
    }

    @Override
    public String getReplyerName() {
        return toUserName;
    }

    @Override
    public String getCommentContent() {
        return content;
    }

    @Override
    public MomentCommentBean getData() {
        return this;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(int sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }


    public int getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(int currentUserId) {
        this.currentUserId = currentUserId;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }
}
