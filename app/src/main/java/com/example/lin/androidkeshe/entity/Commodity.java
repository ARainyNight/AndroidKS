package com.example.lin.androidkeshe.entity;

import java.net.URL;

/**
 * Created by lin on 2018/7/1.
 * 描述:  菜单菜品实体类
 */
public class Commodity {

    private int imgId;
    private String commodity_name;
    private int commodity_price;

    public Commodity(int imgId, String commodity_name,int commodity_price) {
        this.imgId = imgId;
        this.commodity_name = commodity_name;
        this.commodity_price =commodity_price;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getCommodity_name() {
        return commodity_name;
    }

    public void setCommodity_name(String commodity_name) {
        this.commodity_name = commodity_name;
    }

    public int getCommodity_price() {
        return commodity_price;
    }

    public void setCommodity_price(int commodity_price) {
        this.commodity_price = commodity_price;
    }
}
