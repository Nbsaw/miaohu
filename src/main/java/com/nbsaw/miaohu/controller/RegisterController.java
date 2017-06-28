package com.nbsaw.miaohu.controller;

import com.nbsaw.miaohu.form.RegisterForm;
import com.nbsaw.miaohu.entity.UserEntity;
import com.nbsaw.miaohu.repository.UserRepository;
import com.nbsaw.miaohu.type.UserType;
import com.nbsaw.miaohu.util.JwtUtil;
import com.nbsaw.miaohu.util.RedisUtil;
import com.nbsaw.miaohu.util.RegisterValidUtil;
import com.nbsaw.miaohu.config.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by fz on 17-4-9.
 */
@RestController
@RequestMapping(value = "/register")
public class RegisterController {
    @Autowired
    private RedisConfig redisConfig;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private JwtUtil jwtUtil;

    // 第一步检测
    @PostMapping(value = "/valid")
    public Map valid(@ModelAttribute RegisterForm registerForm, String sid, HttpServletRequest request) {
        return validate(registerForm, sid,false,request);
    }

    // 检测参数是否合法
    public Map validate(RegisterForm registerForm, String sid,boolean is,HttpServletRequest request) {
        Map result = new LinkedHashMap();
        Map errors = new LinkedHashMap();

        // 需要被检测的信息
        String username = registerForm.getUsername();
        String password = registerForm.getPassword();
        String imageCaptcha = registerForm.getImageCaptcha();
        String imageCaptchaFormat = redisUtil.imageCaptchaFormat(sid);
        String phoneCaptcha = registerForm.getPhoneCaptcha();
        String redisCaptcha = redisConfig.getTemplate().opsForValue().get(imageCaptchaFormat);
        String phone = registerForm.getPhone();

        // 校验用户名
        RegisterValidUtil.usernameValid(username, errors);

        // 校验密码
        RegisterValidUtil.passwordValid(password, errors);

        // 校验手机号码
        RegisterValidUtil.phoneValid(phone, errors,userRepository);

        // 校验图片验证码
        RegisterValidUtil.imageCaptchaValid(imageCaptcha, errors, redisCaptcha);

        // 校验手机验证码
        if (is == true){
            String phoneCaptchaFormat = redisUtil.phoneCaptchaFormat(phone);
            String redisPhoneCaptcha = (String) redisConfig.getTemplate().opsForValue().get(phoneCaptchaFormat);
            System.out.println("phoneCaptcha : " + phoneCaptcha);
            System.out.println("redisPhoneCaptcha : " + redisPhoneCaptcha);
            RegisterValidUtil.phoneCaptchaValid(phoneCaptcha,errors,redisPhoneCaptcha);
        }

        // 判断是否有错误信息
        if (!errors.isEmpty()) {
            result.put("code", 400);
            result.put("errors", errors);
        } else {
            result.put("code", 200);
            result.put("msg", "验证通过!");
        }

        return result;
    }

    // 正式注册
    @PostMapping
    public Map register(RegisterForm registerForm, String sid,HttpServletRequest request) {
        // 校对用户信息
        Map result = validate(registerForm, sid,true,request);
        // 验证不通过的情况
        if ((int) result.get("code") == 200){
            // 初始化信息
            String userId = UUID.randomUUID().toString();
            UserType userType = UserType.USER;
            // 把注册信息写入到数据库
            UserEntity userEntity = new UserEntity();
            userEntity.setId(userId);
            userEntity.setUsername(registerForm.getUsername());
            userEntity.setPassword(registerForm.getPassword());
            userEntity.setPhone(registerForm.getPhone());
            userEntity.setUserType(userType);
            userRepository.save(userEntity);
            // 设置token
            String token = jwtUtil.createJWT(userId,userType);
            result.put("result",token);
        }
        return result;
    }
}
