package com.sqq.sqq_total.servicedata;

/**
 * Created by sqq on 2016/6/15.
 */
public class TextItem {
    String title;           //标题文字
    String description;     //简短的描述文字
    long time;            //数据发布的时间
    String picUrl;          //图片的地址
    String Url;             //点击之后要跳转去的url

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
