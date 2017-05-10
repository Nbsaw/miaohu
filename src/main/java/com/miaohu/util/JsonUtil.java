package com.miaohu.util;

import org.json.JSONObject;

/**
 * Created by Nbsaw on 17-5-5.
 */
public class JsonUtil {
    public static String formatResult(Integer status, String message){
        JSONObject jsonObject = new JSONObject(){{
            put("code", status);
            put("message", message);
        }};
        return jsonObject.toString();
    }

    public static String formatResult(Integer status, String message, Object result){
        JSONObject jsonObject = new JSONObject(){{
            put("code", status);
            put("message", message);
            put("result", result);
        }};
        return jsonObject.toString();
    }
}
