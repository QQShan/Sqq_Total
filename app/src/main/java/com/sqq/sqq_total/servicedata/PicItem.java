package com.sqq.sqq_total.servicedata;

/**
 * Created by sqq on 2016/6/15.
 */
public class PicItem {
    long id;                //id
    String title;           //标题
    String picUrl;          //原图片的地址
    //String Url;             //点击之后要跳转去的url
    String smallPicUrl;     //小尺寸的图片地址
    long time;            //数据发布的时间
    long userId;
    int scanTimes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getScanTimes() {
        return scanTimes;
    }

    public void setScanTimes(int scanTimes) {
        this.scanTimes = scanTimes;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getSmallPicUrl() {
        return smallPicUrl;
    }

    public void setSmallPicUrl(String smallPicUrl) {
        this.smallPicUrl = smallPicUrl;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
