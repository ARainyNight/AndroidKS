package com.example.lin.androidkeshe.json;

/**
 * Created by lin on 2018/7/5.
 * 描述:
 */
public class UserInfoJS {

    /**
     * msg : 获取用户信息成功
     * code : 502
     * phonenumber : 123345535
     * username : lin
     */

    private int userid;
    private String msg;
    private int code;
    private String phonenumber;
    private String username;
    /**
     * userid : 1
     */


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
