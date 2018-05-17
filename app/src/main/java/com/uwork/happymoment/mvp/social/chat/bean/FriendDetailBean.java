package com.uwork.happymoment.mvp.social.chat.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jie on 2018/5/17.
 */

public class FriendDetailBean implements Serializable{
    /**
     * account : string
     * city : string
     * images : ["string"]
     * imtoken : string
     * labelNames : [{"id":0,"name":"string"}]
     * nickName : string
     * province : string
     * remarkPhone : string
     * remarksName : string
     */

    private String account;
    private String city;
    private String imtoken;
    private String nickName;
    private String province;
    private String remarkPhone;
    private String remarksName;
    private List<String> images;
    private List<LabelNamesBean> labelNames;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getImtoken() {
        return imtoken;
    }

    public void setImtoken(String imtoken) {
        this.imtoken = imtoken;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRemarkPhone() {
        return remarkPhone;
    }

    public void setRemarkPhone(String remarkPhone) {
        this.remarkPhone = remarkPhone;
    }

    public String getRemarksName() {
        return remarksName;
    }

    public void setRemarksName(String remarksName) {
        this.remarksName = remarksName;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<LabelNamesBean> getLabelNames() {
        return labelNames;
    }

    public void setLabelNames(List<LabelNamesBean> labelNames) {
        this.labelNames = labelNames;
    }

    public static class LabelNamesBean {
        /**
         * id : 0
         * name : string
         */

        private int id;
        private String name;

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
