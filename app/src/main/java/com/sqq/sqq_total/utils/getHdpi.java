package com.sqq.sqq_total.utils;

import android.content.Context;

/**
 * Created by sqq on 2016/6/1.
 * 主要是算出资源改放在哪个drawable下
 */
public class getHdpi {

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
}
