package com.uwork.happymoment.mvp.hotel.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jie on 2018/5/30.
 */

public class HotelItemBean implements Serializable{

    /**
     * city : string
     * contactNumber : string
     * hostelImgResponseBeans : [{"des":"string","id":0,"imgLink":"string"}]
     * id : 0
     * lngLat : string
     * name : string
     * rang : 0
     * topicClassifyId : 0
     */

    private String city;
    private String contactNumber;
    private int id;
    private String lngLat;
    private String name;
    private int rang;
    private int topicClassifyId;
    private List<HostelImgResponseBeansBean> hostelImgResponseBeans;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLngLat() {
        return lngLat;
    }

    public void setLngLat(String lngLat) {
        this.lngLat = lngLat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }

    public int getTopicClassifyId() {
        return topicClassifyId;
    }

    public void setTopicClassifyId(int topicClassifyId) {
        this.topicClassifyId = topicClassifyId;
    }

    public List<HostelImgResponseBeansBean> getHostelImgResponseBeans() {
        return hostelImgResponseBeans;
    }

    public void setHostelImgResponseBeans(List<HostelImgResponseBeansBean> hostelImgResponseBeans) {
        this.hostelImgResponseBeans = hostelImgResponseBeans;
    }

    public static class HostelImgResponseBeansBean {
        /**
         * des : string
         * id : 0
         * imgLink : string
         */

        private String des;
        private int id;
        private String imgLink;

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImgLink() {
            return imgLink;
        }

        public void setImgLink(String imgLink) {
            this.imgLink = imgLink;
        }
    }
}
