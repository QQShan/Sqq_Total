package com.sqq.sqq_total.nioclient;

import com.google.gson.Gson;
import com.sqq.sqq_total.data.Type;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by sqq on 2016/8/11.
 */
public class GsonConverter implements Converter<Object,String> {
    private final Gson gson;
    public GsonConverter(){
        gson = new Gson();
    }

    @Override
    public String getType(String msg) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(msg);
            String type = jsonObject.getString("type");
            return type;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Type.NONE;
    }

    @Override
    public String converTo(Object var1) throws IOException {
        return gson.toJson(var1);
    }

    @Override
    public Object converBack(String var,Class<?> clazz) throws IOException {
        return gson.fromJson(var,clazz);
    }
}
