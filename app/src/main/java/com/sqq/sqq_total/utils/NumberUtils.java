package com.sqq.sqq_total.utils;

import android.content.Context;

import com.sqq.sqq_total.R;

import java.math.BigDecimal;

/**
 * Created by sqq on 2016/6/17.
 */
public class NumberUtils {
    /**
     * 只到亿次
     * @param context
     * @param number
     * @return
     */
    public static String getVideoWatchNumber(Context context,int number){
        String ret="";
        if(number<10000){
            //不足一万
            ret = number+context.getString(R.string.nu_times);
        }else{
            float temp_number = (float)number/10000;
            if(temp_number<10000){
                //不足一亿
                float tt_Number = (float)(Math.floor(temp_number*10))/10;
                ret=tt_Number+context.getString(R.string.nu_wtimes);
                /*int scale = 1; //保留的位数
                BigDecimal bd = new BigDecimal(temp_number);
                float tt_Number = bd.setScale(scale,BigDecimal.ROUND_CEILING).floatValue();
                ret=tt_Number+context.getString(R.string.nu_wtimes);*/
            }else {
                float atemp_number = temp_number/10000;
                //大于10000亿就不管
                float tt_Number = (float)(Math.floor(atemp_number*10))/10;
                ret=tt_Number+context.getString(R.string.nu_ytimes);
                /*
                下面这种保留一位的太麻烦
                int scale = 1; //保留的位数
                BigDecimal bd = new BigDecimal(atemp_number);
                float tt_Number = bd.setScale(scale,BigDecimal.ROUND_CEILING).floatValue();
                ret=tt_Number+context.getString(R.string.nu_ytimes);*/
            }
            
        }
        return ret;
    }
}
