package com.uwork.happymoment.mvp.login.bean;

/**
 * Created by jie on 2018/5/9.
 */

public class LoginRequestBean {
    /**
     * password : string
     * phone : string
     */

    private String phone;
    private String password;

    public LoginRequestBean(String phone, String password) {
        this.phone = phone;
        this.password = password;
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
