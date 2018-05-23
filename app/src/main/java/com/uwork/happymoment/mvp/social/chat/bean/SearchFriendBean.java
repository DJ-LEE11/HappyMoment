package com.uwork.happymoment.mvp.social.chat.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jie on 2018/5/18.
 */

public class SearchFriendBean implements Serializable{
    /**
     * id : 14
     * avatar :
     * account : 18028374187
     * nickName : 栋杰
     * remarksName : 栋杰8
     * labelNames : null
     * remarkPhone : null
     * province : 广东
     * city : 中山
     * images : ["string","11111","11111","萨芬"]
     */

    private int id;
    private String avatar;
    private String account;
    private String nickName;
    private String remarksName;
    private Object labelNames;
    private Object remarkPhone;
    private String province;
    private String city;
    private List<String> images;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public Object getLabelNames() {
        return labelNames;
    }

    public void setLabelNames(Object labelNames) {
        this.labelNames = labelNames;
    }

    public Object getRemarkPhone() {
        return remarkPhone;
    }

    public void setRemarkPhone(Object remarkPhone) {
        this.remarkPhone = remarkPhone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
