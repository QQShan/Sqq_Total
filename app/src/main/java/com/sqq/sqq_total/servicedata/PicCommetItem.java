package com.sqq.sqq_total.servicedata;

/**
 * Created by sqq on 2016/7/15.
 */
public class PicCommetItem {
    long id;
    long picId;
    long userId;
    String comment;
    long time;            //数据发布的时间

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPicId() {
        return picId;
    }

    public void setPicId(long picId) {
        this.picId = picId;
    }
}
