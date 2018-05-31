package com.uwork.happymoment.mvp.hotel.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jie on 2018/5/31.
 */

public class RoomItemBean implements Serializable{
    /**
     * fitNumber : 0
     * id : 0
     * imgLink : ["string"]
     * roomModel : string
     * roomNo : string
     * roomPrice : 0
     */

    private int fitNumber;
    private int id;
    private String roomModel;
    private String roomNo;
    private int roomPrice;
    private List<String> imgLink;

    public int getFitNumber() {
        return fitNumber;
    }

    public void setFitNumber(int fitNumber) {
        this.fitNumber = fitNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomModel() {
        return roomModel;
    }

    public void setRoomModel(String roomModel) {
        this.roomModel = roomModel;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public int getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(int roomPrice) {
        this.roomPrice = roomPrice;
    }

    public List<String> getImgLink() {
        return imgLink;
    }

    public void setImgLink(List<String> imgLink) {
        this.imgLink = imgLink;
    }
}
