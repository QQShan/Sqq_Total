package com.sqq.sqq_total.rxbus;

/**
 * Created by sqq on 2016/8/2.
 */
public class DisConnectedEvent {
    String message = "";
    public DisConnectedEvent(String mess){
        message = mess;
    }

    public String getMessage(){
        return message;
    }
}
