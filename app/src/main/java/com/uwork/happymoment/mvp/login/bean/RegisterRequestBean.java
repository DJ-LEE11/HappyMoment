package com.uwork.happymoment.mvp.login.bean;

/**
 * Created by jie on 2018/5/9.
 */

public class RegisterRequestBean {
    /**
     * code : string
     * password : string
     * phone : string
     * sharingCode : string
     */

    private String code;
    private String password;
    private String phone;
    private String sharingCode;

    public RegisterRequestBean(String code, String password, String phone, String sharingCode) {
        this.code = code;
        this.password = password;
        this.phone = phone;
        this.sharingCode = sharingCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSharingCode() {
        return sharingCode;
    }

    public void setSharingCode(String sharingCode) {
        this.sharingCode = sharingCode;
    }
}
