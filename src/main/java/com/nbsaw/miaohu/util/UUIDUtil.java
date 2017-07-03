package com.nbsaw.miaohu.util;

import java.util.UUID;

/**
 * Created by nbsaw on 2017/6/15.
 */
public class UUIDUtil {
    // 检验id是否合法
    public static void valid(String content){
        try{
            UUID uuid = UUID.fromString(content);
        } catch (IllegalArgumentException exception){
            throw exception;
        }
    }
}
