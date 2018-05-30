package com.uwork.happymoment.mvp.hotel.bean;

import java.io.Serializable;

/**
 * Created by jie on 2018/5/30.
 */

public class HotCityLabelBean implements Serializable{
    /**
     * id : 3
     * name : 北京
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
