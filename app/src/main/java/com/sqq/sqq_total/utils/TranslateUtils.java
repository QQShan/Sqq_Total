package com.sqq.sqq_total.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by sqq on 2016/3/24.
 * 各种转换方法
 */
public class TranslateUtils {

    /**
     * 转换成px
     * @param dpVal
     * @return
     */
    public static int dp2px(int dpVal ,Context con)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, con.getResources().getDisplayMetrics());
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static float px2sp(float dpVal ,Context con){
        float fontSize = con.getResources().getDisplayMetrics().scaledDensity;
        return dpVal/fontSize+0.5f;
    }

    public static float sp2px(float dpVal ,Context con){
        float fontSize = con.getResources().getDisplayMetrics().scaledDensity;
        return dpVal*fontSize+0.5f;
    }
}
