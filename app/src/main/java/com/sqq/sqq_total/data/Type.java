package com.sqq.sqq_total.data;

import android.support.annotation.StringDef;

/**
 * Created by sqq on 2016/8/11.
 * 和服务器数据交换的类型
 */
public class Type {
    public static final String NONE= "none";
    public static final String LOGIN = "login";
    public static final String LOGIN_BACK = "loginback";
    public static final String REGISTER = "register";
    public static final String REGISTER_BACK = "registerback";

    @StringDef({NONE,LOGIN,LOGIN_BACK,REGISTER,REGISTER_BACK})
    public @interface dataType{}
}
