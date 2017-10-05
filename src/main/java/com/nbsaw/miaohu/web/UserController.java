package com.nbsaw.miaohu.web;

import com.nbsaw.miaohu.common.JsonResult;
import com.nbsaw.miaohu.dao.UserRepository;
import com.nbsaw.miaohu.model.User;
import com.nbsaw.miaohu.common.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired UserRepository userRepository;
    @Autowired JwtUtils jwtUtils;

    // TODO 第三方登录

    // 登录验证
    @PostMapping("/login")
    public JsonResult login(@RequestParam String phone, @RequestParam String password) {
        User user = userRepository.login(phone, password);
        if (!userRepository.isUserExists(phone))
            return new JsonResult(403,"用户不存在");
        else if (user != null)
            return new JsonResult(0,"登陆成功",new HashMap(){{put("token",jwtUtils.createJWT(user.getId(), user.getUserType()));}});
        return new JsonResult(400,"密码错误");
    }

    // TODO 注销登陆
    // 从redis删除token
}