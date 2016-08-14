package com.sqq.sqq_total.nioclient;

import java.io.IOException;

/**
 * Created by sqq on 2016/8/11.
 * 数据
 */
public interface Converter<F,T> {

    /**
     * 获取类型
     * @param msg
     * @return
     */
    public String getType(String msg);

    /**
     * 转换到例如gson
     * @param var1
     * @return
     * @throws IOException
     */
    T converTo(F var1) throws IOException;

    /**
     * 例如从gson转换回来
     * @param var
     * @param actc
     * @return
     * @throws IOException
     */
    F converBack(T var, Class<?> actc) throws IOException;
}
