package com.example.administrator.sdk.utils;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/7/19.
 */

public class GsonUtils {
    private static GsonUtils gsonUtils = null;

    public static GsonUtils getInstance() {
        if (gsonUtils == null) {
            gsonUtils = new GsonUtils();
        }
        return gsonUtils;
    }

    public String EntityToJson(Object entity) {
        Gson gson = new Gson();
        String json = gson.toJson(entity);
        return json;
    }

    public Object JsonToEntity(String j, Object cls) {
        Gson gson = new Gson();
        Object o = gson.fromJson(j, (Class<? extends Object>) cls);
        return o;
    }
}
