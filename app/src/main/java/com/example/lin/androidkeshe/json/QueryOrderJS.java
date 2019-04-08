package com.example.lin.androidkeshe.json;

/**
 * Created by lin on 2018/7/6.
 * 描述:
 */
public class QueryOrderJS {

    /**
     * commbusinessname : 张三疯披萨
     * commid : 6
     * commimgid : http://fuss10.elemecdn.com/0/1c/f1eae5c02d00820ec6476662f91fejpeg.jpeg
     * commname : 经典意大利肉酱面
     * commprice : 25.0
     * userid : 4
     */

    private String commbusinessname;
    private int commid;
    private String commimgid;
    private String commname;
    private String commprice;
    private int userid;

    public String getCommbusinessname() {
        return commbusinessname;
    }

    public void setCommbusinessname(String commbusinessname) {
        this.commbusinessname = commbusinessname;
    }

    public int getCommid() {
        return commid;
    }

    public void setCommid(int commid) {
        this.commid = commid;
    }

    public String getCommimgid() {
        return commimgid;
    }

    public void setCommimgid(String commimgid) {
        this.commimgid = commimgid;
    }

    public String getCommname() {
        return commname;
    }

    public void setCommname(String commname) {
        this.commname = commname;
    }

    public String getCommprice() {
        return commprice;
    }

    public void setCommprice(String commprice) {
        this.commprice = commprice;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
