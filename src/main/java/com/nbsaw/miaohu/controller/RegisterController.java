package com.nbsaw.miaohu.controller;

import com.nbsaw.miaohu.form.RegisterForm;
import com.nbsaw.miaohu.utils.RegisterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired RegisterUtils registerUtils;

    // 预检测路由
    @PostMapping("/valid")
    public Map valid(RegisterForm registerForm, String sid) {
        return registerUtils.validate(registerForm, sid);
    }

    // 正式注册路由
    @PostMapping
    public Map register(RegisterForm registerForm, String sid) { return registerUtils.register(registerForm, sid); }
}
