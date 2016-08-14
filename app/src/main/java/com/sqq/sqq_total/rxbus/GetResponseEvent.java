package com.sqq.sqq_total.rxbus;

/**
 * Created by sqq on 2016/8/5.
 */
public class GetResponseEvent {

    String response;
    String  type;

    public GetResponseEvent(String response,String type){
        this.response = response;
        this.type = type;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getType(){
        return this.type;
    }
}
