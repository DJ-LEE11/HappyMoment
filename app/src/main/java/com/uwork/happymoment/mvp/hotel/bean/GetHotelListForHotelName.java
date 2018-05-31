package com.uwork.happymoment.mvp.hotel.bean;

import java.io.Serializable;

/**
 * Created by jie on 2018/5/31.
 */

public class GetHotelListForHotelName implements Serializable{
    /**
     * name : string
     */

    private String name;

    public GetHotelListForHotelName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
