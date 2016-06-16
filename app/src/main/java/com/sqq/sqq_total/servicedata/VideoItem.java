package com.sqq.sqq_total.servicedata;

/**
 * Created by sqq on 2016/6/15.
 */
public class VideoItem {
    long id;                //id
    String description;     //简短的描述文字
    long time;              //数据发布的时间
    String picUrl;          //图片的地址
    String Url;             //点击之后要跳转去的url
    int number;             //观看人数，int类型应该够了，不可能有20多亿人看的视频的

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
