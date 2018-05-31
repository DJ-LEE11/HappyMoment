package com.uwork.happymoment.mvp.hotel.bean;

import java.io.Serializable;

/**
 * Created by jie on 2018/5/31.
 */

public class GetHotelListForCity implements Serializable{
    /**
     * city : string
     */

    private String city;

    public GetHotelListForCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
