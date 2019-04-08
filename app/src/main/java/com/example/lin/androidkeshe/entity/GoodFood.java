package com.example.lin.androidkeshe.entity;

/**
 * Created by lin on 2018/7/3.
 * 描述:  美食界面实体类
 */
public class GoodFood {

    //食物图片
    private int food_imgId ;
    //食物名称
    private String food_name;
    //来源商家
    private String food_business;
    //食物价格
    private int food_price;

    public GoodFood(int food_imgId,String food_name, String food_business, int food_price) {
        this.food_imgId = food_imgId;
        this.food_business = food_business;
        this.food_price = food_price;
        this.food_name= food_name;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public int getFood_imgId() {
        return food_imgId;
    }

    public void setFood_imgId(int food_imgId) {
        this.food_imgId = food_imgId;
    }

    public String getFood_business() {
        return food_business;
    }

    public void setFood_business(String food_business) {
        this.food_business = food_business;
    }

    public int getFood_price() {
        return food_price;
    }

    public void setFood_price(int food_price) {
        this.food_price = food_price;
    }
}
