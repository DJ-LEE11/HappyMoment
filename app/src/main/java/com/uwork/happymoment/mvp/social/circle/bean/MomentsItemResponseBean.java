package com.uwork.happymoment.mvp.social.circle.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jie on 2018/5/19.
 */

public class MomentsItemResponseBean implements Serializable{
    /**
     * avatar : string
     * content : string
     * createTime : string
     * id : 0
     * momentsCommentResponseBeans : [{"content":"string","createTime":"string","id":0,"sendUserId":0,"toUserId":0}]
     * momentsFavourResponseBeans : [{"avatar":"string","id":0,"name":"string"}]
     * name : string
     * picture : ["string"]
     * userId : 0
     */

    private String avatar;
    private String content;
    private String createTime;
    private int id;
    private String name;
    private int userId;
    private List<MomentsCommentResponseBeansBean> momentsCommentResponseBeans;
    private List<MomentsFavourResponseBeansBean> momentsFavourResponseBeans;
    private List<String> picture;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<MomentsCommentResponseBeansBean> getMomentsCommentResponseBeans() {
        return momentsCommentResponseBeans;
    }

    public void setMomentsCommentResponseBeans(List<MomentsCommentResponseBeansBean> momentsCommentResponseBeans) {
        this.momentsCommentResponseBeans = momentsCommentResponseBeans;
    }

    public List<MomentsFavourResponseBeansBean> getMomentsFavourResponseBeans() {
        return momentsFavourResponseBeans;
    }

    public void setMomentsFavourResponseBeans(List<MomentsFavourResponseBeansBean> momentsFavourResponseBeans) {
        this.momentsFavourResponseBeans = momentsFavourResponseBeans;
    }

    public List<String> getPicture() {
        return picture;
    }

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }

    public static class MomentsCommentResponseBeansBean {
        /**
         * content : string
         * createTime : string
         * id : 0
         * sendUserId : 0
         * toUserId : 0
         */

        private String content;
        private String createTime;
        private int id;
        private int sendUserId;
        private int toUserId;

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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSendUserId() {
            return sendUserId;
        }

        public void setSendUserId(int sendUserId) {
            this.sendUserId = sendUserId;
        }

        public int getToUserId() {
            return toUserId;
        }

        public void setToUserId(int toUserId) {
            this.toUserId = toUserId;
        }
    }

    public static class MomentsFavourResponseBeansBean {
        /**
         * avatar : string
         * id : 0
         * name : string
         */

        private String avatar;
        private int id;
        private String name;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
