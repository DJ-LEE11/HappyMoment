package com.uwork.happymoment.mvp.social.chat.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jie on 2018/5/18.
 */

public class NewFriendResponseBean implements Serializable{
    private List<FriendPageResponseBean> friendPageResponse;
    private List<NonFriendListResponsesBean> nonFriendListResponses;

    public List<FriendPageResponseBean> getFriendPageResponse() {
        return friendPageResponse;
    }

    public void setFriendPageResponse(List<FriendPageResponseBean> friendPageResponse) {
        this.friendPageResponse = friendPageResponse;
    }

    public List<NonFriendListResponsesBean> getNonFriendListResponses() {
        return nonFriendListResponses;
    }

    public void setNonFriendListResponses(List<NonFriendListResponsesBean> nonFriendListResponses) {
        this.nonFriendListResponses = nonFriendListResponses;
    }

    public static class FriendPageResponseBean {
        /**
         * avatar : string
         * id : 0
         * nickName : string
         * remarksName : string
         */

        private String avatar;
        private int id;
        private String nickName;
        private String remarksName;

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

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getRemarksName() {
            return remarksName;
        }

        public void setRemarksName(String remarksName) {
            this.remarksName = remarksName;
        }
    }

    public static class NonFriendListResponsesBean {
        /**
         * avatar : string
         * id : 0
         * nickName : string
         */

        private String avatar;
        private int id;
        private String nickName;

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

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }
    }
}
