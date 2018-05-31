package com.uwork.happymoment.mvp.hotel.bean;

import java.io.Serializable;

/**
 * Created by jie on 2018/5/31.
 */

public class IntegralBean implements Serializable{
    /**
     * exchangeRate : string
     * integral : string
     * nickName : string
     * phone : string
     */

    private String exchangeRate;
    private String integral;
    private String nickName;
    private String phone;

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
