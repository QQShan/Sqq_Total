package com.sqq.sqq_total.utils;

import android.content.Context;

import java.io.File;

/**
 * Created by sqq on 2016/6/13.
 * 一下两个方法获取的目录都会在app卸载之后删除
 */
public class CacheUtils {

    /**
     * 获取data/packageName/cache
     * @param context
     * @return
     */
    public static File getDataPath(Context context){
        return context.getCacheDir();
    }

    /**
     * 获取sd卡目录下android/data/packageName/cache
     * @param context
     * @return
     */
    public static File getAndroidDataPath(Context context){
        return context.getExternalCacheDir();
    }
}
