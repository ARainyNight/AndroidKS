package com.example.lin.androidkeshe.entity;

/**
 * Created by lin on 2018/7/2.
 * 描述:评论实体类
 */
public class Comment {

    //用户头像
    private int user_imgId;
    //用户名字
    private String user_name;
    //用户评分
    private String rating ;
    //评价内容
    private String content ;
    //时间
    private String data ;

    public int getUser_imgId() {
        return user_imgId;
    }

    public void setUser_imgId(int user_imgId) {
        this.user_imgId = user_imgId;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Comment(int user_imgId, String user_name, String rating, String content, String data) {
        this.user_imgId = user_imgId;
        this.user_name = user_name;
        this.rating = rating;
        this.content = content;
        this.data = data;
    }
}
