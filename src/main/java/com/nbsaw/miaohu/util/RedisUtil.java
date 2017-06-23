package com.nbsaw.miaohu.util;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by nbsaw on 2017/6/14.
 * Redis 工具
 */

@Component
public class RedisUtil {
    // 图片验证码
    @Value("${redis.constant.image}")
    private String imageSuffix;
    @Value("${redis.constant.imageTimeOut}")
    private int imageTimeOut;

    // 手机验证码
    @Value("${redis.constant.phone}")
    private String phoneSuffix;
    @Value("${redis.constant.phoneTimeOut}")
    private int phoneTimeOut;

    // 返回图片验证码码在Redis里面的的格式
    public String imageCaptchaFormat(String id){
        return id + imageSuffix;
    }

    // 返回手机验证码在Redis里面的格式
    public String phoneCaptchaFormat(String phone){
        return phone + phoneSuffix;
    }

    // 获取图片验证码超时时间
    public int getImageTimeOut(){
        return imageTimeOut;
    }

    // 获取手机验证码超时时间
    public int getPhoneTimeOut(){
        return phoneTimeOut;
    }
}
