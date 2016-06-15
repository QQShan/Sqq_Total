package com.sqq.sqq_total.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/6/15.
 */
public class TimerUtils {


    private static String formatType = "yyyy-MM-dd HH:mm:ss";//24小时制，"yyyy-MM-dd hh:mm:ss"12小时制

    public static String getTimeStampString(){
        DateFormat df = new SimpleDateFormat(formatType);
        Date date = Calendar.getInstance().getTime();
        return df.format(date);
    }
    public static Long getTimeStampLong(){
        Date date =Calendar.getInstance().getTime();
        return date.getTime();
    }

    public static String longTimeparseToString(long time){
        DateFormat df = new SimpleDateFormat(formatType);
        Date date = new Date(time);
        return df.format(date);
    }

    public static long stringTimeparseToLong(String time){
        DateFormat df = new SimpleDateFormat(formatType);
        Date date = new Date();
        try {
            date = df.parse(time);
        } catch (ParseException e) {
            Log.e("sqq", "转换出错！");
        }
        return date.getTime();
    }
}
