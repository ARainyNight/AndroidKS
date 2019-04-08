package com.example.lin.androidkeshe.json;

import java.util.List;

/**
 * Created by lin on 2018/7/5.
 * 描述:
 */
public class MenuJS {


    /**
     * type : detail
     * pageToken : null
     * dataType : product
     * hasNext : false
     * data : [{"sellerId":"1827228","description":"\n主要原料: 鸡肉","catName1":"热销*/

    private String type;
    private Object pageToken;
    private String dataType;
    private boolean hasNext;
    private String retcode;
    private String appCode;
    private List<DataBean> data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getPageToken() {
        return pageToken;
    }

    public void setPageToken(Object pageToken) {
        this.pageToken = pageToken;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * sellerId : 1827228
         * description :
         主要原料: 鸡肉
         * catName1 : 热销
         * url : https://www.ele.me/shop/1827228
         * id : 55124117452
         * imageUrls : ["http://fuss10.elemecdn.com/8/4e/7e5ef33fff08feae7f24aa9762f7ajpeg.jpeg"]
         * rating : 4.25
         * skuOptions : [{"price":4.5,"saleCount":1316,"marketPrice":7,"packFee":0,"soldout":false,"id":"64713191372","name":"香辣鸡腿堡","stockSize":6823}]
         * ratingCount : 84
         * title : 香辣鸡腿堡
         * commentCount : 84
         * sortId : 0
         * goodRatingRatio : 1
         * likeCount : 84
         * monthSaleCount : 1316
         * catId1 : 16226837
         */

        private String sellerId;
        private String description;
        private String catName1;
        private String url;
        private String id;
        private double rating;
        private int ratingCount;
        private String title;
        private int commentCount;
        private int sortId;
        private double goodRatingRatio;
        private int likeCount;
        private int monthSaleCount;
        private String catId1;
        private List<String> imageUrls;
        private List<SkuOptionsBean> skuOptions;

        public String getSellerId() {
            return sellerId;
        }

        public void setSellerId(String sellerId) {
            this.sellerId = sellerId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCatName1() {
            return catName1;
        }

        public void setCatName1(String catName1) {
            this.catName1 = catName1;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }

        public int getRatingCount() {
            return ratingCount;
        }

        public void setRatingCount(int ratingCount) {
            this.ratingCount = ratingCount;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public int getSortId() {
            return sortId;
        }

        public void setSortId(int sortId) {
            this.sortId = sortId;
        }

        public double getGoodRatingRatio() {
            return goodRatingRatio;
        }

        public void setGoodRatingRatio(double goodRatingRatio) {
            this.goodRatingRatio = goodRatingRatio;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public int getMonthSaleCount() {
            return monthSaleCount;
        }

        public void setMonthSaleCount(int monthSaleCount) {
            this.monthSaleCount = monthSaleCount;
        }

        public String getCatId1() {
            return catId1;
        }

        public void setCatId1(String catId1) {
            this.catId1 = catId1;
        }

        public List<String> getImageUrls() {
            return imageUrls;
        }

        public void setImageUrls(List<String> imageUrls) {
            this.imageUrls = imageUrls;
        }

        public List<SkuOptionsBean> getSkuOptions() {
            return skuOptions;
        }

        public void setSkuOptions(List<SkuOptionsBean> skuOptions) {
            this.skuOptions = skuOptions;
        }

        public static class SkuOptionsBean {
            /**
             * price : 4.5
             * saleCount : 1316
             * marketPrice : 7
             * packFee : 0
             * soldout : false
             * id : 64713191372
             * name : 香辣鸡腿堡
             * stockSize : 6823
             */

            private double price;
            private int saleCount;
            private double marketPrice;
            private double packFee;
            private boolean soldout;
            private String id;
            private String name;
            private int stockSize;

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public int getSaleCount() {
                return saleCount;
            }

            public void setSaleCount(int saleCount) {
                this.saleCount = saleCount;
            }

            public double getMarketPrice() {
                return marketPrice;
            }

            public void setMarketPrice(double marketPrice) {
                this.marketPrice = marketPrice;
            }

            public double getPackFee() {
                return packFee;
            }

            public void setPackFee(double packFee) {
                this.packFee = packFee;
            }

            public boolean isSoldout() {
                return soldout;
            }

            public void setSoldout(boolean soldout) {
                this.soldout = soldout;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getStockSize() {
                return stockSize;
            }

            public void setStockSize(int stockSize) {
                this.stockSize = stockSize;
            }
        }
    }
}
