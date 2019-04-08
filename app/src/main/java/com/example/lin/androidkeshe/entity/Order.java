package com.example.lin.androidkeshe.entity;

/**
 * Created by lin on 2018/7/2.
 * 描述:  订单实体类
 */
public class Order {

    //订单图片
    private int orderimg_id ;
    //商家名称
    private String order_business_name;
    //订单日期
    private String order_data ;
    //订单内容
    private String order_content;
    //订单价格
    private int order_price ;

    public Order(int orderimg_id, String order_business_name, String order_data, String order_content, int order_price) {
        this.orderimg_id = orderimg_id;
        this.order_business_name = order_business_name;
        this.order_data = order_data;
        this.order_content = order_content;
        this.order_price = order_price;
    }

    public int getOrderimg_id() {
        return orderimg_id;
    }

    public void setOrderimg_id(int orderimg_id) {
        this.orderimg_id = orderimg_id;
    }

    public String getOrder_business_name() {
        return order_business_name;
    }

    public void setOrder_business_name(String order_business_name) {
        this.order_business_name = order_business_name;
    }

    public String getOrder_data() {
        return order_data;
    }

    public void setOrder_data(String order_data) {
        this.order_data = order_data;
    }

    public String getOrder_content() {
        return order_content;
    }

    public void setOrder_content(String order_content) {
        this.order_content = order_content;
    }

    public int getOrder_price() {
        return order_price;
    }

    public void setOrder_price(int order_price) {
        this.order_price = order_price;
    }
}
