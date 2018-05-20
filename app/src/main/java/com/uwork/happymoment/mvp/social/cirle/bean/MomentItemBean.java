package com.uwork.happymoment.mvp.social.cirle.bean;

import com.example.circle_base_ui.base.adapter.MultiType;
import com.example.circle_common.common.MomentsType;
import com.socks.library.KLog;

import java.util.List;

/**
 * Created by jie on 2018/5/19.
 */

public class MomentItemBean implements MultiType {

    private int momentId;
    private int authorId;
    private String authorName;
    private String authorAvatar;
    private String createTime;

    //内容
    private MomentContentBean content;
    //点赞
    private List<MomentLikeBean> likesList;
    //评论

    public MomentItemBean(int momentId, int authorId, String authorName, String authorAvatar, String createTime, MomentContentBean content) {
        this.momentId = momentId;
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorAvatar = authorAvatar;
        this.createTime = createTime;
        this.content = content;
    }

    public MomentItemBean(int momentId, int authorId, String authorName, String authorAvatar, String createTime, MomentContentBean content, List<MomentLikeBean> likesList) {
        this.momentId = momentId;
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorAvatar = authorAvatar;
        this.createTime = createTime;
        this.content = content;
        this.likesList = likesList;
    }


    //返回内容类型(纯文字、图文、链接)
    public int getMomentType() {
        if (content == null) {
            KLog.e("朋友圈内容居然是空的？？？？？MDZZ！！！！");
            return MomentsType.EMPTY_CONTENT;
        }
        return content.getMomentType();
    }

    @Override
    public int getItemType() {
        return getMomentType();
    }

    public int getMomentId() {
        return momentId;
    }

    public void setMomentId(int momentId) {
        this.momentId = momentId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public MomentContentBean getContent() {
        return content;
    }

    public void setContent(MomentContentBean content) {
        this.content = content;
    }

    public List<MomentLikeBean> getLikesList() {
        return likesList;
    }

    public void setLikesList(List<MomentLikeBean> likesList) {
        this.likesList = likesList;
    }
}
