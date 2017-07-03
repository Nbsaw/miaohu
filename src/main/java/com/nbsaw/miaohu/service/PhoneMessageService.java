package com.nbsaw.miaohu.service;

import org.springframework.stereotype.Service;

@Service
public interface PhoneMessageService {

    // 发送注册验证码到手机,并返回验证码
    String sendRegisterCode(String phone);

}
