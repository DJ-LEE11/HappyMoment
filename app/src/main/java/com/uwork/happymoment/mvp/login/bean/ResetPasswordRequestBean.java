package com.uwork.happymoment.mvp.login.bean;

/**
 * Created by jie on 2018/5/9.
 */

public class ResetPasswordRequestBean {
    /**
     * code : string
     * password : string
     * phone : string
     */

    private String phone;
    private String code;
    private String password;

    public ResetPasswordRequestBean(String phone, String code, String password) {
        this.phone = phone;
        this.code = code;
        this.password = password;
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
}
