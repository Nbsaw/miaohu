package com.zhihu.service;

import org.springframework.stereotype.Service;

/**
 * Created by fz on 17-4-6.
 * 手机短信服务,使用了阿里大于
 */
@Service
public interface PhoneMessageService {
    String sendRegisterCode(String phone);// 发送注册验证码到手机,返回验证码
}
