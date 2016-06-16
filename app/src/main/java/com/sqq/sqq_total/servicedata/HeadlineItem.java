package com.sqq.sqq_total.servicedata;


import java.sql.Timestamp;

/**
 * Created by sqq on 2016/6/8.
 * recyclerview里一般的item
 */
public class HeadlineItem {
    long id;                //id
    String title;           //标题文字
    String description;     //简短的描述文字
    long time;            //数据发布的时间
    String picUrl;          //图片的地址
    String Url;             //点击之后要跳转去的url

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
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
}
