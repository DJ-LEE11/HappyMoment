package com.uwork.happymoment.mvp.hotel.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jie on 2018/5/31.
 */

public class RoomDetailBean implements Serializable{
    /**
     * activitySetMealResponseBeans : [{"activity":"string","id":0,"imgLink":["string"],"memberPrice":0,"name":"string","price":0,"restaurant":"string","touristRoute":"string"}]
     * hostelRoomResponseBean : {"facilities":"string","fitNumber":0,"id":0,"imgLink":["string"],"introduce":"string","periphery":"string","roomModel":"string","roomNo":"string","roomPrice":0,"videoUrl":["string"]}
     * id : 0
     */

    private HostelRoomResponseBeanBean hostelRoomResponseBean;
    private int id;
    private List<ActivitySetMealResponseBeansBean> activitySetMealResponseBeans;

    public HostelRoomResponseBeanBean getHostelRoomResponseBean() {
        return hostelRoomResponseBean;
    }

    public void setHostelRoomResponseBean(HostelRoomResponseBeanBean hostelRoomResponseBean) {
        this.hostelRoomResponseBean = hostelRoomResponseBean;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ActivitySetMealResponseBeansBean> getActivitySetMealResponseBeans() {
        return activitySetMealResponseBeans;
    }

    public void setActivitySetMealResponseBeans(List<ActivitySetMealResponseBeansBean> activitySetMealResponseBeans) {
        this.activitySetMealResponseBeans = activitySetMealResponseBeans;
    }

    public static class HostelRoomResponseBeanBean {
        /**
         * facilities : string
         * fitNumber : 0
         * id : 0
         * imgLink : ["string"]
         * introduce : string
         * periphery : string
         * roomModel : string
         * roomNo : string
         * roomPrice : 0
         * videoUrl : ["string"]
         */

        private String facilities;
        private int fitNumber;
        private int id;
        private String introduce;
        private String periphery;
        private String roomModel;
        private String roomNo;
        private int roomPrice;
        private List<String> imgLink;
        private List<String> videoUrl;

        public String getFacilities() {
            return facilities;
        }

        public void setFacilities(String facilities) {
            this.facilities = facilities;
        }

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

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getPeriphery() {
            return periphery;
        }

        public void setPeriphery(String periphery) {
            this.periphery = periphery;
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

        public List<String> getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(List<String> videoUrl) {
            this.videoUrl = videoUrl;
        }
    }

    public static class ActivitySetMealResponseBeansBean {
        /**
         * activity : string
         * id : 0
         * imgLink : ["string"]
         * memberPrice : 0
         * name : string
         * price : 0
         * restaurant : string
         * touristRoute : string
         */

        private String activity;
        private int id;
        private int memberPrice;
        private String name;
        private int price;
        private String restaurant;
        private String touristRoute;
        private List<String> imgLink;

        public String getActivity() {
            return activity;
        }

        public void setActivity(String activity) {
            this.activity = activity;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMemberPrice() {
            return memberPrice;
        }

        public void setMemberPrice(int memberPrice) {
            this.memberPrice = memberPrice;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getRestaurant() {
            return restaurant;
        }

        public void setRestaurant(String restaurant) {
            this.restaurant = restaurant;
        }

        public String getTouristRoute() {
            return touristRoute;
        }

        public void setTouristRoute(String touristRoute) {
            this.touristRoute = touristRoute;
        }

        public List<String> getImgLink() {
            return imgLink;
        }

        public void setImgLink(List<String> imgLink) {
            this.imgLink = imgLink;
        }
    }
}
