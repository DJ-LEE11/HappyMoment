package com.uwork.happymoment.mvp.hotel.bean;

import java.io.Serializable;

/**
 * Created by jie on 2018/5/31.
 */

public class GetHotelListForClassifyId implements Serializable{
    /**
     * classifyId : 0
     */

    private int classifyId;

    public GetHotelListForClassifyId(int classifyId) {
        this.classifyId = classifyId;
    }

    public int getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(int classifyId) {
        this.classifyId = classifyId;
    }
}
