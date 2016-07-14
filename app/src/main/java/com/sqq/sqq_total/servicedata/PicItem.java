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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getSmallPicUrl() {
        return smallPicUrl;
    }

    public void setSmallPicUrl(String smallPicUrl) {
        this.smallPicUrl = smallPicUrl;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
