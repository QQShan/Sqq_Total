package com.sqq.sqq_total.utils;

import android.content.Context;
import android.util.Log;

import com.sqq.sqq_total.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by sqq on 2016/6/15.
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
    /**
     * 根据传入的时间毫秒数算距离现在的时间，距离现在1小时，1天等
     */
    public static String getTimeUpToNow(long time,Context context){
        String ret ="";
        Date date = Calendar.getInstance().getTime();
        Log.d("ret",""+date.getTime()+"   "+TimerUtils.longTimeparseToString(date.getTime()));
        long tTime = date.getTime()-time;
        float tRat = ((float)tTime)/1000/3600;  //得到的是几个小时
        if(tRat<1){
            //不到一小时,半小时，xx分钟前
            if(tRat==0.5){
                ret = context.getString(R.string.tu_halfhour);
            }else{
                ret = (int)(60*tRat)+context.getString(R.string.tu_minute);
            }
        }else if(tRat>=1&&tRat<=24){
            //几小时前
            ret = ((int)tRat)+context.getString(R.string.tu_hour);
        }else{
            tRat = tRat/24;
            //多少天
            if(tRat<365){
                //一年之内
                ret = ((int)tRat)+context.getString(R.string.tu_day);
            }else{
                tRat = tRat/365;
                ret = ((int)tRat)+context.getString(R.string.tu_year);
            }
        }
        return ret;
    }
}
