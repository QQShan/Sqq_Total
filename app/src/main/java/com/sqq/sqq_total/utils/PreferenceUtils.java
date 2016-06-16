package com.sqq.sqq_total.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sqq on 2016/6/16.
 */
public class PreferenceUtils {
    private static String tag = PreferenceUtils.class.getSimpleName();
    private final static String SP_NAME = "sqq_total";
    private static SharedPreferences sp;



    /**
     * 保存布尔值
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveBoolean(Context context, String key, boolean value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().putBoolean(key, value).commit();
    }
    public static void saveBoolean(Context context, int  resId, boolean value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        String key = context.getString(resId);
        sp.edit().putBoolean(key, value).commit();
    }
    /**
     * 保存字符串
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveString(Context context, String key, String value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().putString(key, value).commit();
    }
    public static void saveString(Context context, int resid, String value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        String key = context.getString(resid);
        sp.edit().putString(key, value).commit();
    }
    /**
     * 保存long型
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveLong(Context context, String key, long value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().putLong(key, value).commit();
    }
    public static void saveLong(Context context, int resId, long value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        String key = context.getString(resId);
        sp.edit().putLong(key, value).commit();
    }

    /**
     * 保存int型
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveInt(Context context, String key, int value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().putInt(key, value).commit();
    }
    public static void saveInt(Context context, int resId, int value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        String key = context.getString(resId);
        sp.edit().putInt(key, value).commit();
    }
    /**
     * 保存float型
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveFloat(Context context, String key, float value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().putFloat(key, value).commit();
    }
    public static void saveFloat(Context context, int resId, float value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        String key = context.getString(resId);
        sp.edit().putFloat(key, value).commit();
    }
    /**
     * 获取字符值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static String getString(Context context, String key, String defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getString(key, defValue);
    }
    public static String getString(Context context, int resId, String defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        String key = context.getString(resId);
        return sp.getString(key, defValue);
    }

    /**
     * 获取int值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static int getInt(Context context, String key, int defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getInt(key, defValue);
    }
    public static int getInt(Context context, int resId, int defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        String key = context.getString(resId);
        return sp.getInt(key, defValue);
    }
    /**
     * 获取long值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static long getLong(Context context, String key, long defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getLong(key, defValue);
    }
    public static long getLong(Context context, int resId, long defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        String key = context.getString(resId);
        return sp.getLong(key, defValue);
    }
    /**
     * 获取float值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static float getFloat(Context context, String key, float defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getFloat(key, defValue);
    }
    public static float getFloat(Context context, int resId, float defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        String key = context.getString(resId);
        return sp.getFloat(key, defValue);
    }

    /**
     * 获取布尔值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getBoolean(key, defValue);
    }
    public static boolean getBoolean(Context context, int resId, boolean defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        String key = context.getString(resId);
        return sp.getBoolean(key, defValue);
    }

    public static void clear(Context context) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().clear().commit();
    }

    public static void removeItem(Context context, String key) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().remove(key).commit();
    }
    public static void removeItem(Context context, int resId) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        String key = context.getString(resId);
        sp.edit().remove(key).commit();
    }
}

