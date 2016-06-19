package com.sqq.sqq_total.servicedata;

/**
 * Created by sqq on 2016/6/16.
 * 轮播图数据
 */
public class SlideviewItem {
    long id;                //id
    String title;           //标题
    String picUrl;          //图片的地址
    String Url;             //点击之后要跳转去的url
    long time;              //数据发布的时间

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

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
