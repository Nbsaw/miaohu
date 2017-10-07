package com.nbsaw.miaohu.web;

import com.nbsaw.miaohu.common.ErrorsMap;
import com.nbsaw.miaohu.common.JsonResult;
import com.nbsaw.miaohu.service.RegisterService;
import com.nbsaw.miaohu.validator.RegisterValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
@AllArgsConstructor(onConstructor = @_(@Autowired))
public class RegisterController {

    private final RegisterService service;
    private final RegisterValidator validator;

    // 检测注册参数是否合法
    @PostMapping("/valid")
    public JsonResult valid(@RequestParam String username,
                            @RequestParam String password,
                            @RequestParam String phone,
                            @RequestParam String imageCaptcha,
                            @RequestParam String sid) {
        ErrorsMap errors = validator.validForm(username,password,phone,imageCaptcha,sid);
        if (errors.hasError())
            return new JsonResult(400,"注册参数有误",errors.getErrors());
        else
            return new JsonResult(0,"注册参数验证成功");
    }

    // 正式注册用户的路由
    @PostMapping
    public JsonResult register(@RequestParam String username,
                        @RequestParam String password,
                        @RequestParam String phone,
                        @RequestParam String imageCaptcha,
                        @RequestParam String phoneCaptcha,
                        @RequestParam String sid) {
        ErrorsMap errors = validator.registerValid(username,password,phone,imageCaptcha,phoneCaptcha,sid);
        if (errors.hasError())
            return new JsonResult(400,"注册参数有误",errors.getErrors());
        else{
            service.register(username,password,phone);
            return new JsonResult(0,"注册成功");
        }
    }

}
