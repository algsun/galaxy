package com.microwise.blueplanet.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author xiedeng
 * @date 14-12-8
 */
public class JsonUtil {

    public static String toJson(Object value) {
        Gson gson = new Gson();
        return gson.toJson(value);
    }

    public static Object toEntity(String value, Class c) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.fromJson(value, c);
    }

}
