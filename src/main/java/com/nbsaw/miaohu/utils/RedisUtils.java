package com.nbsaw.miaohu.utils;

import com.nbsaw.miaohu.config.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {
    @Autowired RedisConfig redisConfig;

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
    private String imageCaptchaFormat(String id){ return id + imageSuffix; }

    // 返回手机验证码在Redis里面的格式
    private String phoneCaptchaFormat(String phone){
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

    // 获取Redis模板
    private StringRedisTemplate getTemplate(){
        return redisConfig.getTemplate();
    }

    // 判断是否发送过手机验证码
    public boolean isSentPhoneCaptcha(String phone){
        return StringUtils.isEmpty(getRedisPhoneCaptcha(phone));
    }

    // 保存图片验证码到redis,并设置过期时间
    public void persistImageCaptcha(String sid,String capText){
        StringRedisTemplate template= redisConfig.getTemplate();
        String format = imageCaptchaFormat(sid);
        ValueOperations<String,String> obj =  template.opsForValue();
        obj.set(format,capText.toLowerCase());
        template.expire(format , getImageTimeOut(), TimeUnit.MINUTES);
    }

    // 把手机验证码存到redis
    public void persistPhoneCaptcha(String phone , String code){
        StringRedisTemplate template = getTemplate();
        String format = phoneCaptchaFormat(phone);
        ValueOperations<String,String> obj = getTemplate().opsForValue();
        obj.set(format,code.toLowerCase());
        template.expire(format ,getPhoneTimeOut(), TimeUnit.MINUTES);
    }

    // 获取存在Redis里面的验证码
    public String getRedisImageCaptcha(String sid){
        String imageCaptchaFormat = imageCaptchaFormat(sid);
        String redisCaptcha = getTemplate().opsForValue().get(imageCaptchaFormat);
        return redisCaptcha;
    }

    // 获取Redis里面保存的手机验证码
    public String getRedisPhoneCaptcha(String phone){
        StringRedisTemplate template= redisConfig.getTemplate();
        String phoneCaptchaFormat =  phoneCaptchaFormat(phone);
        String redisCaptcha = template.opsForValue().get(phoneCaptchaFormat);
        return redisCaptcha;
    }
}
