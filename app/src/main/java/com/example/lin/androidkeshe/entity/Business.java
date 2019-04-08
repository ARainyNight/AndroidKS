package com.example.lin.androidkeshe.entity;

/**
 * Created by lin on 2018/6/23.
 * 描述:  商家类
 */
public class Business {

    //商家图片id
    private int business_imgId;

    //商家名称
    private String business_name;

    //商家地址
    private String business_location;

    //商家起送费
    private int business_takeout_cost;

    public int getBusiness_imgId() {
        return business_imgId;
    }

    public void setBusiness_imgId(int business_imgId) {
        this.business_imgId = business_imgId;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getBusiness_location() {
        return business_location;
    }

    public void setBusiness_location(String business_location) {
        this.business_location = business_location;
    }

    public int getBusiness_takeout_cost() {
        return business_takeout_cost;
    }

    public void setBusiness_takeout_cost(int business_takeout_cost) {
        this.business_takeout_cost = business_takeout_cost;
    }


    public Business(int business_imgId, String business_name, String business_location, int business_takeout_cost) {
        this.business_imgId = business_imgId;
        this.business_name = business_name;
        this.business_location = business_location;
        this.business_takeout_cost = business_takeout_cost;
    }
}
