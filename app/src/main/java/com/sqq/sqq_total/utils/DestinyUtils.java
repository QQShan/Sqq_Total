package com.sqq.sqq_total.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;

import java.lang.reflect.Field;

/**
 * Created by sqq on 2016/6/1.
 * 主要是算出资源改放在哪个drawable下
 */
public class DestinyUtils {

    /**
     * xdpi是宽度的dpi值
     * ydpi是高度的dpi值
     * 这两个值通常是近乎相等的
     * 0 ~120  ldpi
     * 120 ~160  mdpi
     * 160 ~240  hdpi
     * 240 ~320  xhdpi
     * 320 ~480  xxhdpi
     * 480 ~640  xxxhdpi
     * @param con
     * @return
     */
    public static float getHdpi(Context con){
        float xdpi = con.getResources().getDisplayMetrics()
                .xdpi;
        float ydpi = con.getResources().getDisplayMetrics()
                .ydpi;

        //
        return xdpi;
    }

    public static int getScreenWidth(Activity context)
    {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int mScreenWidth = dm.widthPixels;// 获取屏幕分辨率宽度
        return  mScreenWidth;
    }

    public static int getScreenHeight(Activity context)
    {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int mScreenHeight = dm.heightPixels;
        return  mScreenHeight;
    }

    public static int getStatusHeight(Activity activity)
    {
        int statusBarHeight = 0;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object o = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = (Integer) field.get(o);
            statusBarHeight = activity.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
            Rect frame = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            statusBarHeight = frame.top;
        }
        return statusBarHeight;
    }
}
