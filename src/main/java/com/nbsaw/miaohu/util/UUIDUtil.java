package com.nbsaw.miaohu.util;

import java.util.UUID;

/**
 * Created by nbsaw on 2017/6/15.
 */
public class UUIDUtil {
    // 检车是否有效
    public static void vaild(String content){
        try{
            UUID uuid = UUID.fromString(content);
        } catch (IllegalArgumentException exception){
            throw exception;
        }
    }
}
