package com.nbsaw.miaohu.web;

import com.nbsaw.miaohu.common.ErrorsMap;
import com.nbsaw.miaohu.common.JsonResult;
import com.nbsaw.miaohu.common.JwtUtils;
import com.nbsaw.miaohu.dao.repository.model.User;
import com.nbsaw.miaohu.service.UserService;
import com.nbsaw.miaohu.validator.UserValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/user")
@AllArgsConstructor(onConstructor = @_(@Autowired))
public class UserController {

    private final JwtUtils jwtUtils;
    private final UserValidator validator;
    private final UserService service;

    // TODO 第三方登录

    // 登录验证
    @PostMapping("/login")
    public JsonResult login(@RequestParam String phone, @RequestParam String password) {
        ErrorsMap errors = validator.existsValid(phone);
        if (errors.hasError())
            return new JsonResult(404, "登陆失败", errors);

        User user = service.login(phone, password);
        if (user == null) {
            errors.put("password", "密码错误");
            return new JsonResult(400, "登陆失败", errors);
        }
        else{
            return new JsonResult(0, "登陆成功",
                    new HashMap() {{
                        put("token", jwtUtils.createJWT(user.getId(), user.getUserType()));
                    }});
        }
    }

    // TODO 注销登陆
    // 从redis删除token

}