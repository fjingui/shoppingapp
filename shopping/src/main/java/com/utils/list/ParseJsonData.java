package com.utils.list;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/9/22 0022.
 */

public class  ParseJsonData {

    public static <T> List<T> parseFromJson(String json,Class<T[]> cls){
        Gson sellerjson = new Gson();
        T[] arr= sellerjson.fromJson(json, cls);
        return Arrays.asList(arr);
    }

    public static <K> String parseToJson(K k){
        Gson gson = new Gson();
        return gson.toJson(k);
    }
    public static <T>  T parseObjectJson(String json,Class<T> cls){
        Gson gson= new Gson();
        return gson.fromJson(json,cls);
    }
}
