package com.nbsaw.miaohu.service;

import com.taobao.api.ApiException;

public interface PhoneMessageService {
    // 发送注册验证码到手机,并返回验证码
    String sendRegisterCode(String phone) throws ApiException;
}
