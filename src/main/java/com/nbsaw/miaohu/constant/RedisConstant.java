package com.nbsaw.miaohu.constant;

/**
 * Created by fz on 17-4-9.
 * 记录在Redis的一些常量
 */
public class RedisConstant {
    public static final String PHONE = ":PHONE"; // 手机验证码在redis的后缀
    public static final String IMAGE = ":IMAGE"; // 图片验证码在redis的后缀
    public static final int PHONETIMEOUT = 3 ;  // 手机验证码超时时间
    public static final int IMAGETIMEOUT = 5 ; // 图片验证码超时时间
}
