package com.nbsaw.miaohu.controller;

import com.nbsaw.miaohu.form.RegisterForm;
import com.nbsaw.miaohu.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired RegisterService registerService;

    // 检测注册参数是否合法
    @PostMapping("/valid")
    public Map valid(RegisterForm registerForm, String sid) {
        return registerService.validate(registerForm, sid);
    }

    // 正式注册用户的路由
    @PostMapping
    public Map register(RegisterForm registerForm, String sid) {
        return registerService.register(registerForm, sid);
    }

}
